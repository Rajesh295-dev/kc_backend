package com.e_management.fullstack_backend.controller;

import com.e_management.fullstack_backend.exception.UserNotFoundException;
import com.e_management.fullstack_backend.model.User;
import com.e_management.fullstack_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());   // This line should work if `setName` is defined correctly.
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setAddress(newUser.getAddress());
                    user.setRole(newUser.getRole());
                    user.setDateOfBirth(newUser.getDateOfBirth());
                    user.setSalary(newUser.getSalary());
                    user.setSsn(newUser.getSsn());
                    user.setEmployeeId(newUser.getEmployeeId());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }


    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully.";
    }
}
