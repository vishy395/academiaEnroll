package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.academicSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering,Integer> {

    List<CourseOffering> findByCourseID(int courseID);
    List<CourseOffering> findByStudentIdsContains(Integer studentId);

    // Custom query to check if a student is enrolled in a course offering
    @Query("SELECT co FROM CourseOffering co WHERE :studentId MEMBER OF co.studentIds")
    List<CourseOffering> findByStudentId(@Param("studentId") int studentId);

    // Custom query to check if a student is already enrolled in a different offering of the same course
    @Query("SELECT CASE WHEN COUNT(co) > 0 THEN true ELSE false END FROM CourseOffering co WHERE co.courseID = :courseId AND :studentId MEMBER OF co.studentIds")
    boolean existsByStudentIdAndCourseID(@Param("studentId") int studentId, @Param("courseId") int courseId);
}

