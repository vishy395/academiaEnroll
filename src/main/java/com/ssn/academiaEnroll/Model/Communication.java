package com.ssn.academiaEnroll.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Getter
public abstract class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int sender;
    private String content;

    private LocalDateTime timestamp;

    public Communication(String content,int sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now();
    }

    public Communication() {
        this.timestamp = LocalDateTime.now();
    }

    public void setSenderId(int senderId) {
        this.sender = senderId;
    }
}
