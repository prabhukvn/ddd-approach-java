package com.kvn.ddd.interfaces.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.ddd.application.dto.ProfileDto;
import com.kvn.ddd.application.services.ProfileApplicationService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileApplicationService profileApplicationService;

    public ProfileController(ProfileApplicationService profileApplicationService) {
        this.profileApplicationService = profileApplicationService;
    }

    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody CreateProfileRequest request) {
        profileApplicationService.createProfile(request.getUserId(), request.getName(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Profile created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@RequestBody LoginRequest request) {
        ProfileDto profile = profileApplicationService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        ProfileDto profile = profileApplicationService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    public static class CreateProfileRequest {
        private Long userId;
        private String name;
        private String email;
        private String password;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}