package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Settings;
import com.ssn.academiaEnroll.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private SettingsRepository settingsRepository;

    @GetMapping("/status")
    public boolean getRegistrationStatus() {
        Settings settings = settingsRepository.findById(1).orElse(new Settings());
        return settings.isRegistrationOpen();
    }

    @PostMapping("/status")
    public boolean toggleRegistrationStatus() {
        Settings settings = settingsRepository.findById(1).orElse(new Settings());
        settings.setIsRegistrationOpen(!settings.isRegistrationOpen());
        settingsRepository.save(settings);
        return settings.isRegistrationOpen();
    }
}
