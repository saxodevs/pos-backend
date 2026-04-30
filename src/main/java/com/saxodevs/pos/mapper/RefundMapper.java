package com.saxodevs.pos.mapper;

import com.saxodevs.pos.dto.RefundDTO;
import com.saxodevs.pos.model.Refund;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.ShiftReport;
import com.saxodevs.pos.model.User;

import java.util.List;

public class RefundMapper {

    public static RefundDTO toDTO(Refund refund){
        return RefundDTO.builder()
                .id(refund.getId())
                .sku(refund.getSku())
                .cashierId(refund.getCashier().getId())
                .saleId(refund.getSale().getId())
                .shiftReportId(refund.getShiftReport().getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .paymentMethod(refund.getPaymentMethod())
                .createdAt(refund.getCreatedAt())
                .build();
    }

    public static Refund toEntity(RefundDTO refund, User user, Sale sale, ShiftReport shiftReport){
        return Refund.builder()
                .sku(refund.getSku())
                .cashier(user)
                .sale(sale)
                .shiftReport(shiftReport)
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .paymentMethod(refund.getPaymentMethod())
                .createdAt(refund.getCreatedAt())
                .build();
    }

    public static List<RefundDTO> refundDTOList(List<Refund> refunds){
        return refunds.stream().map(RefundMapper::toDTO).toList();
    }
}
