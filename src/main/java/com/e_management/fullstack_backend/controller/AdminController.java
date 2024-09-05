package com.e_management.fullstack_backend.controller;

import com.e_management.fullstack_backend.model.Admin;
import com.e_management.fullstack_backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")  // Enable CORS for your front-end
@RequestMapping("/auth")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;  // Autowire the AdminRepository
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        try {
            // Check if email already exists
            Optional<Admin> existingAdmin = adminRepository.findByEmail(email);
            if (existingAdmin.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            }

            String fileName = null;
            if (profileImage != null && !profileImage.isEmpty()) {
                // Save the uploaded file to the public folder
                String uploadDir = "static/uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                fileName = profileImage.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Store the admin details in the database
            Admin admin = new Admin();
            admin.setName(name);
            admin.setEmail(email);
            admin.setPhone(phone);
            admin.setPassword(password);  // In production, encode the password with BCrypt

            if (fileName != null) {
                admin.setProfileImage("/uploads/" + fileName);  // Store the relative path of the image
            } else {
                admin.setProfileImage(null);  // Set profileImage to null if no image is uploaded
            }

            // Save the admin to the database
            adminRepository.save(admin);

            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during registration");
        }
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getAdminProfile(@PathVariable String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }

    @PostMapping("/upload-profile-image/{email}")
    public ResponseEntity<?> uploadProfileImage(
            @PathVariable String email,
            @RequestParam("profileImage") MultipartFile profileImage) {

        try {
            // Find the admin by email
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            if (adminOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
            }

            Admin admin = adminOptional.get();

            // Save the image to the static/uploads directory
            String uploadDir = "static/uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = profileImage.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Update the admin profile image path in the database
            admin.setProfileImage("/uploads/" + fileName);
            adminRepository.save(admin);

            // Return the new image URL
            return ResponseEntity.ok(Map.of("profileImageUrl", "/uploads/" + fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }




}
