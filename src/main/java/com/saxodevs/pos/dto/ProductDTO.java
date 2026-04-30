package com.saxodevs.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.saxodevs.pos.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private UUID id;

    private String sku;

    private String name;

    private String brand;

    private String barcode;

    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    private int stockQuantity;

    private BigDecimal markUp;

    private int reorderLevel;

    private UUID createdBy;

    private UUID updatedBy;

    private UUID categoryId;
    private UUID supplierId;
    private List<String> images;

    private Boolean isExpiryProduct;

    private LocalDate expiryDate;
    private LocalDate manufacturingDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
