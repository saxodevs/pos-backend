package com.saxodevs.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO {

    private UUID id;
    private String name;
    private String des;
    private String sku;

    private UUID createdById;
    private UUID updatedById;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductDTO> products;
}