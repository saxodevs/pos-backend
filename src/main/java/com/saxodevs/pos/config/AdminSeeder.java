package com.saxodevs.pos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saxodevs.pos.domain.UserRole;
import com.saxodevs.pos.model.User;
import com.saxodevs.pos.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    CommandLineRunner createAdmin() {
        return args -> {

            boolean adminExists = userRepository.existsByRole(UserRole.ADMIN);

            if (!adminExists) {
                User admin = new User();
//                admin.setId(generator.generate("USR"));
                admin.setUsername("admin001");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(UserRole.ADMIN);
                admin.setFirstName("System");
                admin.setLastName("Admin");
                admin.setPhone("0000000000");

                userRepository.save(admin);

                System.out.println("Admin user created.");
            }
        };
    }
}
