package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.Faculty;
import com.ssn.academiaEnroll.Model.Student;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import com.ssn.academiaEnroll.repository.facultyRepository;
import com.ssn.academiaEnroll.repository.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private facultyRepository facultyRepository;

    @Autowired
    private studentRepository studentRepository;

    public String enrollStudentInCourseOffering(int studentId, int courseOfferingId) {
        //Retrieve the student, course offering, and validate the student exists
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return "Student not found";
        }
        Student student = studentOptional.get();

        Optional<CourseOffering> courseOfferingOptional = courseOfferingRepository.findById(courseOfferingId);
        if (!courseOfferingOptional.isPresent()) {
            return "CourseOffering not found";
        }
        CourseOffering courseOffering = courseOfferingOptional.get();

        //Retrieve all course offerings the student is already enrolled in
        List<CourseOffering> studentCourseOfferings = courseOfferingRepository.findByStudentIdsContains(studentId);

        //Check if the student is already enrolled in a course with the same classSection
        for (CourseOffering enrolledOffering : studentCourseOfferings) {
            if (enrolledOffering.getClassName().equals(courseOffering.getClassName())) {
                return "Student is already enrolled in a course with the same class section";
            }
            // Check if the student is enrolled in another offering of the same course
            if (enrolledOffering.getCourseID() == courseOffering.getCourseID()) {
                return "Student is already enrolled in a different offering of this course";
            }
        }

        //Check if the capacity of the course offering is exceeded
        if (courseOffering.getStudentIds().size() >= courseOffering.getCapacity()) {
            return "CourseOffering capacity exceeded";
        }

        //Add student to CourseOffering
        List<Integer> enrolledStudents = courseOffering.getStudentIds();
        enrolledStudents.add(studentId);
        courseOffering.setStudentIds(enrolledStudents);
        courseOfferingRepository.save(courseOffering);

        //Add student to Faculty's student list
        Optional<Faculty> facultyOptional = facultyRepository.findById(courseOffering.getFacultyID());
        if (facultyOptional.isPresent()) {
            Faculty faculty = facultyOptional.get();
            List<Integer> facultyStudents = faculty.getStudentIds();
            facultyStudents.add(studentId);
            faculty.setStudentIds(facultyStudents);
            facultyRepository.save(faculty);
        }

        return "Student successfully enrolled in the course offering";
    }
}
