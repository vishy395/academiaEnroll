package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public String enrollStudent(@RequestParam int studentId, @RequestParam int courseOfferingId) {
        return enrollmentService.enrollStudentInCourseOffering(studentId, courseOfferingId);
    }
}
