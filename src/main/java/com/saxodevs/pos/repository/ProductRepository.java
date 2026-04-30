package com.saxodevs.pos.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saxodevs.pos.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @NullMarked
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") UUID id);

    @Query("SELECT p FROM Product p WHERE p.barcode = :barcode")
    Product findByBarcode(@Param("barcode") String barcode);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.reorderLevel")
    List<Product> findByStockQuantityLessThanEqualReorderLevel();

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE " +
            "LOWER(CONCAT('%', :query, '%')) OR LOWER(p.brand) LIKE " +
            "LOWER(CONCAT('%', :query, '%')) OR LOWER(p.barcode) LIKE " +
            "LOWER(CONCAT('%', :query, '%'))")
    List<Product> findByKeyword(@Param("query") String query);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity = 0")
    List<Product> findOutOfStockProduct();

    @Query("SELECT e FROM Product e WHERE e.expiryDate IS NOT NULL AND e.expiryDate <= :date")
    List<Product> findExpiredProducts(@Param("date") LocalDate date);

    @NullMarked
    List<Product> findAll();

    List<Product> findByDeletedFalse();

}
