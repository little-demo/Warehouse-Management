package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Adjustment.AdjustmentRequest;
import com.antran.Warehouse_management.dto.response.AdjustmentResponse;
import com.antran.Warehouse_management.service.AdjustmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjustments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdjustmentController {
    AdjustmentService adjustmentService;

    @PostMapping()
    ApiResponse<AdjustmentResponse> createAdjustment(@RequestBody AdjustmentRequest request) {
        return ApiResponse.<AdjustmentResponse>builder()
                .result(adjustmentService.createAdjustment(request))
                .message("Tạo phiếu điều chỉnh thành công")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<AdjustmentResponse> getAdjustmentById(@PathVariable int id) {
        return ApiResponse.<AdjustmentResponse>builder()
                .result(adjustmentService.getAdjustmentById(id))
                .message("Lấy thông tin phiếu điều chỉnh thành công")
                .build();
    }

    @GetMapping()
    ApiResponse<List<AdjustmentResponse>> getAllAdjustments() {
        return ApiResponse.<List<AdjustmentResponse>>builder()
                .result(adjustmentService.getAllAdjustments())
                .message("Lấy danh sách phiếu điều chỉnh thành công")
                .build();
    }

    @PutMapping("/cancel/{id}")
    ApiResponse<Void> cancelAdjustment(@PathVariable int id) {
        adjustmentService.cancelAdjustment(id);
        return ApiResponse.<Void>builder()
                .message("Hủy phiếu điều chỉnh thành công")
                .build();
    }
}
