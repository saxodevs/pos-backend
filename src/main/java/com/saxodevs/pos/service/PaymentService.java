package com.saxodevs.pos.service;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.dto.PaymentDTO;
import com.saxodevs.pos.model.Payment;
import com.saxodevs.pos.model.Sale;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface PaymentService {

   PaymentDTO processPayment(PaymentDTO paymentDTO);

    List<PaymentDTO> getPayment();

    PaymentDTO getById(UUID ID) throws Exception;

    List<PaymentDTO> getByMethod(PaymentMethod method);

    void deletePayment(UUID ID) throws Exception;

    List<PaymentDTO> getPaymentByDate(LocalDateTime createdAt) throws Exception;

    List<PaymentDTO> getAllPayment();
}
