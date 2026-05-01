package com.saxodevs.pos.service.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.model.*;
import com.saxodevs.pos.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saxodevs.pos.dto.ProductDTO;
import com.saxodevs.pos.exceptions.ProductException;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.mapper.ProductMapper;
import com.saxodevs.pos.repository.CategoryRepository;
import com.saxodevs.pos.repository.ProductRepository;
import com.saxodevs.pos.service.ProductService;
import com.saxodevs.pos.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ProductImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final CustomIdGenerator generator;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final SupplierRepository supplierRepository;

    @Override
    public ProductDTO createProduct(ProductDTO dto) throws UserException {

        Product existing = productRepository.findByBarcode(dto.getBarcode());

        if (existing != null) {
            throw new AppException("Product barcode already exists!", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getCurrentUser();

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new AppException("Category not found", HttpStatus.NOT_FOUND));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new AppException("Supplier not found", HttpStatus.NOT_FOUND));

        dto.setSku(generator.generate("PRD"));

        Product product = ProductMapper.toEntity(dto, user, category, supplier);

        List<ProductImage> imageList = Optional.ofNullable(dto.getImages())
                .orElse(List.of())
                .stream()
                .map(url -> {
                    ProductImage img = new ProductImage();
                    img.setPath(url);
                    img.setProduct(product);
                    return img;
                })
                .toList();

        product.setImages(imageList);

        Product saved = productRepository.save(product);

        return ProductMapper.toDTO(saved);
    }
    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO dto) throws Exception {

        User user = userService.getCurrentUser();

        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found by ID " + id));

        existingProduct.setName(dto.getName());
        existingProduct.setBrand(dto.getBrand());
        existingProduct.setBarcode(dto.getBarcode());
        existingProduct.setCostPrice(dto.getCostPrice());
        existingProduct.setSellingPrice(dto.getSellingPrice());
        existingProduct.setStockQuantity(dto.getStockQuantity());
        existingProduct.setMarkUp(dto.getMarkUp());
        existingProduct.setReorderLevel(dto.getReorderLevel());
        existingProduct.setUpdatedBy(user);
        existingProduct.setUpdatedAt(dto.getUpdatedAt());

        Product product = productRepository.save(existingProduct);

        return ProductMapper.toDTO(product);
    }

    @Override
    public ProductDTO adjustStock(UUID id, int qty) throws ProductException {

        User user = userService.getCurrentUser();

        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found by ID " + id));

        existingProduct.setStockQuantity(qty);

        existingProduct.setUpdatedBy(user);
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product product = productRepository.save(existingProduct);

        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getOutOfStockProduct() {

        return ProductMapper.toDTOList(productRepository.findOutOfStockProduct());
    }

    @Override
    public List<ProductDTO> getAllProduct() {

        return ProductMapper.toDTOList(productRepository.findByDeletedFalse());

    }

    @Override
    public void deleteProduct(UUID id) throws ProductException {

        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found by ID " + id));

        existingProduct.setDeleted(true);
        existingProduct.setDeletedBy(userService.getCurrentUser());
        productRepository.save(existingProduct);
    }

    @Override
    public ProductDTO getById(UUID id) throws ProductException {

        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ProductException("Product not found by ID " + id));

        return ProductMapper.toDTO(existingProduct);
    }

    @Override
    public ProductDTO getByBarCode(String barcode) throws ProductException {

        Product existingProduct = productRepository.findByBarcode(barcode);

        if (existingProduct == null) {
            throw new ProductException("Product not found by barcode " + barcode);
        }

        return ProductMapper.toDTO(existingProduct);

    }

    @Override
    public List<ProductDTO> getByKeyword(String keyword) throws ProductException {

        List<Product> products = productRepository.findByKeyword(keyword);

        if (products.isEmpty()) {
            throw new ProductException("Product not found by keyword :" + keyword);
        }

        return ProductMapper.toDTOList(products);
    }

    public List<ProductDTO> getLowStockProducts() {


        return ProductMapper.toDTOList(productRepository.findByStockQuantityLessThanEqualReorderLevel());
    }

    @Override
    public List<ProductDTO> getExpiryProducts() {
        System.out.println(productRepository.findExpiredProducts(LocalDate.now()));
        return ProductMapper.toDTOList(productRepository.findExpiredProducts(LocalDate.now()));
    }

}
