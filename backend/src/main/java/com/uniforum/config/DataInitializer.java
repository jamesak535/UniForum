package com.uniforum.config;

import com.uniforum.model.User;
import com.uniforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User(null, "admin", "admin@uniforum.com", passwordEncoder.encode("admin123"), "Admin User", "Admin University", Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
            
            userRepository.save(admin);
            
            System.out.println("Admin user created successfully!");
        }
    }
}