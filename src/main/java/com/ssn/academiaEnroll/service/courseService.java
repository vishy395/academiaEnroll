package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.academicSemester;
import com.ssn.academiaEnroll.repository.courseRepository;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import com.ssn.academiaEnroll.repository.academicSemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class courseService {

    @Autowired
    private courseRepository courseRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private academicSemesterRepository academicSemesterRepository;

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

    public List<CourseOffering> addCourseOfferings(int courseId, List<CourseOffering> courseOfferings) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));


        for (CourseOffering courseOffering : courseOfferings) {
            courseOffering.setCourseID(courseId);
            CourseOffering savedOffering = courseOfferingRepository.save(courseOffering);
            course.getCourseOfferings().add(savedOffering.getId());
        }
        courseRepository.save(course);
        return courseOfferings;
    }



    public List<Course> getCoursesByAcademicSemester(Long academicSemesterId) {
        Optional<academicSemester> academicSemesterOptional = academicSemesterRepository.findById(academicSemesterId);

        if (academicSemesterOptional.isPresent()) {
            academicSemester semester = academicSemesterOptional.get();
            List<Integer> courseIds = semester.getCourseIds();
            return courseRepository.findByIdIn(courseIds);
        } else {
            throw new RuntimeException("Academic Semester not found");
        }

    }
}

