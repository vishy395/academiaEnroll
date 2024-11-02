package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Faculty;
import com.ssn.academiaEnroll.Model.Student;
import com.ssn.academiaEnroll.repository.studentRepository;
import com.ssn.academiaEnroll.service.FacultyService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private studentRepository studentRepository;
    @GetMapping
    public List<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable int id) {
        return facultyService.getFacultyById(id);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.saveFaculty(faculty);
    }

    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable int id, @RequestBody Faculty faculty) {
        faculty.setId(id);
        return facultyService.saveFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/students")
    public List<Student> getStudentsForFaculty() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int facultyId = myUserDetailsService.getLoggedInUserId(authentication);
        List<Integer> studentIds = facultyService.getStudentsForFaculty(facultyId);
        return studentRepository.findAllById(studentIds);
    }
}
