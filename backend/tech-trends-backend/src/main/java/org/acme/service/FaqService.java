package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedFAQDTO;
import org.acme.model.ChatHistory;
import org.acme.model.Event;
import org.acme.model.Faq;
import org.acme.model.User;
import org.acme.repository.FaqRepository;
import org.acme.repository.UserRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.acme.exceptions.UserNotFoundException;

@ApplicationScoped
public class FaqService {

    @Inject
    FaqRepository faqRepository;

    @Inject
    UserRepository userRepository;

    public List<DecryptedFAQDTO> getAllFaqs() {
        List<Faq> faqs =  faqRepository.listAll();
        List<DecryptedFAQDTO> DTOs = new ArrayList<>();

        for(Faq faq : faqs) {
            DTOs.add(DTOUtils.mapToDTO(faq));
        }
        return DTOs;
    }

    public Optional<DecryptedFAQDTO> getFaqById(Long faqId) {
        Faq faq = faqRepository.findById(faqId);
        if (faq == null) {
            return Optional.empty();
        }
        try {
            DecryptedFAQDTO dto = DTOUtils.mapToDTO(faq);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't decrypt FAQ data: " + e);
        }
    }

    public List<DecryptedFAQDTO> getFaqsByAdminId(Long adminId) {
        List<Faq> faqs =  faqRepository.findbyAdminId(adminId);
        List<DecryptedFAQDTO> DTOs = new ArrayList<>();

        for(Faq faq : faqs) {
            DTOs.add(DTOUtils.mapToDTO(faq));
        }
        return DTOs;
    }

    @Transactional
    public void createFaq(Long adminId, Faq faq) throws UserNotFoundException {
        try {
            User user = userRepository.findById(adminId);
            if (user == null || user.id == null) {
                throw new UserNotFoundException("User with id" + adminId + "doesn't exist or isn't an admin.");
            }
            faq.setAdmin(user);
            faq.setQuestion(EncryptionUtil.encrypt(faq.getQuestion()));
            faq.setAnswer(EncryptionUtil.encrypt(faq.getAnswer()));
            faq.setStatus(EncryptionUtil.encrypt(faq.getStatus()));
            faqRepository.persist(faq);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't encrypt data");
        }
    }

    @Transactional
    public void updateFaq(Long faqId, Faq faqData) throws FaqNotFoundException {
        Faq existingFaq = faqRepository.findById(faqId);
        if (existingFaq == null) {
            throw new FaqNotFoundException("FAQ with id " + faqId + " does not exist.");
        }
        try {
            existingFaq.setQuestion(faqData.getQuestion() != null ? EncryptionUtil.encrypt(faqData.getQuestion()) : existingFaq.getQuestion());
            existingFaq.setAnswer(faqData.getAnswer() != null ? EncryptionUtil.encrypt(faqData.getAnswer()) : existingFaq.getAnswer());
            existingFaq.setStatus(faqData.getStatus() != null ? EncryptionUtil.encrypt(faqData.getStatus()) : existingFaq.getStatus());
            faqRepository.persist(existingFaq);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting FAQ data");
        }
    }

    @Transactional
    public boolean deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId);
        if(faq != null) {
            faqRepository.delete(faq);
            return true;
        }
        return false;
    }

    public List<DecryptedFAQDTO> getFaqsByStatus(String status) {
        List<Faq> faqs = faqRepository.findbyStatus(status);
        List<DecryptedFAQDTO> DTOs = new ArrayList<>();

        for(Faq faq : faqs) {
            DTOs.add(DTOUtils.mapToDTO(faq));
        }
        return DTOs;
    }

    public boolean isAdminValid(Long adminId) {
        try {
            User admin = userRepository.findById(adminId);
            if (admin == null) {
                return false;
            }
            String userType = admin.getUserType();
            userType = EncryptionUtil.decrypt(userType);
            return userType.equals("admin");
        } catch (Exception e) {
            throw new RuntimeException("Couldn't verify if provided id has permits");
        }
    }

    public static class FaqNotFoundException extends Exception {
        public FaqNotFoundException(String message) {
            super(message);
        }
    }


}
