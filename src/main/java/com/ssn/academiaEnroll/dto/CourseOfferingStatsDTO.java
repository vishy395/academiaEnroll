package com.ssn.academiaEnroll.dto;

import lombok.Data;

@Data
public class CourseOfferingStatsDTO {
    private String facultyName;
    private String className;
    private int studentCount;
    private int courseID;
}
