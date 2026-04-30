
package com.saxodevs.pos.service.implementation;

import java.util.List;
import java.util.UUID;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saxodevs.pos.dto.CategoryDTO;
import com.saxodevs.pos.mapper.CategoryMapper;
import com.saxodevs.pos.model.Category;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.CategoryRepository;
import com.saxodevs.pos.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryImplementation implements CategoryService {

   private final UserService userService;
    private final CustomIdGenerator generator;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) throws Exception {

        if (categoryRepository.findByName(dto.getName()) != null) {
            throw new AppException("Category already exits by name " + dto.getName(), HttpStatus.BAD_REQUEST);

        }


        User user = userService.getCurrentUser();

        dto.setSku(generator.generate("CTR"));

        Category category = CategoryMapper.toEntity(dto, user);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(savedCategory);

    }

    @Override
    public CategoryDTO updateCategory(UUID id, CategoryDTO dto) throws Exception {
        User user = userService.getCurrentUser();

        Category existinCategory = categoryRepository.findById(id).orElseThrow(
                () -> new AppException("Category not found by " + id, HttpStatus.NOT_FOUND));

        existinCategory.setName(dto.getName());
        existinCategory.setDes(dto.getDes());
        existinCategory.setUpdatedBy(user);

        Category category = categoryRepository.save(existinCategory);

        return CategoryMapper.toDTO(category);

    }

    @Override
    public CategoryDTO getCategoryById(UUID id) throws Exception {

        Category existinCategory = categoryRepository.findById(id).orElseThrow(
                () -> new  AppException("Category not found by " + id, HttpStatus.NOT_FOUND));

        return CategoryMapper.toDTO(existinCategory);
    }

    @Override
    public CategoryDTO getCategoryByName(String name) throws Exception {
        Category category = categoryRepository.findByName(name);

        if (category == null) {
            throw new AppException("Category not found by name " + name,HttpStatus.NOT_FOUND);
        }

        return CategoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {

        return CategoryMapper.toDTOList(categoryRepository.findByDeletedFalse());
    }

    @Override
    public void deleteCategory(UUID id) throws Exception {
        User user = userService.getCurrentUser();

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new  AppException("Category not found by " + id, HttpStatus.NOT_FOUND));

        category.setDeletedBy(user);
        category.setDeleted(true);

        categoryRepository.save(category);

    }

}
