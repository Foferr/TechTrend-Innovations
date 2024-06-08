package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedMessageDTO;
import org.acme.model.Messages;
import org.acme.repository.MessagesRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MessagesService {


    @Inject
    MessagesRepository messagesRepository;

    public List<DecryptedMessageDTO> getAllMessages() {
        List<Messages> messages = messagesRepository.listAll();
        List<DecryptedMessageDTO> DTOs = new ArrayList<>();

        for(Messages message: messages) {
            DTOs.add(DTOUtils.mapToDTO(message));
        }
        return DTOs;
    }

    @Transactional
    public void postMessage(Messages message) {
        try {
            message.setMessageContent(EncryptionUtil.encrypt(message.getMessageContent()));
            message.setSenderType(EncryptionUtil.encrypt(message.getSenderType()));

            messagesRepository.persist(message);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting user data", e);
        }
    }

    public List<DecryptedMessageDTO> getMessagesByUserId(Long userId) {
        List<Messages> messages = messagesRepository.getByUserId(userId);
        List<DecryptedMessageDTO> DTOs = new ArrayList<>();

        for(Messages message : messages) {
            DTOs.add(DTOUtils.mapToDTO(message));
        }
        return DTOs;
    }

    public List<DecryptedMessageDTO> getMessagesByUserIdChatId(Long userId, Long chatId) {
        List<Messages> messages = messagesRepository.getByUserIdAndChatId(userId, chatId);
        List<DecryptedMessageDTO> DTOs = new ArrayList<>();

        for(Messages message : messages) {
            DTOs.add(DTOUtils.mapToDTO(message));
        }
        return DTOs;
    }

    public List<DecryptedMessageDTO> getMessagesByChatId(Long chatId) {
        List<Messages> messages = messagesRepository.getByChatId(chatId);
        List<DecryptedMessageDTO> DTOs = new ArrayList<>();

        for(Messages message : messages) {
            DTOs.add(DTOUtils.mapToDTO(message));
        }
        return DTOs;
    }

    public List<DecryptedMessageDTO> getMessagesBySenderType(String senderType) {
        try {
            String encryptedStatus = EncryptionUtil.encrypt(senderType);
            List<Messages> messages = messagesRepository.getBySenderType(encryptedStatus);
            List<DecryptedMessageDTO> DTOs = new ArrayList<>();

            for(Messages message : messages) {
                DTOs.add(DTOUtils.mapToDTO(message));
            }
            return DTOs;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't parse sender type" + e);
        }
    }
}
