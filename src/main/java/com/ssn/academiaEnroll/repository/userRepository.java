package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface userRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
