package com.example.application.repository;

import com.example.application.model.RoflanUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoflanUserRepository extends JpaRepository<RoflanUser, UUID> {
    Optional<RoflanUser> findByUsername(String username);
}
