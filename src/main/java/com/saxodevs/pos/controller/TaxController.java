package com.saxodevs.pos.controller;

import com.saxodevs.pos.dto.TaxDTO;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/taxes")
public class TaxController {

    private final TaxService taxService;

    @PostMapping("/create")
    public ResponseEntity<TaxDTO> create(@RequestBody TaxDTO dto) throws Exception {
        return ResponseEntity.ok(taxService.createTax(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxDTO> update(@PathVariable("id") UUID id, @RequestBody TaxDTO dto) throws Exception {
        return ResponseEntity.ok(taxService.updateTax(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxDTO> getById(@PathVariable("id") UUID id) throws Exception {
        return ResponseEntity.ok(taxService.getTaxById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TaxDTO> getByName(@PathVariable("name") String name) throws Exception {
        return ResponseEntity.ok(taxService.getTaxByName(name));
    }
    @GetMapping("/code/{code}")
    public ResponseEntity<TaxDTO> getByCode(@PathVariable("code") String code) throws Exception {
        return ResponseEntity.ok(taxService.getTaxCode(code));
    }
    @GetMapping("/all")
    public ResponseEntity<List<TaxDTO>> getAll() throws Exception {
        return ResponseEntity.ok(taxService.getAllTax());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> getAll(@PathVariable("id") UUID id) throws Exception {

        taxService.deleteTax(id);

        ApiResponse response = new ApiResponse();

        response.setMessage("Tax deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

}
