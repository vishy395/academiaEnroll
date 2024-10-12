package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.CourseEnrollmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentHistoryRepository extends JpaRepository<CourseEnrollmentHistory, Integer> {
}
