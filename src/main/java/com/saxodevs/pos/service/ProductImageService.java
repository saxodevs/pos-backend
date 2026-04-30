package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.ProductImageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductImageService {

    ProductImageDTO getById(UUID id) throws Exception;
    List<ProductImageDTO> getAll();

}
