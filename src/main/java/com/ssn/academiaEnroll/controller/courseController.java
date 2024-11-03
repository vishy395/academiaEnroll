package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.CourseEnrollmentHistory;
import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.service.CourseEnrollmentService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import com.ssn.academiaEnroll.service.courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class courseController {

    @Autowired
    private courseService courseService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

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

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollInCourseOffering(@RequestBody Map<String, Integer> requestBody, Authentication authentication) {
        int courseOfferingId = requestBody.get("courseOfferingId");
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);

        String result = courseEnrollmentService.enrollStudent(courseOfferingId, studentId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/deroll")
    public ResponseEntity<String> derollFromCourseOffering(@RequestBody Map<String, Integer> requestBody, Authentication authentication) {
        int courseOfferingId = requestBody.get("courseOfferingId");
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);

        String result = courseEnrollmentService.derollStudent(courseOfferingId, studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{courseOfferingId}/isEnrolled")
    public ResponseEntity<Boolean> isStudentEnrolled(@PathVariable int courseOfferingId, Authentication authentication) {
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);
        boolean isEnrolled = courseEnrollmentService.isStudentEnrolled(courseOfferingId, studentId);
        return ResponseEntity.ok(isEnrolled);
    }

    @GetMapping("/enrollment/history")
    public ResponseEntity<List<CourseEnrollmentHistory>> getEnrollmentHistory(Authentication authentication) {
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);
        List<CourseEnrollmentHistory> history = courseEnrollmentService.getEnrollmentHistory(studentId);
        return ResponseEntity.ok(history);
    }


}
