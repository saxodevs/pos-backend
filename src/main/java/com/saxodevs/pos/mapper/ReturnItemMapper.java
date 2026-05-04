package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.ReturnItemDTO;
import com.saxodevs.pos.model.ReturnItem;
import lombok.Data;

import java.util.List;

@Data
public class ReturnItemMapper {

    public static ReturnItemDTO toDTO(ReturnItem returnItem) {
        return ReturnItemDTO.builder()
                .id(returnItem.getId())
                .returnsId(returnItem.getId())
                .quantity(returnItem.getQuantity())
                .productId(returnItem.getProduct().getId())
                .build();
    }

    public static List<ReturnItemDTO> toDTO(List<ReturnItem> returnItems) {
        return  returnItems.stream().map(ReturnItemMapper::toDTO).toList();
    }
}
