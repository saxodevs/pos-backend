package com.saxodevs.pos.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnItemDTO {

    private UUID id;

    private UUID returnsId;

    private UUID productId;

    private int quantity;
}
