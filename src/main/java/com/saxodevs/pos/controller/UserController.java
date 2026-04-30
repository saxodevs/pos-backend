package com.saxodevs.pos.controller;

import java.util.List;
import java.util.UUID;

import com.saxodevs.pos.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.mapper.UserMapper;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(
            @RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll(
            ) throws UserException {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserDTO> getById(
            @PathVariable("id") UUID id) throws UserException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUerFromJwtToken(
            @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.getUserFromJwtToken(token);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }



    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("id") UUID id
      ) throws UserException {

        userService.deleteUserById(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User deleted successfully");
        apiResponse.setSuccess(true);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordRequest request) throws Exception {

        return  ResponseEntity.ok(userService.changePassword(request));
    }
}
