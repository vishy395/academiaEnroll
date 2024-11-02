// ModuleRepository.java
package com.ssn.academiaEnroll.repository;

import com.ssn.academiaEnroll.Model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    List<Module> findByFacultyId(int facultyId);
    List<Module> findByStudentIdsContaining(int studentId);
}
