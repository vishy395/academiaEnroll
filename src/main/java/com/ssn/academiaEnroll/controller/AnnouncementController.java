package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Announcement;
import com.ssn.academiaEnroll.service.CommunicationService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    // Endpoint for fetching all announcements (accessible to students)
    @GetMapping
    public ResponseEntity<List<Announcement>> getAnnouncements() {
        List<Announcement> announcements = communicationService.getAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    // Endpoint for sending an announcement (accessible to faculty)
    @PostMapping
    public ResponseEntity<?> sendAnnouncement(@RequestBody Announcement announcement) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the logged-in user ID from the authentication
        int senderId = myUserDetailsService.getLoggedInUserId(authentication);

        // Check if the user has permission to make an announcement
        if (communicationService.isFaculty(senderId)) {
            announcement.setSenderId(senderId);
            Announcement sendAnnouncement = communicationService.sendCommunication(announcement);
            return ResponseEntity.ok(sendAnnouncement);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only faculty members can send announcements.");
        }
    }
}
