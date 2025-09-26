package com.antran.Warehouse_management.service;

import com.antran.Warehouse_management.dto.request.User.UserChangePasswordRequest;
import com.antran.Warehouse_management.dto.request.User.UserRequest;
import com.antran.Warehouse_management.dto.request.User.UserUpdateRequest;
import com.antran.Warehouse_management.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(int id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(int id, UserUpdateRequest request);
    void changeUserStatus(int id);
    void changePassword(int id, UserChangePasswordRequest request);
    void deleteUser(int id);
}
