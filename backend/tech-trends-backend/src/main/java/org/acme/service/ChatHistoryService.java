package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedChatHistoryDTO;
import org.acme.DTO.decryptionDTOs.DecryptedFAQDTO;
import org.acme.exceptions.UserNotFoundException;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.repository.ChatHistoryRepository;
import org.acme.repository.UserRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ChatHistoryService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatHistoryService.class);

    @Inject
    ChatHistoryRepository chathistoryRepository;

    @Inject
    UserRepository userRepository;

    public List<DecryptedChatHistoryDTO> getAllChatHistory() {
        List<ChatHistory> chatHistories = chathistoryRepository.listAll();
        List<DecryptedChatHistoryDTO> DTOs = new ArrayList<>();

        for (ChatHistory chatHistory : chatHistories) {
            DTOs.add(DTOUtils.mapToDTO(chatHistory));
        }
        return DTOs;
    }

    public List<DecryptedChatHistoryDTO> getChatHistoryByUserId(Long userId) {
        List<ChatHistory> chatHistories = chathistoryRepository.findbyUserId(userId);
        List<DecryptedChatHistoryDTO> DTOs = new ArrayList<>();
        for (ChatHistory chatHistory : chatHistories) {
            DTOs.add(DTOUtils.mapToDTO(chatHistory));
        }
        return DTOs;
    }

    @Transactional
    public void postChatHistory(Long userId, ChatHistory chatHistory) throws UserNotFoundException {
        try {
            User user = userRepository.findById(userId);
            if(user == null) {
                throw new UserNotFoundException("User with id " + userId + " does not exist.");
            }
            chatHistory.setUser(user);

            // Decrypt sensitive fields before persisting
            chatHistory.setStatus(EncryptionUtil.encrypt(chatHistory.getStatus()));
            chatHistory.setTitle(EncryptionUtil.encrypt(chatHistory.getTitle()));

            // Persist chat history
            chathistoryRepository.persist(chatHistory);
        } catch (UserNotFoundException e) {
            LOG.error("Error posting chat history: {}", e.getMessage());
            throw e; // Re-throw the exception
        } catch (Exception e) {
            // Log detailed error message with stack trace
            LOG.error("Error posting chat history: {}", e.getMessage(), e);
            throw new RuntimeException("Error posting chat history: " + e.getMessage(), e);
        }
    }

    public Optional<DecryptedChatHistoryDTO> getChatHistoryById(Long chatHistoryId) {
        ChatHistory chatHistory = chathistoryRepository.findById(chatHistoryId);
        if(chatHistory == null) {
            return Optional.empty();
        }
        try {
            DecryptedChatHistoryDTO dto = DTOUtils.mapToDTO(chatHistory);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't decrypt chat histoy data: " + e);
        }
    }

    public List<DecryptedChatHistoryDTO> getChatHistoriesByUserIdAndStatus(Long userId, String status) {
        try {
            String encryptedStatus = EncryptionUtil.encrypt(status);
            List<ChatHistory> chatHistories = chathistoryRepository.findChatHistoriesByUserIdAndStatus(userId, encryptedStatus);
            List<DecryptedChatHistoryDTO> DTOs = new ArrayList<>();

            for (ChatHistory chatHistory : chatHistories) {
                DTOs.add(DTOUtils.mapToDTO(chatHistory));
            }
            return DTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting status", e);
        }
    }

    @Transactional
    public void updateChatHistory(Long chatHistoryId, ChatHistory chatHistory) throws ChatHistoryNotFoundException{
        ChatHistory existingChatHistory = chathistoryRepository.findById(chatHistoryId);
        if (existingChatHistory == null) {
            throw new ChatHistoryNotFoundException("Chat History with id: " + chatHistoryId + " does not exist.");
        }

        try {
            existingChatHistory.setStatus(chatHistory.getStatus() != null ? EncryptionUtil.encrypt(chatHistory.getStatus()) : existingChatHistory.getStatus());
            existingChatHistory.setTitle(chatHistory.getTitle() != null ? EncryptionUtil.encrypt(chatHistory.getTitle()) : existingChatHistory.getTitle());
            chathistoryRepository.persist(existingChatHistory);
        } catch (Exception e) {
            throw new RuntimeException("Error updating chat history: ", e);
        }
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
    public static class ChatHistoryNotFoundException extends Exception {
        public ChatHistoryNotFoundException(String message) {
            super(message);
        }
    }

    public Optional<ChatHistory> chatHistoryExists(Long chatHistoryId) {
        ChatHistory chatHistory = chathistoryRepository.findById(chatHistoryId);
        if(chatHistory == null) {
            return Optional.empty();
        } else {
            return Optional.of(chatHistory);
        }
    }
}