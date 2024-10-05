package com.ssn.academiaEnroll.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.time.YearMonth;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class academicSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = YearMonthConverter.class)
    private YearMonth StartDate;

    @Convert(converter = YearMonthConverter.class)
    private YearMonth EndDate;

    private Semester semester;

    @ElementCollection
    private List<Integer> courseIds;
}

