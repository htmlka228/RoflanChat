package com.example.application.controller;

import com.example.application.model.RoflanUser;
import com.example.application.model.Role;
import com.example.application.repository.RoflanUserRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@AnonymousAllowed
public class UserCreationEndpoint {
    private final RoflanUserRepository roflanUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public RoflanUser saveUser(@RequestBody String username) {
        return roflanUserRepository.save(
                RoflanUser.builder()
                        .uuid(UUID.randomUUID())
                        .username(username)
                        .password(bCryptPasswordEncoder.encode("pass"))
                        .roles(Collections.singleton(
                                Role.builder()
                                        .id("1")
                                        .name("ADMIN")
                                        .build()
                        ))
                        .enabled(true)
                        .build()
        );
    }

    @GetMapping
    public List<RoflanUser> getUsers() {
        return roflanUserRepository.findAll();
    }
}
