package com.saxodevs.pos.service;

import java.util.List;
import java.util.UUID;

import com.saxodevs.pos.dto.ProductDTO;
import com.saxodevs.pos.exceptions.ProductException;
import com.saxodevs.pos.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductDTO createProduct(ProductDTO dto) throws ProductException, Exception;

    ProductDTO updateProduct(UUID id, ProductDTO dto) throws ProductException, Exception;

    ProductDTO adjustStock(UUID id, int qty) throws ProductException;

    List<ProductDTO> getOutOfStockProduct() throws ProductException;

    List<ProductDTO> getAllProduct();

    void deleteProduct(UUID id) throws ProductException;

    ProductDTO getById(UUID id) throws ProductException;

    ProductDTO getByBarCode(String barcode) throws ProductException;

    List<ProductDTO> getByKeyword(String keyword) throws ProductException;

    List<ProductDTO> getLowStockProducts();
    List<ProductDTO> getExpiryProducts();

}
