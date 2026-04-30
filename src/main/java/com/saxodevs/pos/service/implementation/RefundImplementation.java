package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.dto.RefundDTO;
import com.saxodevs.pos.mapper.RefundMapper;
import com.saxodevs.pos.model.Refund;
import com.saxodevs.pos.model.Sale;
import com.saxodevs.pos.model.ShiftReport;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.RefundRepository;
import com.saxodevs.pos.repository.SaleRepository;
import com.saxodevs.pos.repository.ShiftReportRepository;
import com.saxodevs.pos.service.InventoryLogService;
import com.saxodevs.pos.service.RefundService;
import com.saxodevs.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundImplementation implements RefundService {

    private final UserService userService;
    private final RefundRepository refundRepository;
    private final SaleRepository saleRepository;
    private final ShiftReportRepository shiftReportRepository;
    private final CustomIdGenerator generator;

    @Override
    public RefundDTO createRefund(RefundDTO refund) {

        User user = userService.getCurrentUser();

        Sale sale = saleRepository.findById(refund.getSaleId()).orElseThrow(
                () -> new RuntimeException("Sale not found by ID: " + refund.getSaleId())
        );

        ShiftReport shiftReport = shiftReportRepository.findById(refund.getShiftReportId()).orElseThrow(
                () -> new RuntimeException("Shift report not found by ID: " + refund.getShiftReportId())
        );

        refund.setSku(generator.generate("RFD"));

        Refund newRefund = RefundMapper.toEntity(refund, user, sale, shiftReport);

        return RefundMapper.toDTO(refundRepository.save(newRefund));
    }

    @Override
    public List<RefundDTO> getAAllRefund() {
        return RefundMapper.refundDTOList(refundRepository.findAll());
    }

    @Override
    public RefundDTO getRefundByCashier(UUID cashierId) {
        Refund cashier = refundRepository.findByCashier(cashierId);

        if (cashier == null) {
            throw new RuntimeException("Cashier not found by ID: " + cashierId);
        }

        return RefundMapper.toDTO(cashier);
    }

    @Override
    public RefundDTO getRefundByShiftReport(UUID reportId) {

        Refund report = refundRepository.findByShiftReport(reportId);
        return RefundMapper.toDTO(report);
    }



    @Override
    public List<RefundDTO> getRefundByCashierAndCreatedAt(UUID cashier, LocalDateTime startDate, LocalDateTime endDate) {

        List<Refund> refunds = refundRepository.findRefundByCashierAndCreatedAt(cashier, startDate, endDate);
        return RefundMapper.refundDTOList(refunds);
    }

    @Override
    public RefundDTO getById(UUID id) {
        Refund refund = refundRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Refund not found")
        );
        return RefundMapper.toDTO(refund);
    }

    @Override
    public void deleteRefund(UUID id) {
        Refund refund = refundRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Refund not found")
        );
        refundRepository.delete(refund);

    }
}
