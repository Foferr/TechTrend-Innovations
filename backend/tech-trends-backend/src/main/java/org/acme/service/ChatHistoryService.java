package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.repository.ChatHistoryRepository;

import java.util.List;

@ApplicationScoped
public class ChatHistoryService {

    @Inject
    ChatHistoryRepository chathistoryRepository;

    public List<ChatHistory> getAllChatHistory() {
        return chathistoryRepository.listAll();
    }
}
