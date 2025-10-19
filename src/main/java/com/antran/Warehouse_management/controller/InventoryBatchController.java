package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.response.InventoryResponse;
import com.antran.Warehouse_management.service.InventoryBatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-batches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryBatchController {
    InventoryBatchService inventoryBatchService;

    @GetMapping("/{id}")
    ApiResponse<InventoryResponse> getInventoryBatchById(int id) {
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryBatchService.getInventoryBatchById(id))
                .message("Lấy lô hàng tồn kho thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<InventoryResponse>> getAllInventoryBatches() {
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventoryBatchService.getAllInventoryBatches())
                .message("Lấy danh sách lô hàng tồn kho thành công!")
                .build();
    }

    @GetMapping("/product/{productId}")
    ApiResponse<List<InventoryResponse>> getInventoryBatchesByProductId(@PathVariable int productId) {
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventoryBatchService.getInventoryBatchesByProductId(productId))
                .message("Lấy danh sách lô hàng tồn kho theo sản phẩm thành công!")
                .build();
    }

    @GetMapping("/batch/{batchCode}")
    ApiResponse<InventoryResponse> getInventoryBatchByBatchCode(@PathVariable String batchCode) {
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryBatchService.getInventoryBatchByBatchCode(batchCode))
                .message("Lấy lô hàng tồn kho theo mã lô thành công!")
                .build();
    }
}
