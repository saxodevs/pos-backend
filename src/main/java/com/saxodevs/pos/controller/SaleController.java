package com.saxodevs.pos.controller;


import com.saxodevs.pos.dto.SalesReportDTO;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.dto.SaleDTO;
import com.saxodevs.pos.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class SaleController {


    private final SaleService saleService;


    @PostMapping("/create")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO dto) throws Exception {
        return ResponseEntity.ok(saleService.createSale(dto));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SaleDTO> getById(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }


    @GetMapping("/customer/{id}")
    public ResponseEntity<List<SaleDTO>> getByCustomer(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(saleService.getSaleByCustomer(id));
    }

    @GetMapping("/report/daily")
    public ResponseEntity<List<SalesReportDTO>> getReportDaily() {
        return ResponseEntity.ok(saleService.getDailySaleReport());
    }

    @GetMapping("/report/monthly")
    public ResponseEntity<List<SalesReportDTO>> getReportMonthly() {
        return ResponseEntity.ok(saleService.getMonthlySaleReport());
    }

    @GetMapping("/sales-by-cashier")
    public ResponseEntity<List<SaleDTO>> getSaleByCashier(UUID id) throws Exception {
        return ResponseEntity.ok(saleService.getSalesByCashier(id));
    }


    @GetMapping("/all")
    public ResponseEntity<List<SaleDTO>> getAllSale() throws Exception {
        return ResponseEntity.ok(saleService.getAll());
    }


    @GetMapping("/cashier/{id}")
    public ResponseEntity<List<SaleDTO>> getByCashier(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(saleService.getSaleByCashier(id));
    }

    @GetMapping("/payment/{payment}")
    public ResponseEntity<List<SaleDTO>> getByCustomer(@PathVariable("payment") PaymentMethod payment) throws Exception {
        return ResponseEntity.ok(saleService.getSaleByPaymentMethod(payment));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") UUID id) throws Exception {

        saleService.deleteSale(id);

        ApiResponse response = new ApiResponse();

        response.setMessage("Sale deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }


}
