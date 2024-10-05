package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.academicSemester;
import com.ssn.academiaEnroll.repository.academicSemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class academicSemesterService {

    @Autowired
    private academicSemesterRepository academicSemesterRepository;

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
}
