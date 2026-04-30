package com.saxodevs.pos.repository;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.model.Payment;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findById(UUID id);

    @NullMarked
    List<Payment> findAll();

    List<Payment> findByDeletedFalse();

    List<Payment> findByMethod(PaymentMethod method);

    List<Payment> findByCreatedAt(LocalDateTime createdAt);



}
