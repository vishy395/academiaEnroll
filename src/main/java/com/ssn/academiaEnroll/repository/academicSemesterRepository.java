package com.ssn.academiaEnroll.repository;


import com.ssn.academiaEnroll.Model.academicSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface academicSemesterRepository extends JpaRepository<academicSemester,Long> {

}
