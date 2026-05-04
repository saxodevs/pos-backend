package com.saxodevs.pos.repository;

import com.saxodevs.pos.domain.ReturnStatus;
import com.saxodevs.pos.model.Returns;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReturnRepository extends JpaRepository<Returns, UUID> {


    @NonNull
    Optional<Returns> findById(UUID uuid);

    @NonNull
    List<Returns> findAll();

    List<Returns> findByStatus(ReturnStatus status);
    List<Returns> findByUserIdAndCreatedAtBetween(
            UUID userId,
            LocalDateTime start,
            LocalDateTime end
    );
    List<Returns> findByUserId(UUID id);
    
}
