package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
