package com.saxodevs.pos.service;

import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.model.Product;

public interface InventoryService {
    void reduceStock(Product product, int quantity) throws Exception;
    void reStock(Product product, int quantity) throws Exception;

    void logRepository(Product product, int quantity);

    void checkReorderLevel(Product product) throws Exception;
}
