package com.kvn.ddd.infrastructure.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kvn.ddd.domain.aggregates.Profile;
import com.kvn.ddd.domain.repositories.ProfileRepository;
import com.kvn.ddd.domain.valueobjects.Email;
import com.kvn.ddd.domain.valueobjects.UserId;
import com.kvn.ddd.infrastructure.persistence.ProfileEntity;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    
    private final JpaProfileRepository jpaRepository;

    public ProfileRepositoryImpl(JpaProfileRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Profile save(Profile profile) {
        ProfileEntity entity = mapToEntity(profile);
        jpaRepository.save(entity);
        return profile;
    }

    @Override
    public Optional<Profile> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
            .map(this::mapToDomain);
    }

    @Override
    public Optional<Profile> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
            .map(this::mapToDomain);
    }

    private ProfileEntity mapToEntity(Profile profile) {
        return new ProfileEntity(
            profile.getId().getValue(),
            profile.getName(),
            profile.getEmail().getValue(),
            profile.getPasswordHash(),
            profile.getCreatedDate(),
            profile.getLastLoginDate(),
            profile.isActive()
        );
    }

    private Profile mapToDomain(ProfileEntity entity) {
        Profile profile = new Profile(
            new UserId(entity.getId()),
            entity.getName(),
            new Email(entity.getEmail()),
            entity.getPasswordHash()
        );
        return profile;
    }
}