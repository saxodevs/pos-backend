package com.saxodevs.pos.controller;

import com.saxodevs.pos.dto.PaymentDTO;
import com.saxodevs.pos.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/payments")
public class PaymentController {

    private  final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> create(@RequestBody PaymentDTO paymentDTO){
        return ResponseEntity.ok(paymentService.processPayment(paymentDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> all(){
        return ResponseEntity.ok(paymentService.getAllPayment());
    }
}
