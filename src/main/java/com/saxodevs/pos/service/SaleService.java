package com.saxodevs.pos.service;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.dto.SaleDTO;
import com.saxodevs.pos.dto.SalesReportDTO;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    SaleDTO createSale(SaleDTO dto) throws Exception;

    SaleDTO getSaleById(UUID saleId) throws Exception;

    List<SaleDTO> getSaleByCashier(UUID cahierId) throws Exception;

    List<SaleDTO> getSaleByCustomer(UUID customerId) throws Exception;

    List<SaleDTO> getSaleByPaymentMethod(PaymentMethod paymentMethod) throws Exception;

    List<SaleDTO> getAll() throws Exception;

    List<SaleDTO> getSalesByCashier(UUID id) throws Exception;

    List<SalesReportDTO> getDailySaleReport();

    List<SalesReportDTO> getMonthlySaleReport();

    void deleteSale(UUID saleId) throws Exception;
}
