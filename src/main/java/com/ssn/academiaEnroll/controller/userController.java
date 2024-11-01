package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.User;
import com.ssn.academiaEnroll.dto.LoginDTO;
import com.ssn.academiaEnroll.repository.userRepository;
import com.ssn.academiaEnroll.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private userRepository userRepository;
    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        return userService.saveUser(user);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO user) {

        return userService.verify(user);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User userOptional = userRepository.findByUsername(username); // Ensure this returns an Optional<User>
        return userOptional;
    }


}


