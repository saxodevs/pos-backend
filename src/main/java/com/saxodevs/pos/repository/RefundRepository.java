package com.saxodevs.pos.repository;

import com.saxodevs.pos.model.Refund;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<Refund, UUID> {

    @Override
    @NullMarked
    Optional<Refund> findById(UUID uuid);

    Refund findByCashier(UUID id);

    Refund findByShiftReport(UUID id);

    List<Refund> findRefundByCashierAndCreatedAt(UUID id, LocalDateTime startDate, LocalDateTime endDate);

    @NullMarked
    List<Refund> findAll();


}
