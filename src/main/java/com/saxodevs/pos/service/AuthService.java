package com.saxodevs.pos.service;

import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.response.AuthResponse;

public interface AuthService {
    AuthResponse loginUser(UserDTO request) throws Exception;

    AuthResponse resetPassword(String phone, String token, String newPassword) throws AppException;
}
