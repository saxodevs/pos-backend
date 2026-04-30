package com.saxodevs.pos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.saxodevs.pos.dto.CategoryDTO;

@Service
public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO dto) throws Exception;

    CategoryDTO updateCategory(UUID id, CategoryDTO dto) throws Exception;

    CategoryDTO getCategoryById(UUID id) throws Exception;

    CategoryDTO getCategoryByName(String name) throws Exception;

    List<CategoryDTO> getAllCategory() throws Exception;

    void deleteCategory(UUID id) throws Exception;

}
