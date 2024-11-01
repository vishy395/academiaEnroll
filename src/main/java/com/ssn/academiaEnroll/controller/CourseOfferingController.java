package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.dto.CourseOfferingStatsDTO;
import com.ssn.academiaEnroll.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courseOfferings")
public class CourseOfferingController {

    @Autowired
    private CourseOfferingService courseOfferingService;

    @GetMapping
    public List<CourseOffering> getAllCourseOfferings() {
        return courseOfferingService.getAllCourseOfferings();
    }

    @GetMapping("/{id}")
    public CourseOffering getCourseOfferingById(@PathVariable int id) {
        return courseOfferingService.getCourseOfferingById(id);
    }

    @PostMapping
    public CourseOffering createCourseOffering(@RequestBody CourseOffering courseOffering) {
        return courseOfferingService.saveCourseOffering(courseOffering);
    }

    @PutMapping("/{id}")
    public CourseOffering updateCourseOffering(@PathVariable int id, @RequestBody CourseOffering courseOffering) {
        courseOffering.setId(id);
        return courseOfferingService.saveCourseOffering(courseOffering);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseOffering(@PathVariable int id) {
        courseOfferingService.deleteCourseOffering(id);
    }

    @GetMapping("/bySemester/{semesterId}")
    public List<CourseOfferingStatsDTO> getCourseOfferingsBySemester(@PathVariable int semesterId) {
        return courseOfferingService.getCourseOfferingsBySemester(semesterId);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportCourseOfferings(
            @RequestParam("semester") int semester,
            @RequestParam("format") String format) {
        String result = courseOfferingService.exportCourseOfferings(semester, format);
        return ResponseEntity.ok(result);
    }

}
