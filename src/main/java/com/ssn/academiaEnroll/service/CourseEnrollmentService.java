package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.CourseEnrollmentHistory;
import com.ssn.academiaEnroll.Model.Faculty;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import com.ssn.academiaEnroll.repository.CourseEnrollmentHistoryRepository;
import com.ssn.academiaEnroll.repository.studentRepository;
import com.ssn.academiaEnroll.repository.facultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CourseEnrollmentService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private CourseEnrollmentHistoryRepository courseEnrollmentHistoryRepository;

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private facultyRepository facultyRepository;

    public String enrollStudent(int courseOfferingId, int studentId) {

        // Step 1: Fetch the CourseOffering
        CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId)
                .orElseThrow(() -> new RuntimeException("Course offering not found"));

        // Step 2: Check capacity
        if (courseOffering.getStudentIds().size() >= courseOffering.getCapacity()) {
            return "Course offering is full";
        }

        // Step 3: Check if already enrolled in a different class section
        List<CourseOffering> enrolledOfferings = courseOfferingRepository.findByStudentId(studentId);
        for (CourseOffering offering : enrolledOfferings) {
            if (offering.getClassName() != courseOffering.getClassName()) {
                return "Already enrolled in a different class section";
            }
        }

        // Step 4: Check if already enrolled in a different offering of the same course
        if (courseOfferingRepository.existsByStudentIdAndCourseID(studentId, courseOffering.getCourseID())) {
            return "Already enrolled in a different offering for this course";
        }

        // Step 5: Enroll the student
        courseOffering.getStudentIds().add(studentId);
        courseOfferingRepository.save(courseOffering);

        // Step 6: Add student to the faculty's list
        Faculty faculty = facultyRepository.findById(courseOffering.getFacultyID())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        if (!faculty.getStudentIds().contains(studentId)) {
            faculty.getStudentIds().add(studentId);
            facultyRepository.save(faculty);
        }

        // Step 7: Log enrollment in CourseEnrollmentHistory
        CourseEnrollmentHistory history = new CourseEnrollmentHistory();
        history.setStudentId(studentId);
        history.setCourseOfferingId(courseOfferingId);
        history.setTimestamp(LocalDateTime.now());
        history.setAction("ENROLLED");
        courseEnrollmentHistoryRepository.save(history);

        return "Enrollment successful";
    }
}
