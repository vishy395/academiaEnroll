package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.CourseOffering;
import com.ssn.academiaEnroll.Model.academicSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface academicSemesterRepository extends JpaRepository<academicSemester,Long> {

}
