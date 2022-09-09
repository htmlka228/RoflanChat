package com.example.application.controller;

import com.example.application.model.RoflanMessage;
import com.example.application.model.RoflanUser;
import com.example.application.service.RoflanMessageService;
import com.example.application.service.RoflanUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roflan-messages")
public class RoflanMessageController {
    private final RoflanMessageService roflanMessageService;
    private final RoflanUserService roflanUserService;

    @DeleteMapping
    public void cleanMessageHistory() {
        roflanMessageService.deleteAllMessages();
    }

    @PostMapping("/send")
    public List<RoflanMessage> sendManyMessages() {
        List<RoflanMessage> messages = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            messages.add(saveMockedAdminMessage());
        }

        return messages;
    }

    private RoflanMessage saveMockedAdminMessage() {
        return roflanMessageService.save(
                RoflanMessage.builder()
                        .uuid(UUID.randomUUID())
                        .message(RandomStringUtils.randomAlphanumeric(10, 30))
                        .sentTime(LocalDateTime.now())
                        .roflanUser((RoflanUser) roflanUserService.loadUserByUsername("admin"))
                        .build()
        );
    }
}
