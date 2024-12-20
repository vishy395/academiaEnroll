package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Module;
import com.ssn.academiaEnroll.service.ModuleService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/create")
    public Module createModule(@RequestBody Module module) {
        return moduleService.createModule(module);
    }

    @PostMapping("/{moduleId}/add-material")
    public ResponseEntity<String> uploadMaterial(
            @PathVariable int moduleId,
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = saveFileAndGetUrl(file);
            moduleService.addMaterialToModule(moduleId, fileUrl);
            return ResponseEntity.ok("Material uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload material.");
        }
    }

    private String saveFileAndGetUrl(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/uploads/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/modules/uploads/")
                .path(filename)
                .toUriString();
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("src/main/resources/static/uploads/").resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // Adjust MIME type if necessary
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/faculty")
    public ResponseEntity<List<Module>> getModulesByFaculty() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int facultyId = myUserDetailsService.getLoggedInUserId(authentication);
        List<Module> modules = moduleService.getModulesByFaculty(facultyId);
        return ResponseEntity.ok(modules);
    }
}
