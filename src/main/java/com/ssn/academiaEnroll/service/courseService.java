package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.repository.courseRepository;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class courseService {

    @Autowired
    private courseRepository courseRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

    public List<CourseOffering> getCourseOfferingsByCourseId(int courseId) {
        return courseOfferingRepository.findByCourseID(courseId);
    }
}
