package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.repository.userRepository;
import com.ssn.academiaEnroll.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private userRepository userRepo;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return user;
    }

    public int getLoggedInUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username);
        return user.getId();
    }
}