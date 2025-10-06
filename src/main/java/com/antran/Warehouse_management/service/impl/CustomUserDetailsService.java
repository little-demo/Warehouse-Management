package com.antran.Warehouse_management.service.impl;

import com.antran.Warehouse_management.dto.request.User.CustomUserPrincipal;
import com.antran.Warehouse_management.entity.User;
import com.antran.Warehouse_management.entity.Warehouse;
import com.antran.Warehouse_management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user với username: " + username));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Role.name = ERole
                .toList();

        return new CustomUserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                authorities
                //user.getWarehouses().stream().map(Warehouse::getId).toList()
        );
    }
}
