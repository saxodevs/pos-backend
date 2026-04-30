package com.saxodevs.pos.controller;


import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.response.AuthResponse;
import com.saxodevs.pos.service.AuthService;
import com.saxodevs.pos.service.implementation.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody UserDTO dto
    ) throws Exception {
        return ResponseEntity.ok(authService.loginUser(dto));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<AuthResponse> sendOtp(@RequestParam("phone") String phone) {
        otpService.generateOtp(phone);
        AuthResponse response = new AuthResponse();
        response.setMessage("An OTP code has been send to your phone number");
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("phone") String phone,
                            @RequestParam("code") String code) {

        return otpService.verifyOtpAndGenerateToken(phone, code);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("phone") String phone,
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) throws AppException {

        authService.resetPassword(phone, token, newPassword);
        return "Password updated successfully";
    }
}
