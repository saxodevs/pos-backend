package com.saxodevs.pos.service.implementation;


import com.saxodevs.pos.dto.ProductImageDTO;
import com.saxodevs.pos.mapper.ProductImageMapper;
import com.saxodevs.pos.model.ProductImage;
import com.saxodevs.pos.repository.ProductImageRepository;
import com.saxodevs.pos.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageImplementation implements ProductImageService {

    private  final ProductImageRepository imageRepository;
    @Override
    public ProductImageDTO getById(UUID id) throws Exception {

        ProductImage image = imageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product image not found")
        );
        return ProductImageMapper.dto(image);
    }

    @Override
    public List<ProductImageDTO> getAll() {
        return ProductImageMapper.dtoList(imageRepository.findAll());
    }
}
