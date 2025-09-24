package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.User.UserChangePasswordRequest;
import com.antran.Warehouse_management.dto.request.User.UserRequest;
import com.antran.Warehouse_management.dto.request.User.UserUpdateRequest;
import com.antran.Warehouse_management.dto.response.UserResponse;
import com.antran.Warehouse_management.entity.User;
import com.antran.Warehouse_management.exception.AppException;
import com.antran.Warehouse_management.exception.ErrorCode;
import com.antran.Warehouse_management.mapper.UserMapper;
import com.antran.Warehouse_management.repository.UserRepository;
import com.antran.Warehouse_management.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(int id) {
        return UserMapper.toResponse(findUserById(id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(int id, UserUpdateRequest request) {
        User user = findUserById(id);
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setDob(request.getDob());
        user.setRole(request.getRole());
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void changeUserStatus(int id) {
        User user = findUserById(id);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Override
    public void changePassword(int id, UserChangePasswordRequest request) {
        User user = findUserById(id);
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new AppException(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH);
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_NEW_PASSWORD_MATCH);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
