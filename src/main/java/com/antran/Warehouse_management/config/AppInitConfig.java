package com.antran.Warehouse_management.config;

import com.antran.Warehouse_management.entity.Role;
import com.antran.Warehouse_management.entity.User;
import com.antran.Warehouse_management.enums.ERole;
import com.antran.Warehouse_management.repository.RoleRepository;
import com.antran.Warehouse_management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (ERole erole : ERole.values()) {
                roleRepository.findByName(erole.name())
                        .orElseGet(() -> {
                            Role role = Role.builder().name(erole.name()).build();
                            return roleRepository.save(role);
                        });
            }
        };
    }
    @Bean
    ApplicationRunner initAdmin(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName(ERole.ADMIN.name())
                        .orElseThrow(() -> new RuntimeException("Role ADMIN chưa được khởi tạo trong DB"));
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .roles(Set.of(adminRole))
                        .build();
                userRepository.save(user);
                log.info("User admin được tạo với username: admin, password: 123456");
            }
        };
    }

}
