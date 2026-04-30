package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.SaleDTO;
import com.saxodevs.pos.dto.SaleItemDTO;
import com.saxodevs.pos.model.Customer;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.SaleItem;
import com.saxodevs.pos.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class SaleMapper {

    public static SaleDTO toDTO(Sale entity) {
        if (entity == null) return null;



        return SaleDTO.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .cashierId(entity.getUser().getId())
                .customerId(entity.getCustomer().getId())
                .items(entity.getSaleItems().stream()
                        .map(SaleItemMapper::toDTO)
                        .collect(Collectors.toList())
                )
                .totalAmount(entity.getTotalAmount())
                .taxAmount(entity.getTaxAmount())
                .discount(entity.getDiscount())
                .paymentMethod(entity.getPaymentMethod())
                .createdAt(entity.getCreatedAt())
                .deleted(entity.getDeleted())
                .deletedBy(null)
                .payment(entity.getPayment() != null? PaymentMapper.toDTO(entity.getPayment()) : null)
                .build();
    }

    public static Sale toEntity(SaleDTO dto,User cashier, Customer customer) {
        if (dto == null) return null;

        return Sale.builder()
                .id(dto.getId())
                .user(cashier)
                .customer(customer)
                .totalAmount(dto.getTotalAmount())
                .taxAmount(dto.getTaxAmount())
                .discount(dto.getDiscount())
                .paymentMethod(dto.getPaymentMethod())
                .build();


    }

    public static List<SaleDTO> dtoList(List<Sale> sales){
        return sales.stream().map(SaleMapper::toDTO).toList();
    }
}