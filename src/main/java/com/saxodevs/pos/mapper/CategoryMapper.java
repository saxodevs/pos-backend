package com.saxodevs.pos.mapper;

import java.util.Collections;
import java.util.List;

import com.saxodevs.pos.dto.CategoryDTO;
import com.saxodevs.pos.model.Category;


import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.model.User;
import lombok.Data;

@Data
public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .sku(category.getSku())
                .des(category.getDes())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .createdById(category.getCreatedBy() != null ? category.getCreatedBy().getId() : null)
                .updatedById(category.getUpdatedBy() != null ? category.getUpdatedBy().getId() : null)
                .build();
    }

    public static Category toEntity(CategoryDTO dto, User user) {
        if (dto == null) return null;

        return Category.builder()
                .name(dto.getName())
                .des(dto.getDes())
                .sku(dto.getSku())
                .createdBy(user)

                .build();
    }

    public static void updateEntity(Category category, CategoryDTO dto, User user) {
        category.setName(dto.getName());
        category.setDes(dto.getDes());
        category.setSku(dto.getSku());
        category.setUpdatedBy(user);
    }

    public static List<CategoryDTO> toDTOList(List<Category> categories){
        return categories.stream().map(CategoryMapper::toDTO).toList();
    }
}
