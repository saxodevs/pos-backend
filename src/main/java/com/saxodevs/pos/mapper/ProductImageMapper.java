package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.ProductImageDTO;
import com.saxodevs.pos.model.ProductImage;

import java.util.List;

public class ProductImageMapper {

    public static ProductImageDTO dto(ProductImage image){
        return  ProductImageDTO.builder()
                .product(image.getProduct().getId())
                .path(image.getPath())
                .id(image.getId())
                .build();
    }

    public static List<ProductImageDTO> dtoList(List<ProductImage> images){
        return images.stream().map(ProductImageMapper::dto).toList();
    }
}
