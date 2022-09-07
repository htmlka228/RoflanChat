package com.example.application.controller;

import com.example.application.service.RoflanMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roflan-messages")
public class RoflanMessageController {
    private final RoflanMessageService roflanMessageService;

    @DeleteMapping
    public void cleanMessageHistory() {
        roflanMessageService.deleteAllMessages();
    }
}
