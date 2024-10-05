package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.academicSemester;
import com.ssn.academiaEnroll.service.academicSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class academicSemesterController {

    @Autowired
    private academicSemesterService academicSemesterService;

    @GetMapping
    public List<academicSemester> getAllSemesters() {
        return academicSemesterService.getAllSemesters();
    }

    @GetMapping("/{id}")
    public academicSemester getSemesterById(@PathVariable Long id) {
        return academicSemesterService.getSemesterById(id);
    }

    @PostMapping
    public academicSemester createSemester(@RequestBody academicSemester semester) {
        return academicSemesterService.saveSemester(semester);
    }

    @PutMapping("/{id}")
    public academicSemester updateSemester(@PathVariable Long id, @RequestBody academicSemester semester) {
        semester.setId(id);
        return academicSemesterService.saveSemester(semester);
    }

    @DeleteMapping("/{id}")
    public void deleteSemester(@PathVariable Long id) {
        academicSemesterService.deleteSemester(id);
    }
}
