package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.model.Event;
import org.acme.model.Faq;
import org.acme.model.User;
import org.acme.repository.FaqRepository;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FaqService {

    @Inject
    FaqRepository faqRepository;

    @Inject
    UserRepository userRepository;

    public List<Faq> getAllFaqs() {
        return faqRepository.listAll();
    }

    public Faq getFaqById(Long faqId) {
        return faqRepository.findById(faqId);
    }

    public List<Faq> getFaqsByAdminId(long adminId) { return faqRepository.findbyAdminId(adminId); }

    @Transactional
    public void createFaq(Long adminId, Faq faq) throws UserNotFoundException {
        User user = userRepository.findById(adminId);
        if (user == null || user.id == null) {
            throw new UserNotFoundException("User with id" + adminId + "doesn't exist or isn't an admin.");
        }
        faq.setAdmin(user);
        faqRepository.persist(faq);
    }

    @Transactional
    public void updateFaq(Long faqId, Faq faqData) throws FaqNotFoundException {
        Faq faq = faqRepository.findById(faqId);
        if (faq == null) {
            throw new FaqNotFoundException("FAQ with id " + faqId + " does not exist.");
        }
        faq.question = faqData.question;
        faq.answer = faqData.answer;
        faq.status = faqData.status;
        faqRepository.persist(faq);
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

    public List<Faq> getFaqsByStatus(String status) {
        return faqRepository.findbyStatus(status);
    }

    public boolean isAdminValid(Long adminId) {
        User admin = userRepository.findById(adminId);
        if (admin == null) {
            return false;
        }
        String userType = admin.getUserType();
        return userType.equals("admin");
    }

    public static class FaqNotFoundException extends Exception {
        public FaqNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
