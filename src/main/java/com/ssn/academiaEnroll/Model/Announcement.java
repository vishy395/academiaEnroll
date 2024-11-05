package com.ssn.academiaEnroll.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Announcement extends Communication {
    private Long academicSemester;

    public Announcement(String content,int sender,Long academicSemester) {
        super(content,sender);
        this.academicSemester = academicSemester;
    }

}

