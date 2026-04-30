package com.saxodevs.pos.controller;

import com.saxodevs.pos.dto.SupplierDTO;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/create")
    public ResponseEntity<SupplierDTO> create(@RequestBody SupplierDTO supplierDTO) throws Exception {
        return ResponseEntity.ok(supplierService.addSupplier(supplierDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupplierDTO>> all() throws Exception {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<SupplierDTO> update(@PathVariable("id") UUID id, @RequestBody SupplierDTO supplierDTO) throws Exception {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") UUID id) throws Exception {

        supplierService.deleteSupplier(id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Supplier deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<SupplierDTO> getById(@PathVariable("id") UUID id) throws  Exception{
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @GetMapping("email/{email}")
    public ResponseEntity<SupplierDTO> getByEmail(@PathVariable("email") String email) throws  Exception{
        return ResponseEntity.ok(supplierService.getByEmail(email));
    }

    @GetMapping("phone/{phone}")
    public ResponseEntity<SupplierDTO> getByPhone(@PathVariable("phone") String phone) throws  Exception{
        return ResponseEntity.ok(supplierService.getByPhoneNumber(phone));
    }
}
