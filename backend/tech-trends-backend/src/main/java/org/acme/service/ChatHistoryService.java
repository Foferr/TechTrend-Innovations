package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.repository.ChatHistoryRepository;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ChatHistoryService {

    @Inject
    ChatHistoryRepository chathistoryRepository;

    @Inject
    UserRepository userRepository;

    public List<ChatHistory> getAllChatHistory() {
        return chathistoryRepository.listAll();
    }

    public List<ChatHistory> getChatHistoryByUserId(Long userId) {
        return chathistoryRepository.findbyUserId(userId);
    }

    @Transactional
    public void postChatHistory(ChatHistory chathistory, Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with id " + userId + " does not exist.");
        }
        chathistory.setUser(user);
        chathistoryRepository.persist(chathistory);
    }

    public Optional<ChatHistory> getChatHistoryById(Long chatHistoryId) {
        return Optional.ofNullable(chathistoryRepository.findById(chatHistoryId));
    }

    public List<ChatHistory> getChatHistoriesByUserIdAndStatus(Long userId, String status) {
        return chathistoryRepository.findChatHistoriesByUserIdAndStatus(userId, status);
    }

    @Transactional
    public Optional<ChatHistory> updateChatHistory(Long chatHistoryId, String status) {
        ChatHistory chatHistory = chathistoryRepository.findById(chatHistoryId);
        if(chatHistory != null) {
            chatHistory.setStatus(status);
            chathistoryRepository.persist(chatHistory); //Persist will trigger @PreUpdate
            return Optional.of(chatHistory);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteChatHistory(Long chatHistoryId) {
        ChatHistory chatHistory = chathistoryRepository.findById(chatHistoryId);
        if(chatHistory != null) {
            chathistoryRepository.delete(chatHistory);
            return true;
        }
        return false;
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}