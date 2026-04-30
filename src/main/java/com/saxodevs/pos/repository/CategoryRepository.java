package com.saxodevs.pos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saxodevs.pos.model.Category;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @NullMarked
    Optional<Category> findById(UUID id);

    Category findByName(String name);

    @NullMarked
    List<Category> findAll();

    List<Category> findByDeletedFalse();

}
