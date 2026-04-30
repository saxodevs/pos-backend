package com.saxodevs.pos.service;

import com.saxodevs.pos.domain.ChangeType;
import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.model.SaleItem;
import com.saxodevs.pos.model.ShiftReport;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryLogService {

    void logInventoryChange(Product product, ChangeType changeType, int quantity);
    void logSaleItems(List<SaleItem> items);


}
