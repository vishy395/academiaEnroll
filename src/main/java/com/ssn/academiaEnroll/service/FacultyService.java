package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.Faculty;
import com.ssn.academiaEnroll.Model.Student;
import com.ssn.academiaEnroll.repository.facultyRepository;
import com.ssn.academiaEnroll.repository.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    @Autowired
    private facultyRepository facultyRepository;


    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(int id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(int id) {
        facultyRepository.deleteById(id);
    }

    // Retrieves the list of students associated with the logged-in faculty
    public List<Integer> getStudentsForFaculty(int facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        return faculty.getStudentIds();
    }
}
