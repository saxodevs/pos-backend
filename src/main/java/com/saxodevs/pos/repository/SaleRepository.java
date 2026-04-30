package com.saxodevs.pos.repository;

import com.saxodevs.pos.domain.PaymentMethod;
import com.saxodevs.pos.model.Sale;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    @NullMarked
    @Query("SELECT s FROM Sale s WHERE s.id = :id")
    Optional<Sale> findById(@Param("id") UUID id);

    @Query("SELECT c FROM Sale c WHERE c.user.id = :cashierId")
    List<Sale> findByCashierId(@Param("cashierId") UUID cashierId);

    @Query("SELECT s FROM Sale s WHERE s.customer = :customer")
    List<Sale> findByCustomer(@Param("customer")UUID customer);


    @Query("SELECT p FROM Sale p WHERE p.paymentMethod = :payment")
    List<Sale> findByPaymentMethod(@Param("payment") PaymentMethod payment);

    @NullMarked
    List<Sale> findAll();

    List<Sale> findByDeletedFalse();

    @Query("SELECT  s FROM Sale s WHERE s.user = :cashierId GROUP BY DATE(s.createdAt)" )
    List<Sale> getSalesByCashier(@Param("cashierId")  UUID cashierId);

    // DAILY
    @Query("""
    SELECT DATE(s.createdAt), SUM(s.totalAmount), COUNT(s.id)FROM Sale s GROUP BY DATE(s.createdAt) ORDER BY DATE(s.createdAt)""")
    List<Object[]> getDailySalesReport();


    // MONTHLY
    @Query("""
    SELECT MONTH(s.createdAt), SUM(s.totalAmount),COUNT(s.id)FROM Sale s GROUP BY MONTH(s.createdAt) ORDER BY MONTH(s.createdAt)""")
    List<Object[]> getMonthlySalesReport();
}
