package com.saxodevs.pos.dto;

import com.saxodevs.pos.domain.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RefundDTO {
    private UUID id;
    private String sku;

    private UUID saleId;

    private String reason;

    private BigDecimal amount;

    private UUID shiftReportId;

    private UUID cashierId;

    private LocalDateTime createdAt;

    private PaymentMethod paymentMethod;
}
