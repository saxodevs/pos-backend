package com.saxodevs.pos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.dto.CategoryDTO;
import com.saxodevs.pos.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
            @RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> all() throws Exception {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") UUID id, @RequestBody CategoryDTO dto)
            throws Exception {

        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable("id") UUID id)
            throws Exception {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable("name") String name)
            throws Exception {

        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") UUID id) throws Exception {

        categoryService.deleteCategory(id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Category deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

}
