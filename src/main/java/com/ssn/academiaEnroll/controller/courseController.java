package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class courseController {

    @Autowired
    private courseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course course) {
        course.setId(id);
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/{courseId}/offerings")
    public List<CourseOffering> getCourseOfferings(@PathVariable int courseId) {
        return courseService.getCourseOfferingsByCourseId(courseId);
    }

    @GetMapping("/byAcademicSemester/{academicSemesterId}")
    public List<Course> getCoursesByAcademicSemester(@PathVariable Long academicSemesterId) {
        return courseService.getCoursesByAcademicSemester(academicSemesterId);
    }

    @PostMapping("/{courseId}/offerings")
    @PreAuthorize("hasRole('USER')")
    public List<CourseOffering> addCourseOffering(@PathVariable int courseId, @RequestBody List<CourseOffering> courseOfferings) {
        return courseService.addCourseOfferings(courseId, courseOfferings);
    }
}
