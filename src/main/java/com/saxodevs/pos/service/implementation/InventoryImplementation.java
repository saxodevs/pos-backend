package com.saxodevs.pos.service.implementation;


import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.model.InventoryLog;
import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.repository.InventoryLogRepository;
import com.saxodevs.pos.repository.ProductRepository;
import com.saxodevs.pos.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryImplementation implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryLogRepository logRepository;

    @Transactional
    public void reduceStock(Product product, int quantity) throws Exception {

        int newStock = product.getStockQuantity() - quantity;

        if (newStock < 0) {
            throw new AppException("Insufficient stock", HttpStatus.BAD_REQUEST);
        }

        product.setStockQuantity(newStock);
        productRepository.save(product);

        logRepository(product, quantity);
//        checkReorderLevel(product);


    }

    @Override
    public void reStock(Product product, int quantity) throws Exception {
        int newStock = product.getStockQuantity() + quantity;



        product.setStockQuantity(newStock);
        productRepository.save(product);

        logRepository(product, quantity);
    }

    public void logRepository(Product product, int quantity) {
        InventoryLog inventoryLog = InventoryLog.builder()
                .product(product)
                .qtyChange(quantity)
                .build();

        logRepository.save(inventoryLog);
    }

    @Override
    public void checkReorderLevel(Product product)  {


        if (product.getStockQuantity() <= product.getReorderLevel()) {

            throw new AppException("Low stock alert: " + product.getSku(), HttpStatus.BAD_REQUEST);

        }


    }

}
