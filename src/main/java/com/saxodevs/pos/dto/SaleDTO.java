package com.saxodevs.pos.dto;

import com.saxodevs.pos.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {

    private UUID id;

    private String sku;

    private UUID cashierId;
    private UUID customerId;

    private List<SaleItemDTO> items;

    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal discount;

    private PaymentMethod paymentMethod;

    private PaymentDTO payment;

    private LocalDateTime createdAt;
    private Boolean deleted;
    private UUID deletedBy;
}
