package com.ssn.academiaEnroll.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty extends User{
    private String title;
    private int experience;
    @ElementCollection
    private List<Integer> studentIds;

    public Faculty(String name, String username, String password,String role, String title, int experience, List<Integer> studentIds) {
        super(name, username, password, role);  // Set role as ROLE_FACULTY
        this.title = title;
        this.experience = experience;
        this.studentIds = studentIds;
    }
}
