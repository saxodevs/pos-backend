package com.saxodevs.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private UUID id;

    private Boolean isDefault;

    private String sku;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private UUID createdBy;

    private UUID updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
