package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface facultyRepository extends JpaRepository<Faculty,Integer> {
}
