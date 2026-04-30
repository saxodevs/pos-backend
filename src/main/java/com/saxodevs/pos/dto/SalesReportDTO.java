package com.saxodevs.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportDTO {

    private String label; // day or month
    private Double revenue;
    private Long sales;
}
