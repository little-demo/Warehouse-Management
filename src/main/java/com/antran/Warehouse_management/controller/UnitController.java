package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Customer.CustomerRequest;
import com.antran.Warehouse_management.dto.request.Unit.UnitRequest;
import com.antran.Warehouse_management.dto.response.CustomerResponse;
import com.antran.Warehouse_management.dto.response.UnitResponse;
import com.antran.Warehouse_management.service.UnitService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/units")
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitController {
//    UnitService unitService;
//
//    @PostMapping()
//    ApiResponse<UnitResponse> createUnit(@RequestBody @Valid UnitRequest request) {
//        return ApiResponse.<UnitResponse>builder()
//                .result(unitService.createUnit(request))
//                .message("Tạo danh mục mới thành công!")
//                .build();
//    }
//
//    @GetMapping()
//    ApiResponse<List<UnitResponse>> getAllUnits() {
//        return ApiResponse.<List<UnitResponse>>builder()
//                .result(unitService.getAllUnits())
//                .message("Lấy danh sách danh mục thành công!")
//                .build();
//    }
//
//    @GetMapping("/{id}")
//    ApiResponse<UnitResponse> getUnitById(@PathVariable int id) {
//        return ApiResponse.<UnitResponse>builder()
//                .result(unitService.getUnitById(id))
//                .message("Lấy thông tin danh mục thành công!")
//                .build();
//    }
}
