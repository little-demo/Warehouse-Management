package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Warehouse.WarehouseRequest;
import com.antran.Warehouse_management.dto.response.WarehouseResponse;
import com.antran.Warehouse_management.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/warehouses")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseController {
//    WarehouseService warehouseService;
//
//    @PostMapping()
//    ApiResponse<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest request) {
//        return ApiResponse.<WarehouseResponse>builder()
//                .result(warehouseService.createWarehouse(request))
//                .message("Tạo kho mới thành công!")
//                .build();
//    }
//
//    @GetMapping()
//    ApiResponse<List<WarehouseResponse>> getAllWarehouses() {
//        return ApiResponse.<List<WarehouseResponse>>builder()
//                .result(warehouseService.getAllWarehouses())
//                .message("Lấy danh sách kho thành công!")
//                .build();
//    }
//
//    @GetMapping("/{id}")
//    ApiResponse<WarehouseResponse> getWarehouseById(@PathVariable int id) {
//        return ApiResponse.<WarehouseResponse>builder()
//                .result(warehouseService.getWarehouseById(id))
//                .message("Lấy thông tin kho thành công!")
//                .build();
//    }
//
//    @PutMapping("/{id}")
//    ApiResponse<WarehouseResponse> updateWarehouse(@PathVariable int id, @RequestBody @Valid WarehouseRequest request) {
//        return ApiResponse.<WarehouseResponse>builder()
//                .result(warehouseService.updateWarehouse(id, request))
//                .message("Cập nhật thông tin kho thành công!")
//                .build();
//    }
//
//    @PutMapping("/toggle/{id}")
//    ApiResponse<Void> disableWarehouse(@PathVariable int id) {
//        warehouseService.disableWarehouse(id);
//        return ApiResponse.<Void>builder()
//                .message("Thay đổi trạng thái kho thành công!")
//                .build();
//    }
//
//    @DeleteMapping("/{id}")
//    ApiResponse<Void> deleteWarehouse(@PathVariable int id) {
//        warehouseService.deleteWarehouse(id);
//        return ApiResponse.<Void>builder()
//                .message("Xoá kho thành công!")
//                .build();
//    }
}
