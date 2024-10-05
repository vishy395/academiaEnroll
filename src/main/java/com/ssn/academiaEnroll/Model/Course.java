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
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String code;
    @Enumerated(EnumType.STRING)
    private courseType type;
    private String description;
    @ElementCollection
    private List<Integer> courseOfferings;
}
