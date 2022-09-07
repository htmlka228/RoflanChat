package com.example.application.repository;

import com.example.application.model.RoflanMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoflanMessageRepository extends JpaRepository<RoflanMessage, UUID> {
}
