package com.ssn.academiaEnroll.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class CourseEnrollmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int studentId;
    private int courseOfferingId;
    private LocalDateTime timestamp;
    private String action;  // "ENROLLED" or "DE-ROLLED"

}
