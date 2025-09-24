package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.Location.LocationRequest;
import com.antran.Warehouse_management.dto.response.LocationResponse;
import com.antran.Warehouse_management.service.LocationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationController {
    LocationService locationService;

    @PostMapping()
    ApiResponse<LocationResponse> createLocation(@RequestBody @Valid LocationRequest request) {
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.createLocation(request))
                .message("Tạo vị trí mới thành công!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<LocationResponse>> getAllLocations() {
        return ApiResponse.<List<LocationResponse>>builder()
                .result(locationService.getAllLocations())
                .message("Lấy danh sách vị trí thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<LocationResponse> getLocationById(@PathVariable int id) {
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.getLocationById(id))
                .message("Lấy thông tin vị trí thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LocationResponse> updateLocation(@PathVariable int id, @RequestBody @Valid LocationRequest request) {
        return ApiResponse.<LocationResponse>builder()
                .result(locationService.updateLocation(id, request))
                .message("Cập nhật thông tin vị trí thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteLocation(@PathVariable int id) {
        locationService.deleteLocation(id);
        return ApiResponse.<Void>builder()
                .message("Xoá vị trí thành công!")
                .build();
    }
}
