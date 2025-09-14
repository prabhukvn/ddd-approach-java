package com.kvn.ddd.application.dto;

import java.time.LocalDateTime;

public class ProfileDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastLoginDate;
    private boolean active;

    public ProfileDto(Long id, String name, String email, LocalDateTime createdDate, LocalDateTime lastLoginDate, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.lastLoginDate = lastLoginDate;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastLoginDate() { return lastLoginDate; }
    public boolean isActive() { return active; }
}