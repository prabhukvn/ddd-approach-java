package com.kvn.ddd.domain.aggregates;

import java.time.LocalDateTime;

import com.kvn.ddd.domain.valueobjects.Email;
import com.kvn.ddd.domain.valueobjects.UserId;

public class Profile {
    private UserId id;
    private String name;
    private Email email;
    private String passwordHash;
    private LocalDateTime createdDate;
    private LocalDateTime lastLoginDate;
    private boolean active;

    public Profile(UserId id, String name, Email email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdDate = LocalDateTime.now();
        this.active = true;
    }

    public void login() {
        this.lastLoginDate = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public UserId getId() { return id; }
    public String getName() { return name; }
    public Email getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastLoginDate() { return lastLoginDate; }
    public boolean isActive() { return active; }
}