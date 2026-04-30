package com.saxodevs.pos.repository;


import com.saxodevs.pos.model.Customer;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @NullMarked
    Optional<Customer> findById(@Param("id") UUID id);

    @Query("SELECT  c FROM Customer c WHERE c.email = :email")
    Customer findEmail(@Param("email") String email);

    @Query("SELECT  c FROM Customer c WHERE c.phoneNumber = :phone")
    Customer findByPhone(@Param("phone") String phone);

    @Query("SELECT  c FROM Customer c WHERE c.firstName = :name OR c.lastName = :name")
    Customer findByName(@Param("name") String name);

    Optional<Customer> findByIsDefaultTrue();

    List<Customer> findByDeletedFalse();

}
