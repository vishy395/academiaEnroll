package com.ssn.academiaEnroll.service;

import com.ssn.academiaEnroll.Model.Module;
import com.ssn.academiaEnroll.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";


    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public void addMaterialToModule(int moduleId, String materialUrl) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(() ->
                new RuntimeException("Module not found"));
        module.getMaterialUrls().add(materialUrl);
        moduleRepository.save(module);
    }

    public List<Module> getModulesForStudent(int studentId) {
        return moduleRepository.findByStudentIdsContaining(studentId);
    }

    public List<Module> getModulesByFaculty(int facultyId) {
        return moduleRepository.findByFacultyId(facultyId);
    }
}
