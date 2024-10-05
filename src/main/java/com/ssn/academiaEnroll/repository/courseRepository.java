package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface courseRepository extends JpaRepository<Course,Integer> {

    @Query("SELECT c FROM Course c WHERE c.id IN :courseIds")
    List<Course> findCoursesByIds(@Param("courseIds") List<Integer> courseIds);

    // Find all courses with IDs in the provided list
    List<Course> findByIdIn(List<Integer> courseIds);
}
