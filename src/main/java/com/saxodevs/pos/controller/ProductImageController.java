package com.saxodevs.pos.controller;


import com.saxodevs.pos.dto.ProductImageDTO;
import com.saxodevs.pos.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ProductImageController {

    private  final ProductImageService imageService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductImageDTO>> all(){
        return ResponseEntity.ok(imageService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDTO> byId(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(imageService.getById(id));
    }
}
