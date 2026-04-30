package com.saxodevs.pos.mapper;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.dto.PaymentDTO;
import com.saxodevs.pos.model.Payment;
import com.saxodevs.pos.model.Sale;

import java.util.List;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment){
        return PaymentDTO.builder()
                .id(payment.getId())
                .sku(payment.getSku())
                .ref(payment.getRef())
                .method(payment.getMethod())
                .amount(payment.getAmount())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public static Payment toEntity(PaymentDTO dto, Sale sale, PaymentMethod method){
        return Payment.builder()
                .ref(dto.getRef())
                .method(method)
                .amount(dto.getAmount())
                .build();
    }

    public static List<PaymentDTO> toList(List<Payment> payments){
        return  payments.stream().map(PaymentMapper::toDTO).toList();
    }
}
