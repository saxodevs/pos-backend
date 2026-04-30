package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.domain.ChangeType;
import com.saxodevs.pos.model.InventoryLog;
import com.saxodevs.pos.model.Product;
import com.saxodevs.pos.model.SaleItem;
import com.saxodevs.pos.repository.InventoryLogRepository;
import com.saxodevs.pos.service.InventoryLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LogInventoryImplementation implements InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;

    @Override
    public  void  logInventoryChange(Product product, ChangeType changeType, int quantity) {

        InventoryLog inventoryLog= InventoryLog.builder()
                .product(product)
                .changeType(changeType)
                .qtyChange(quantity)
                .build();

        inventoryLogRepository.save(inventoryLog);

    }

    @Override
    public void logSaleItems(List<SaleItem> items) {
        for (SaleItem item : items){
            logInventoryChange(item.getProduct(),ChangeType.SALE, item.getQuantity());
        }

    }
}
