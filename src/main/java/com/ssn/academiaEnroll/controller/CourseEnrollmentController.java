package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.service.CourseEnrollmentService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class CourseEnrollmentController {

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestBody Map<String, Integer> requestBody, Authentication authentication) {
        int courseOfferingId = requestBody.get("courseOfferingId");

        // Extract student ID from JWT token (assuming you have a utility for this)
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);

        String result = courseEnrollmentService.enrollStudent(courseOfferingId, studentId);

        return ResponseEntity.ok(result);
    }

}

