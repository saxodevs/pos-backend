package com.saxodevs.pos.controller;


import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.dto.CustomerDTO;
import com.saxodevs.pos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO dto) throws Exception {
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable("id") UUID id, @RequestBody CustomerDTO dto) throws Exception {
        return ResponseEntity.ok(customerService.updateCustomer(id, dto));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAll() throws Exception {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getEmail(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CustomerDTO> getName(@PathVariable("name") String name) throws Exception {
        return ResponseEntity.ok(customerService.getByName(name));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerDTO> getPhone(@PathVariable("phone") String phone) throws Exception {
        return ResponseEntity.ok(customerService.getByPhone(phone));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") UUID id) throws Exception {

        customerService.deleteCustomer(id);

        ApiResponse response = new ApiResponse();

        response.setMessage("Customer deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }


}
