package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.response.InventoryResponse;
import com.antran.Warehouse_management.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {
    InventoryService inventoryService;

    @GetMapping()
    ApiResponse<List<InventoryResponse>> getAllInventory() {
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventoryService.getAllInventory())
                .message("Lấy danh sách tồn kho thành công!")
                .build();
    }
}
