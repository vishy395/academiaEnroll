package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.CourseEnrollmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseEnrollmentHistoryRepository extends JpaRepository<CourseEnrollmentHistory, Integer> {
    List<CourseEnrollmentHistory> findByStudentId(int studentId);
}
