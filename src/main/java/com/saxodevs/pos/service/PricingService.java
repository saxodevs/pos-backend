package com.saxodevs.pos.service;

import com.saxodevs.pos.model.SaleItem;
import com.saxodevs.pos.pricing.Pricing;

import java.util.List;

public interface PricingService {

    Pricing calculate(List<SaleItem> items);
}