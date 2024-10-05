package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseOfferingService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public List<CourseOffering> getAllCourseOfferings() {
        return courseOfferingRepository.findAll();
    }

    public CourseOffering getCourseOfferingById(int id) {
        return courseOfferingRepository.findById(id).orElse(null);
    }

    public CourseOffering saveCourseOffering(CourseOffering courseOffering) {
        return courseOfferingRepository.save(courseOffering);
    }

    public void deleteCourseOffering(int id) {
        courseOfferingRepository.deleteById(id);
    }
}
