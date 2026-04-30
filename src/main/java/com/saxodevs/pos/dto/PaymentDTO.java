package com.saxodevs.pos.dto;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.model.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private UUID id;

    private String sku;
    private String ref;

    private UUID saleId;

    private PaymentMethod method;

    private BigDecimal amount;

    private LocalDateTime createdAt;
}
