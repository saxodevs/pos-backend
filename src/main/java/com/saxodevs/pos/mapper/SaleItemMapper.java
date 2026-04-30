package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.SaleItemDTO;
import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.SaleItem;

import java.util.List;

public class SaleItemMapper {

    public static SaleItemDTO toDTO(SaleItem entity) {
        if (entity == null) return null;

        return SaleItemDTO.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .sku(entity.getSku())
                .build();
    }

    public static List<SaleItemDTO> dtoList(List<SaleItem> saleItems){
        return saleItems.stream().map(SaleItemMapper::toDTO).toList();
    }


}