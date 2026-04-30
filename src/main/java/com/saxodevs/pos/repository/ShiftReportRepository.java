package com.saxodevs.pos.repository;

import com.saxodevs.pos.model.ShiftReport;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShiftReportRepository extends JpaRepository<ShiftReport, UUID> {

    @NullMarked
    Optional<ShiftReport> findById(UUID id);
}