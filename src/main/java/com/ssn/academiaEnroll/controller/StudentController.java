package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.Student;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import com.ssn.academiaEnroll.service.courseService;
import com.ssn.academiaEnroll.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private studentService studentService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private courseService courseService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        student.setId(id);
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/current/semester")
    public Long getCurrentStudentSemester(Authentication authentication) {
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);
        Student student = studentService.getStudentById(studentId);
        return student.getAcademicSemester();  // Assuming this field exists in Student entity
    }

    @GetMapping("/current/courses")
    public List<Course> getCoursesForCurrentStudent(Authentication authentication) {
        int studentId = myUserDetailsService.getLoggedInUserId(authentication);
        Student student = studentService.getStudentById(studentId);
        Long semesterId = student.getAcademicSemester(); // Get the student's academic semester
        return courseService.getCoursesByAcademicSemester(semesterId);
    }
}
