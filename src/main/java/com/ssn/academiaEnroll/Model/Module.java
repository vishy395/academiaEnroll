// Module.java
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
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int facultyId;

    @ElementCollection
    private List<Integer> studentIds;

    @ElementCollection
    private List<String> materialUrls;  // URLs for uploaded materials
}
