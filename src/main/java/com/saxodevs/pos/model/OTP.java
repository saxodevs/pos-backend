package com.saxodevs.pos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String code;
    private LocalDateTime expiryTime;
    private boolean used;
    private boolean verified;
    private String resetToken;

}
