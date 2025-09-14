package com.kvn.ddd.application.services;

import org.springframework.stereotype.Service;

import com.kvn.ddd.application.dto.ProfileDto;
import com.kvn.ddd.domain.aggregates.Profile;
import com.kvn.ddd.domain.repositories.ProfileRepository;
import com.kvn.ddd.domain.valueobjects.Email;
import com.kvn.ddd.domain.valueobjects.UserId;

@Service
public class ProfileApplicationService {
    
    private final ProfileRepository profileRepository;

    public ProfileApplicationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Long userId, String name, String email, String password) {
        UserId userIdVO = new UserId(userId);
        Email emailVO = new Email(email);
        String passwordHash = hashPassword(password);
        
        Profile profile = new Profile(userIdVO, name, emailVO, passwordHash);
        return profileRepository.save(profile);
    }

    public ProfileDto login(String email, String password) {
        Email emailVO = new Email(email);
        Profile profile = profileRepository.findByEmail(emailVO)
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        
        if (!profile.isActive() || !verifyPassword(password, profile.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        profile.login();
        profileRepository.save(profile);
        
        return mapToDto(profile);
    }

    public ProfileDto getProfile(Long userId) {
        UserId userIdVO = new UserId(userId);
        Profile profile = profileRepository.findById(userIdVO)
            .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        
        return mapToDto(profile);
    }

    private ProfileDto mapToDto(Profile profile) {
        return new ProfileDto(
            profile.getId().getValue(),
            profile.getName(),
            profile.getEmail().getValue(),
            profile.getCreatedDate(),
            profile.getLastLoginDate(),
            profile.isActive()
        );
    }

    private String hashPassword(String password) {
        return "hashed_" + password; // Simple hash for demo
    }

    private boolean verifyPassword(String password, String hash) {
        return hash.equals("hashed_" + password);
    }
}