package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.Course;
import com.ssn.academiaEnroll.Model.academicSemester;
import com.ssn.academiaEnroll.repository.academicSemesterRepository;
import com.ssn.academiaEnroll.repository.courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class academicSemesterService {

    @Autowired
    private academicSemesterRepository academicSemesterRepository;

    @Autowired
    private courseRepository courseRepository;

    public List<academicSemester> getAllSemesters() {
        return academicSemesterRepository.findAll();
    }

    public academicSemester getSemesterById(Long id) {
        return academicSemesterRepository.findById(id).orElse(null);
    }

    public academicSemester saveSemester(academicSemester semester) {
        return academicSemesterRepository.save(semester);
    }

    public void deleteSemester(Long id) {
        academicSemesterRepository.deleteById(id);
    }



    public List<Course> getCoursesByAcademicSemester(Long semesterId) {

        academicSemester semester = academicSemesterRepository.findById(semesterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid academic semester ID: " + semesterId));


        List<Integer> courseIds = semester.getCourseIds();
        return courseRepository.findCoursesByIds(courseIds);
    }
}
