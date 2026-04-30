package com.saxodevs.pos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.saxodevs.pos.exceptions.AppException;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saxodevs.pos.domain.UserRole;
import com.saxodevs.pos.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {
    @NullMarked
    Optional<User> findById(UUID id);

    User findByUsername(String username);

    User findByPhone(String phone) throws AppException;

    boolean existsByRole(UserRole userRole);

    @NullMarked
    List<User> findAll();

    List<User> findByDeletedFalse();

}
