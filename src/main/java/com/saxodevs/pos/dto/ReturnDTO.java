package com.saxodevs.pos.dto;

import com.saxodevs.pos.domain.ReturnStatus;
import com.saxodevs.pos.model.ReturnItem;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ReturnDTO {
    private UUID id;

    private UUID saleId;
    private String reason;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private ReturnStatus status;
    private  String sku;

    private UUID userId;
    private UUID approvedBy;
    private UUID rejectedBy;

    private List<ReturnItemDTO> items;
}
