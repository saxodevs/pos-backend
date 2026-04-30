package com.saxodevs.pos.dto;

import com.saxodevs.pos.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaxDTO {

    private UUID id;

    private String taxCode;

    private String taxName;

    private Double taxRate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UUID createdBy;

    private UUID updatedBy;
}
