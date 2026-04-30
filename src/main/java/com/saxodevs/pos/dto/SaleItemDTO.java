package com.saxodevs.pos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemDTO {

    private UUID id;
    private String sku;

    private UUID productId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
