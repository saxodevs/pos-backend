package com.saxodevs.pos.pricing;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class Pricing {

    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal finalTotal;
}
