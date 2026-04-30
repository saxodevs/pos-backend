package com.saxodevs.pos.repository;

import com.saxodevs.pos.model.Tax;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaxRepository extends JpaRepository<Tax, UUID> {

    @NullMarked
    Optional<Tax> findById(UUID id);

    Tax findByTaxName(String name);

    Tax findByTaxCode(String code);

    @NullMarked
    List<Tax> findAll();

    List<Tax> findByDeletedFalse();
}
