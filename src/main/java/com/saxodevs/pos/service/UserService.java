package com.saxodevs.pos.service;

import java.util.List;
import java.util.UUID;

import com.saxodevs.pos.request.ChangePasswordRequest;
import com.saxodevs.pos.response.AuthResponse;
import org.springframework.stereotype.Service;

import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.model.User;

@Service
public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;

    User getCurrentUser() throws UserException;

    UserDTO createUser(UserDTO userDTO) throws UserException;

    UserDTO getUserById(UUID id) throws UserException;

    List<UserDTO> getAllUsers() throws UserException;

    void deleteUserById(UUID id) throws UserException;

    UserDTO updateUser(UUID id, UserDTO userDto) throws UserException;

    UserDTO changePassword(ChangePasswordRequest request) throws Exception;
}
