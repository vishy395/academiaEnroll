package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseRepository extends JpaRepository<Course,Integer> {
}
