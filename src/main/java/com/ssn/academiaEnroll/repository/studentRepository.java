package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface studentRepository extends JpaRepository<Student,Integer> {
}
