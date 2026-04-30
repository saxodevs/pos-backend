package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.dto.PaymentDTO;
import com.saxodevs.pos.dto.SaleDTO;
import com.saxodevs.pos.mapper.PaymentMapper;
import com.saxodevs.pos.mapper.SaleMapper;
import com.saxodevs.pos.model.Payment;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.repository.PaymentRepository;
import com.saxodevs.pos.repository.SaleRepository;
import com.saxodevs.pos.service.PaymentService;
import com.saxodevs.pos.service.SaleService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentImplementation implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SaleRepository saleRepository;
    private final CustomIdGenerator generator;
    private final UserService userService;
    private SaleService saleService;

    public PaymentDTO processPayment(PaymentDTO paymentDTO) {

        if (paymentDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }


        Payment payment = Payment.builder()
                .sku(generator.generate("PYM"))
                .ref(paymentDTO.getRef())
                .method(paymentDTO.getMethod())
                .amount(paymentDTO.getAmount())
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentMapper.toDTO(savedPayment);
    }

    @Override
    public List<PaymentDTO> getPayment() {

        return PaymentMapper.toList(paymentRepository.findAll());
    }

    @Override
    public PaymentDTO getById(UUID id) throws Exception {

        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new Exception("Payment not found")
        );
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public List<PaymentDTO> getByMethod(PaymentMethod method) {
        List<Payment> payments = paymentRepository.findByMethod(method);
        return PaymentMapper.toList(payments);
    }

    @Override
    public void deletePayment(UUID id) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new Exception("Payment not found")
        );
        payment.setDeletedBy(userService.getCurrentUser());
        payment.setDeleted(true);
        paymentRepository.save(payment);


    }

    @Override
    public List<PaymentDTO> getPaymentByDate(LocalDateTime createdAt) throws Exception {
        List<Payment> payments = paymentRepository.findByCreatedAt(createdAt);
        return PaymentMapper.toList(payments);
    }

    @Override
    public List<PaymentDTO> getAllPayment() {
        List<Payment> paymentList = paymentRepository.findByDeletedFalse();
        return PaymentMapper.toList(paymentList);
    }
}
