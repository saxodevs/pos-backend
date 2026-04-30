package com.saxodevs.pos.dto;

import com.saxodevs.pos.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductImageDTO {
    private UUID id;
    private  String path;
    private UUID product;
}
