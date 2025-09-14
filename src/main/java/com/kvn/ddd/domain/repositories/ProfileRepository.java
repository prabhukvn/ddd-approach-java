package com.kvn.ddd.domain.repositories;

import java.util.Optional;

import com.kvn.ddd.domain.aggregates.Profile;
import com.kvn.ddd.domain.valueobjects.Email;
import com.kvn.ddd.domain.valueobjects.UserId;

public interface ProfileRepository {
    Profile save(Profile profile);
    Optional<Profile> findById(UserId id);
    Optional<Profile> findByEmail(Email email);
}