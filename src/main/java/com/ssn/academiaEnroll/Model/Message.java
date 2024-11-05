package com.ssn.academiaEnroll.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends Communication {

    private int receiver;

    public Message(String content, int sender, int receiver) {
        super(content,sender);
        this.receiver = receiver;
    }

}
