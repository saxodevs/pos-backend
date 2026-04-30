package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.model.SaleItem;
import com.saxodevs.pos.pricing.Pricing;
import com.saxodevs.pos.service.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PricingImplementation implements PricingService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.125");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.02");

    @Override
    public Pricing calculate(List<SaleItem> items) {

        BigDecimal subtotal = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (SaleItem item : items) {
            BigDecimal itemTotal = item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            subtotal = subtotal.add(itemTotal);
            totalQuantity += item.getQuantity();
        }


        BigDecimal discount = BigDecimal.ZERO;
        if (totalQuantity >= 3) {
            discount = subtotal.multiply(DISCOUNT_RATE);
        }

        BigDecimal afterDiscount = subtotal.subtract(discount);

        BigDecimal tax = afterDiscount.multiply(TAX_RATE);

        BigDecimal finalTotal = afterDiscount.add(tax);

        return Pricing.builder()
                .subtotal(subtotal.setScale(2, RoundingMode.HALF_UP))
                .discount(discount.setScale(2, RoundingMode.HALF_UP))
                .tax(tax.setScale(2, RoundingMode.HALF_UP))
                .finalTotal(finalTotal.setScale(2, RoundingMode.HALF_UP))
                .build();
    }
}
