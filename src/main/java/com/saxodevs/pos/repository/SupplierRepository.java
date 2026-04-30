package com.saxodevs.pos.repository;

import com.saxodevs.pos.model.Supplier;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    @NullMarked
    Optional<Supplier> findById(UUID id);

    @NullMarked
    List<Supplier> findAll();

    List<Supplier> findByDeletedFalse();

    Supplier findByEmail(String email);

    Supplier findByPhoneNumber(String phone);

}
