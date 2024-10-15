package com.ssn.academiaEnroll.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{

    private Long academicSemester;
    public Student(String name, String username, String password, String role,Long academicSemester) {
        super(name, username, password, role);  // Set role as ROLE_STUDENT
        this.academicSemester = academicSemester;
    }
}
