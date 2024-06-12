package org.acme.utils;

import org.acme.DTO.decryptionDTOs.*;
import org.acme.model.*;

import java.time.LocalDateTime;

public class DTOUtils {
    public static DecryptedUserDTO mapToDTO(User user) {
        try {
            DecryptedUserDTO dto = new DecryptedUserDTO();
            dto.setId(user.id);
            dto.setFirstName(EncryptionUtil.decrypt(user.getFirstName()));
            dto.setLastName(EncryptionUtil.decrypt(user.getLastName()));
            dto.setLanguage(EncryptionUtil.decrypt(user.getLanguage()));
            dto.setBirthday(user.getBirthday());
            dto.setEmail(EncryptionUtil.decrypt(user.getEmail()));
            dto.setUserPassword(EncryptionUtil.decrypt(user.getUserPassword()));
            dto.setPhone(EncryptionUtil.decrypt(user.getPhone()));
            dto.setUserType(EncryptionUtil.decrypt(user.getUserType()));
            dto.setCountry(user.getCountry());
            dto.setCreatedAt(user.getCreatedAt());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping user to decryption DTO: " + e);
        }
    }
    public static DecryptedMessageDTO mapToDTO(Messages message) {
        try {
            DecryptedMessageDTO dto = new DecryptedMessageDTO();
            dto.setId(message.id);
            dto.setChatHistoryId(message.getChatHistory().id);
            dto.setSenderType(EncryptionUtil.decrypt(message.getSenderType()));
            dto.setMessageContent(EncryptionUtil.decrypt(message.getMessageContent()));
            dto.setCreatedAt(message.getCreatedAt());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping message to decryption DTO");
        }
    }
    public static DecryptedFAQDTO mapToDTO(Faq faq) {
        try {
            DecryptedFAQDTO dto = new DecryptedFAQDTO();
            dto.setId(faq.id);
            dto.setQuestion(EncryptionUtil.decrypt(faq.getQuestion()));
            dto.setAnswer(EncryptionUtil.decrypt(faq.getAnswer()));
            dto.setAdminId(faq.getAdmin().id);
            dto.setStatus(EncryptionUtil.decrypt(faq.getStatus()));
            dto.setCreatedAt(faq.getCreatedAt());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping FAQ to decryption DTO");
        }
    }
    public static DecryptedEventDTO mapToDTO(Event event) {
        try {
            DecryptedEventDTO dto = new DecryptedEventDTO();
            dto.setId(event.id);
            dto.setUserId(event.getUserId());
            dto.setEventTarget(EncryptionUtil.decrypt(event.getEventTarget()));
            dto.setCreatedAt(event.getCreatedAt());
            dto.setMetadata(EncryptionUtil.decrypt(event.getMetadata()));
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping event to decryption DTO");
        }
    }
    private Long id;
    private String title;
    private String newsContent;
    private Long adminId;
    private String status;
    private LocalDateTime createdAt;
    public static DecryptedCompanyNewsDTO mapToDTO(CompanyNews companyNews) {
        try {
            DecryptedCompanyNewsDTO dto = new DecryptedCompanyNewsDTO();
            dto.setId(companyNews.id);
            dto.setTitle(EncryptionUtil.decrypt(companyNews.getTitle()));
            dto.setNewsContent(EncryptionUtil.decrypt(companyNews.getNewsContent()));
            dto.setAdminId(companyNews.getUser().id);
            dto.setStatus(EncryptionUtil.decrypt(companyNews.getStatus()));
            dto.setCreatedAt(companyNews.getCreatedAt());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping CompanyNews to decryption DTO");
        }
    }
    public static DecryptedChatHistoryDTO mapToDTO(ChatHistory chatHistory) {
        try {
            DecryptedChatHistoryDTO dto = new DecryptedChatHistoryDTO();
            dto.setId(chatHistory.id);
            dto.setUserId(chatHistory.getUser().id);
            dto.setCreatedAt(chatHistory.getCreatedAt());
            dto.setUpdatedAt(chatHistory.getUpdatedAt());
            dto.setStatus(EncryptionUtil.decrypt(chatHistory.getStatus()));
            dto.setTitle(EncryptionUtil.decrypt(chatHistory.getTitle()));
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping ChatHistory to decryption DTO");
        }
    }
}
