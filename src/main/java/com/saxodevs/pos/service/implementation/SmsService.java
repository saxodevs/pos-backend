package com.saxodevs.pos.service.implementation;

import com.saxodevs.pos.exceptions.AppException;
import com.saxodevs.pos.exceptions.UserException;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${arkesel.api.key}")
    private String apiKey;

    private final UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOtp(String phone, String message) {


        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new AppException("Phone number doesn't exist.", HttpStatus.NOT_FOUND);
        }

        String url = "https://sms.arkesel.com/sms/api" +
                "?action=send-sms" +
                "&api_key=" + apiKey +
                "&to=" + phone +
                "&from=POS" +
                "&sms=" + message;

        restTemplate.getForObject(url, String.class);
    }
}
