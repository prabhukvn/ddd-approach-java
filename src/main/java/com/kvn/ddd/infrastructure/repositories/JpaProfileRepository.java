package com.kvn.ddd.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kvn.ddd.infrastructure.persistence.ProfileEntity;

public interface JpaProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByEmail(String email);
}