package com.ssn.academiaEnroll.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Inheritance(strategy = InheritanceType.JOINED)
@Component
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="people")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String username;
    private String password;
}
