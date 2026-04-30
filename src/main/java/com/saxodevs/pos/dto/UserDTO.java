package com.saxodevs.pos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.saxodevs.pos.domain.UserRole;

import lombok.Data;

@Data
public class UserDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phone;

    private String username;

    private String password;

    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}
