package com.saxodevs.pos.service.implementation;

import java.time.LocalDateTime;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.model.OTP;
import com.saxodevs.pos.repository.OTPRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saxodevs.pos.config.JwtProvider;
import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.mapper.UserMapper;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.UserRepository;
import com.saxodevs.pos.response.AuthResponse;
import com.saxodevs.pos.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthService {

    private final UserRepository userRepository;
    private final OTPRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider provider;

    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse loginUser(UserDTO userDto) throws Exception {

        String username = userDto.getUsername();
        String password = userDto.getPassword();

        UserDetails userDetails;

        try {
            userDetails = customUserImplementation.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            throw new AppException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }


        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AppException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = provider.generateToken(authentication);

        User existingUser = userRepository.findByUsername(username);

        existingUser.setLastLoginAt(LocalDateTime.now());
        userRepository.save(existingUser);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwt);
        authResponse.setMessage("Logged in successfully.");
        authResponse.setSuccess(true);
        authResponse.setUser(UserMapper.toDTO(existingUser));

        return authResponse;
    }

    @Override
    public AuthResponse resetPassword(String phone, String token, String newPassword) throws AppException {

        OTP otp = otpRepository
                .findTopByPhoneNumberOrderByExpiryTimeDesc(phone)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.isVerified() || !token.equals(otp.getResetToken())) {
            throw new AppException("Unauthorized password reset", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByPhone(phone);

        if (user == null) {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }
        System.out.println("newPassword: " + newPassword);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        System.out.println("Encoded password: " + passwordEncoder.encode(newPassword));

        otp.setResetToken(null);
        otp.setVerified(false);
        otpRepository.save(otp);

        return null;
    }


}
