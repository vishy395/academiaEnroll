package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.User;
import com.ssn.academiaEnroll.dto.LoginDTO;
import com.ssn.academiaEnroll.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    // Login endpoint (Spring Security will authenticate the user)
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "Login successful";
        } catch (Exception e) {
            return "Invalid username or password";
        }
    }
}
