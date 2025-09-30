package com.antran.Warehouse_management.controller;

import com.antran.Warehouse_management.dto.ApiResponse;
import com.antran.Warehouse_management.dto.request.User.UserChangePasswordRequest;
import com.antran.Warehouse_management.dto.request.User.UserRequest;
import com.antran.Warehouse_management.dto.request.User.UserUpdateRequest;
import com.antran.Warehouse_management.dto.response.UserResponse;
import com.antran.Warehouse_management.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .message("Tạo user mới thành công!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUserById(@PathVariable int id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .message("Lấy thông tin user thành công!")
                .build();
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .message("Lấy danh sách user thành công!")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable int id, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .message("Cập nhật thông tin user thành công!")
                .build();
    }

    @PutMapping("/toggle/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> changUserStatus(@PathVariable int id) {
        userService.changeUserStatus(id);
        return ApiResponse.<Void>builder()
                .message("Thay đổi trạng thái user thành công!")
                .build();
    }

    @PutMapping("/change-password/{id}")
    ApiResponse<Void> changePassword(@PathVariable int id, @RequestBody @Valid UserChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ApiResponse.<Void>builder()
                .message("Đổi mật khẩu thành công!")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .message("Xoá user thành công!")
                .build();
    }
}
