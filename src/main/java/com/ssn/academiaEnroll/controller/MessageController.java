package com.ssn.academiaEnroll.controller;

import com.ssn.academiaEnroll.Model.Message;
import com.ssn.academiaEnroll.service.MessageService;
import com.ssn.academiaEnroll.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @GetMapping
    public ResponseEntity<List<Message>> getMessagesForCurrentUser() {
        // Get the authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the logged-in user ID from the authentication
        int userId = myUserDetailsService.getLoggedInUserId(authentication);

        // Fetch messages for the current user
        List<Message> messages = messageService.getMessagesForUser((long) userId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        if (!messageService.canMessage(message.getSender(), message.getReceiver())) {
            return ResponseEntity.badRequest().build(); // Bad request if the messaging is not allowed
        }
        Message savedMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}