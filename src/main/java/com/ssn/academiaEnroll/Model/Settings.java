package com.ssn.academiaEnroll.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// Assuming a Settings model exists
@Entity
public class Settings {
    @Id
    @GeneratedValue
    private int id;
    private boolean isRegistrationOpen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRegistrationOpen() {
        return isRegistrationOpen;
    }

    public void setIsRegistrationOpen(boolean registrationOpen) {
        isRegistrationOpen = registrationOpen;
    }
// Getters and Setters
}
