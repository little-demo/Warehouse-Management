package com.antran.Warehouse_management.mapper;

import com.antran.Warehouse_management.dto.request.User.UserRequest;
import com.antran.Warehouse_management.dto.request.User.UserUpdateRequest;
import com.antran.Warehouse_management.dto.response.UserResponse;
import com.antran.Warehouse_management.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dob(user.getDob())
                .enabled(user.isEnabled())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserRequest request) {
        if (request == null) return null;
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dob(request.getDob())
                .enabled(true)
                .role(request.getRole())
                .build();
    }
}
