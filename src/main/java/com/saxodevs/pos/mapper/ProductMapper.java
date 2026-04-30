package com.saxodevs.pos.mapper;

import java.util.List;

import com.saxodevs.pos.dto.ProductDTO;
import com.saxodevs.pos.model.*;

import lombok.Data;

@Data
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .barcode(product.getBarcode())
                .costPrice(product.getCostPrice())
                .sellingPrice(product.getSellingPrice())
                .stockQuantity(product.getStockQuantity())
                .isExpiryProduct(product.getIsExpiryProduct())
                .expiryDate(product.getExpiryDate())
                .manufacturingDate(product.getManufacturingDate())
                .markUp(product.getMarkUp())
                .images(product.getImages().stream().map(ProductImage::getPath).toList())
                .reorderLevel(product.getReorderLevel())
                .createdBy(product.getCreatedBy() != null ? product.getCreatedBy().getId():null)
                .updatedBy(product.getUpdatedBy() != null ? product.getUpdatedBy().getId():null)
                .categoryId(product.getCategory()!= null ? product.getCategory().getId():null)
                .supplierId(product.getSupplier() != null ? product.getSupplier().getId(): null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO dto, User user, Category category, Supplier supplier) {

        return Product.builder()
                .sku(dto.getSku())
                .name(dto.getName())
                .brand(dto.getBrand())
                .barcode(dto.getBarcode())
                .costPrice(dto.getCostPrice())
                .isExpiryProduct(dto.getIsExpiryProduct())
                .expiryDate(dto.getExpiryDate())
                .manufacturingDate(dto.getManufacturingDate())
                .sellingPrice(dto.getSellingPrice())
                .stockQuantity(dto.getStockQuantity())
                .markUp(dto.getMarkUp())
                .reorderLevel(dto.getReorderLevel())
                .createdBy(user)
                .updatedBy(user)
                .category(category)
                .supplier(supplier)
                .build();

    }

    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream().map(ProductMapper::toDTO).toList();
    }

}
