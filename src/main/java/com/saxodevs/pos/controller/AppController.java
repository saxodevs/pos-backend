package com.saxodevs.pos.controller;

import com.saxodevs.pos.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping
    public ResponseEntity<ApiResponse> App(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Welcome to POS System By Saxodevs. Made with ❤️❤️❤️");
        apiResponse.setSuccess(true);
        return ResponseEntity.ok(apiResponse);
    }
}
