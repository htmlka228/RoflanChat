package com.example.application.service;

import com.example.application.component.UIRoflanMessage;
import com.example.application.model.RoflanMessage;
import com.example.application.repository.RoflanMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoflanMessageService {
    private final RoflanMessageRepository roflanMessageRepository;
    private List<RoflanMessage> messagesHistory;

    @PostConstruct
    public void init() {
        messagesHistory = findAll();
    }

    public RoflanMessage save(RoflanMessage message) {
        messagesHistory.add(message);
        return roflanMessageRepository.save(message);
    }

    public void deleteAllMessages() {
        messagesHistory.clear();
        roflanMessageRepository.deleteAll();
    }

    public List<RoflanMessage> findAll() {
        return roflanMessageRepository.findAll();
    }

    public List<UIRoflanMessage> getMessagesHistory() {
        return messagesHistory.stream()
                .sorted(Comparator.comparing(RoflanMessage::getSentTime))
                .map(UIRoflanMessage::new)
                .collect(Collectors.toList());
    }
}
