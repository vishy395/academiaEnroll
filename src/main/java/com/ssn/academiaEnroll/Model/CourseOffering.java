package com.ssn.academiaEnroll.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOffering {
    @Id
    @GeneratedValue
    private int id;
    private int capacity;

    @Enumerated(EnumType.STRING)
    private classSection className;

    private int courseID;
    private int facultyID;
    @ElementCollection
    private List<Integer> studentIds;
    private int academicSemester;

    public int getFacultyID() {
        return facultyID;
    }
}
