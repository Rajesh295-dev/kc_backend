package com.e_management.fullstack_backend.controller;

import com.e_management.fullstack_backend.model.Admin;
import com.e_management.fullstack_backend.model.User;
import com.e_management.fullstack_backend.repository.AdminRepository;
import com.e_management.fullstack_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // User/Admin Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody com.e_management.fullstack_backend.dto.LoginRequest loginRequest) {
        // First, check if the email belongs to an Admin
        Optional<Admin> adminOptional = adminRepository.findByEmail(loginRequest.getEmail());

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            // Validate the password for the Admin
            if (!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            // If credentials are correct, return a successful response for admin
            return ResponseEntity.ok("Admin login successful");
        }

        // If not Admin, check if the email belongs to a regular User
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()));

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate the password for the User
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            // If credentials are correct, return a successful response for the user
            return ResponseEntity.ok("User login successful");
        }

        // If neither Admin nor User found
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }


}
