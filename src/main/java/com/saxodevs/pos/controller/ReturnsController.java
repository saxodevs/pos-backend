package com.saxodevs.pos.controller;

import com.saxodevs.pos.domain.ReturnStatus;
import com.saxodevs.pos.dto.ReturnDTO;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/returns")
public class ReturnsController {

    private final ReturnService returnService;

    @PostMapping("/send-requst")
    public ResponseEntity<ReturnDTO> createReturn(@RequestBody ReturnDTO returnDTO) {
        return ResponseEntity.ok(returnService.createReturn(returnDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnDTO> updateReturn(@PathVariable UUID id, @RequestBody ReturnDTO returnDTO) throws Exception {
        return ResponseEntity.ok(returnService.updateReturn(id, returnDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnDTO> getReturnsById(@PathVariable UUID id) {
        return ResponseEntity.ok(returnService.getReturnById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReturn(@PathVariable UUID id) {

        returnService.deleteReturn(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deleted Return");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReturnDTO>> getAllReturns() {
        return ResponseEntity.ok(returnService.getAllReturns());
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<ReturnDTO>> getReturnsByStatus(@PathVariable ReturnStatus status) {
        return ResponseEntity.ok(returnService.getReturnByStatus(status));
    }

    @GetMapping("/return/{userId}")
    public ResponseEntity<ReturnDTO> getReturnsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(returnService.getReturnByUserId(userId));
    }

    @GetMapping("/today/{userId}")
    public ResponseEntity<List<ReturnDTO>> getReturnsByToday(@PathVariable UUID userId) {

        return ResponseEntity.ok(returnService.getReturnsByUserIdAndToday(userId));
    }


}
