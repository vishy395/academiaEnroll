package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.dto.CourseOfferingStatsDTO;
import com.ssn.academiaEnroll.repository.CourseOfferingRepository;
import com.ssn.academiaEnroll.repository.facultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class CourseOfferingService {

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingService.class.getName());

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private facultyRepository facultyRepository;

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

    public List<CourseOfferingStatsDTO> getCourseOfferingsBySemester(int semesterId) {
        List<CourseOffering> courseOfferings = courseOfferingRepository.findByAcademicSemester(semesterId);

        return courseOfferings.stream().map(offering -> {
            CourseOfferingStatsDTO stats = new CourseOfferingStatsDTO();
            String facultyName = facultyRepository.findById(offering.getFacultyID())
                    .map(faculty -> faculty.getName())
                    .orElse("Unknown");

            if ("Unknown".equals(facultyName)) {
                LOGGER.warning("Faculty ID " + offering.getFacultyID() + " not found for Course ID " + offering.getCourseID());
            }

            stats.setFacultyName(facultyName);
            stats.setClassName(offering.getClassName().toString());
            stats.setStudentCount(offering.getStudentIds().size());
            stats.setCourseID(offering.getCourseID());
            return stats;
        }).collect(Collectors.toList());
    }

    public String exportCourseOfferings(int semester, String format) {
        List<CourseOfferingStatsDTO> offerings = getCourseOfferingsBySemester(semester);

        String fileName = "course_offerings_" + semester + "." + format;
        try (FileWriter writer = new FileWriter(fileName)) {
            if ("csv".equalsIgnoreCase(format)) {
                writeCsvFile(writer, offerings);
            } else if ("txt".equalsIgnoreCase(format)) {
                writeTxtFile(writer, offerings);
            } else {
                return "Unsupported format: " + format;
            }
            return "Export successful: " + fileName;

        } catch (IOException e) {
            LOGGER.severe("Error exporting file: " + e.getMessage());
            return "Export failed due to an error.";
        }
    }

    private void writeCsvFile(FileWriter writer, List<CourseOfferingStatsDTO> offerings) throws IOException {
        writer.write("CourseID,FacultyName,ClassName,StudentCount\n");
        for (CourseOfferingStatsDTO offering : offerings) {
            writer.write(String.format("%d,%s,%s,%d\n",
                    offering.getCourseID(),
                    offering.getFacultyName(),
                    offering.getClassName(),
                    offering.getStudentCount()));
        }
    }

    private void writeTxtFile(FileWriter writer, List<CourseOfferingStatsDTO> offerings) throws IOException {
        for (CourseOfferingStatsDTO offering : offerings) {
            writer.write(String.format("Course ID: %d\nFaculty: %s\nClass: %s\nStudents: %d\n\n",
                    offering.getCourseID(),
                    offering.getFacultyName(),
                    offering.getClassName(),
                    offering.getStudentCount()));
        }
    }
}
