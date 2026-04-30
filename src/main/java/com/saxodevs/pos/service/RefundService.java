package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RefundService {

    RefundDTO createRefund(RefundDTO refund);

    List<RefundDTO> getAAllRefund();

    RefundDTO getRefundByCashier(UUID id);

    RefundDTO getRefundByShiftReport(UUID id);

    List<RefundDTO> getRefundByCashierAndCreatedAt(UUID id, LocalDateTime startDate, LocalDateTime endDate);

    RefundDTO getById(UUID id);

    void deleteRefund(UUID id);
}
