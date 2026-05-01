package com.saxodevs.pos.controller;


import com.saxodevs.pos.exceptions.ProductException;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.dto.ProductDTO;
import com.saxodevs.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(
            @RequestBody ProductDTO dto

    ) throws Exception {

        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") UUID id, @RequestBody ProductDTO dto) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @PutMapping(value = "/stock/id/{id}", consumes = "application/json")
    public ResponseEntity<ProductDTO> adjust(@PathVariable("id") UUID id, @RequestBody int qty) throws Exception {
        return ResponseEntity.ok(productService.adjustStock(id, qty));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<ProductDTO>> outOfStock() throws ProductException {
        return ResponseEntity.ok(productService.getOutOfStockProduct());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(productService.getById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") UUID id) throws Exception {

        productService.deleteProduct(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");
        apiResponse.setSuccess(true);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProductDTO>> getByKeyword(@PathVariable String keyword) throws Exception {
        return ResponseEntity.ok(productService.getByKeyword(keyword));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductDTO> getByBarcode(@PathVariable String barcode) throws Exception {
        return ResponseEntity.ok(productService.getByBarCode(barcode));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }
    @GetMapping("/expired-product")
    public ResponseEntity<List<ProductDTO>> getExpiryProducts() {
        return ResponseEntity.ok(productService.getExpiryProducts());
    }
}
