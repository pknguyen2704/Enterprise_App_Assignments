package com.example.securingweb.config;

import com.example.securingweb.model.Role;
import com.example.securingweb.model.User;
import com.example.securingweb.repository.RoleRepository;
import com.example.securingweb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo role ADMIN nếu chưa có
            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName("ADMIN");
                return roleRepository.save(newRole);
            });

            // Tạo role USER nếu chưa có
            Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName("USER");
                return roleRepository.save(newRole);
            });

            // Tạo tài khoản chỉ có quyền ADMIN
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole)); // Chỉ có quyền ADMIN
                userRepository.save(admin);
                System.out.println("Created ADMIN: admin / admin123");
            }

            // Tạo tài khoản chỉ có quyền USER
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(Set.of(userRole)); // Chỉ có quyền USER
                userRepository.save(user);
                System.out.println("Created USER: user / user123");
            }

            // Tạo tài khoản có cả ADMIN & USER (Super Admin)
            if (userRepository.findByUsername("superadmin").isEmpty()) {
                User superAdmin = new User();
                superAdmin.setUsername("superadmin");
                superAdmin.setPassword(passwordEncoder.encode("superadmin123"));
                superAdmin.setRoles(Set.of(adminRole, userRole)); // Có cả 2 quyền
                userRepository.save(superAdmin);
                System.out.println("Created SUPER ADMIN: superadmin / superadmin123");
            }
        };
    }
}
