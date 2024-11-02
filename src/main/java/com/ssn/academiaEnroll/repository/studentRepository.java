package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface studentRepository extends JpaRepository<Student,Integer> {

}
