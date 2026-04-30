package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.domain.ChangeType;
import com.saxodevs.pos.domain.PaymentMethod;

import com.saxodevs.pos.dto.SaleDTO;

import com.saxodevs.pos.dto.SaleItemDTO;
import com.saxodevs.pos.dto.SalesReportDTO;
import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.mapper.SaleMapper;
import com.saxodevs.pos.model.*;
import com.saxodevs.pos.pricing.Pricing;
import com.saxodevs.pos.repository.*;
import com.saxodevs.pos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleImplementation implements SaleService {

    private final SaleRepository saleRepository;

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PricingService pricingService;
    private final PaymentService paymentService;
    private final InventoryLogService inventoryLogService;
    private final InventoryService inventoryService;


    private final CustomIdGenerator generator;

    @Transactional
    public SaleDTO createSale(SaleDTO dto) throws Exception {

        User cashier = userService.getCurrentUser();

        Customer customer = (dto.getCustomerId() != null)
                ? customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new AppException("Customer not found", HttpStatus.NOT_FOUND))
                : customerRepository.findByIsDefaultTrue()
                .orElseThrow(() -> new AppException("Default customer not found", HttpStatus.NOT_FOUND));

        Sale sale = Sale.builder()
                .sku(generator.generate("SAL"))
                .customer(customer)
                .createdAt(dto.getCreatedAt())
                .user(cashier)
                .paymentMethod(dto.getPaymentMethod())
                .build();

        List<SaleItem> saleItems = new ArrayList<>();


        Map<Product, Integer> productQuantityMap = new HashMap<>();

        for (SaleItemDTO itemDTO : dto.getItems()) {

            if (itemDTO.getQuantity() <= 0) {
                throw new AppException("Quantity must be greater than zero", HttpStatus.BAD_REQUEST);
            }

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new AppException("Product not found", HttpStatus.NOT_FOUND));

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new AppException("Insufficient stock for product: " + product.getSku(), HttpStatus.BAD_REQUEST);
            }

            productQuantityMap.put(product, itemDTO.getQuantity());

            SaleItem saleItem = SaleItem.builder()
                    .sale(sale)
                    .sku(generator.generate("SLIT"))
                    .product(product)
                    .unitPrice(product.getSellingPrice())
                    .quantity(itemDTO.getQuantity())
                    .totalPrice(
                            product.getSellingPrice()
                                    .multiply(BigDecimal.valueOf(itemDTO.getQuantity()))
                    )
                    .build();

            saleItems.add(saleItem);
        }


        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            inventoryService.reduceStock(product, quantity);
            inventoryLogService.logInventoryChange(product, ChangeType.SALE, quantity);
        }

        Pricing pricing = pricingService.calculate(saleItems);

        sale.setTotalAmount(pricing.getFinalTotal());
        sale.setTaxAmount(pricing.getTax());
        sale.setDiscount(pricing.getDiscount());
        sale.setSaleItems(saleItems);


        Sale savedSale = saleRepository.save(sale);

        return SaleMapper.toDTO(savedSale);
    }

    @Override
    public SaleDTO getSaleById(UUID saleId) throws Exception {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new AppException("Sale not found", HttpStatus.NOT_FOUND));

        return SaleMapper.toDTO(sale);
    }

    @Override
    public List<SaleDTO> getSaleByCashier(UUID cashierId) {
        return saleRepository.findByCashierId(cashierId)
                .stream()
                .map(SaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getSaleByCustomer(UUID customerId) {
        return saleRepository.findByCustomer(customerId)
                .stream()
                .map(SaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getSaleByPaymentMethod(PaymentMethod paymentMethod) {
        return saleRepository.findByPaymentMethod(paymentMethod)
                .stream()
                .map(SaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getAll() throws Exception {
        List<Sale> saleDTOS = saleRepository.findByDeletedFalse();
        return SaleMapper.dtoList(saleDTOS);
    }

    @Override
    public List<SaleDTO> getSalesByCashier(UUID id) throws Exception {

        User cashier = userRepository.findById(id).orElseThrow(
                () -> new  Exception("User not found")
        );

       List<Sale> sale = saleRepository.getSalesByCashier(cashier.getId());

        return SaleMapper.dtoList(sale);
    }

    @Override
    public List<SalesReportDTO> getDailySaleReport() {
        return saleRepository.getDailySalesReport()
                .stream()
                .map(obj -> {
                    java.sql.Date sqlDate = (java.sql.Date) obj[0];
                    LocalDate date = sqlDate.toLocalDate();

                    double revenue = ((Number) obj[1]).doubleValue();
                    long sales = ((Number) obj[2]).longValue();

                    return new SalesReportDTO(
                            date.toString(),
                            revenue,
                            sales
                    );
                })
                .toList();

    }

    @Override
    public List<SalesReportDTO> getMonthlySaleReport() {
        return saleRepository.getMonthlySalesReport()
                .stream()
                .map(obj -> {

                    int monthNumber = ((Number) obj[0]).intValue();
                    double revenue = ((Number) obj[1]).doubleValue();
                    long sales = ((Number) obj[2]).longValue();

                    String monthName = Month.of(monthNumber)
                            .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

                    return new SalesReportDTO(
                            monthName,
                            revenue,
                            sales
                    );
                })
                .toList();
    }


    @Override
    public void deleteSale(UUID saleId) throws Exception {


        User user = userService.getCurrentUser();

        Sale sale = saleRepository.findById(saleId).orElseThrow(
                () -> new AppException("Sale not found", HttpStatus.NOT_FOUND)
        );

        sale.setDeletedBy(user);
        sale.setDeleted(true);
        saleRepository.save(sale);
    }
}