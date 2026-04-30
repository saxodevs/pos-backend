package com.saxodevs.pos.controller;

import com.saxodevs.pos.dto.RefundDTO;
import com.saxodevs.pos.response.ApiResponse;
import com.saxodevs.pos.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/create")
    public ResponseEntity<RefundDTO> create(@RequestBody RefundDTO dto){
        return ResponseEntity.ok(refundService.createRefund(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefundDTO>> getAll(){
        return ResponseEntity.ok(refundService.getAAllRefund());
    }

    @GetMapping("/cashier/{id}")
    public ResponseEntity<RefundDTO> getByCahierId(@PathVariable("id")UUID id){
        return ResponseEntity.ok(refundService.getRefundByCashier(id));
    }

    @GetMapping("/shift-report/{id}")
    public ResponseEntity<RefundDTO> getShiftReportId(@PathVariable("id")UUID id){
        return ResponseEntity.ok(refundService.getRefundByShiftReport(id));
    }

    @GetMapping("/cashier/{id}/range")
    public ResponseEntity<List<RefundDTO>> getByDateRange(
            @PathVariable("id")UUID id,
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate

            ){
        return ResponseEntity.ok(refundService.getRefundByCashierAndCreatedAt(id, startDate, endDate));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id")UUID id){

        refundService.deleteRefund(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Refund deleted successfully");
        apiResponse.setSuccess(true);

        return ResponseEntity.ok(apiResponse);
    }
}
