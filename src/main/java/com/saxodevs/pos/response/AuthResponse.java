package com.saxodevs.pos.response;

import com.saxodevs.pos.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String message;
    private String token;
    private boolean success;

    private UserDTO user;

}
