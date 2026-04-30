package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.model.OTP;
import com.saxodevs.pos.repository.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OTPRepository otpRepository;
    private final SmsService smsService;

    public void generateOtp(String phone) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        OTP entity = new OTP();
        entity.setPhoneNumber(phone);
        entity.setCode(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        entity.setUsed(false);

        otpRepository.save(entity);

        smsService.sendOtp(phone, "Your password reset OTP is: " + otp+". Do not share with any one.");

    }

    public String verifyOtpAndGenerateToken(String phone, String code) {
        OTP otp = otpRepository
                .findTopByPhoneNumberOrderByExpiryTimeDesc(phone)
                .orElseThrow(() -> new AppException("OTP not found", HttpStatus.NOT_FOUND));

        if (otp.isUsed() || otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new AppException("OTP expired or used", HttpStatus.BAD_REQUEST);
        }

        if (!otp.getCode().equals(code)) {
            throw new AppException("Invalid OTP", HttpStatus.BAD_REQUEST);
        }

        otp.setUsed(true);
        otp.setVerified(true);


        String token = UUID.randomUUID().toString();
        otp.setResetToken(token);

        otpRepository.save(otp);

        return token;
    }
}
