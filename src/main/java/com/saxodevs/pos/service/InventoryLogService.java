package com.saxodevs.pos.service;

import com.saxodevs.pos.domain.ChangeType;
import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.model.SaleItem;

import java.util.List;

public interface InventoryLogService {

    void logInventoryChange(Product product, ChangeType changeType, int quantity);
    void logSaleItems(List<SaleItem> items);


}
