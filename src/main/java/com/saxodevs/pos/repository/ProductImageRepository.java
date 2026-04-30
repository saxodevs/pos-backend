package com.saxodevs.pos.repository;

import com.saxodevs.pos.model.ProductImage;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    Optional<ProductImage> findById(UUID id);


    List<ProductImage> findAll();
}

