//package org.acme.service;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.transaction.Transactional;
//import org.acme.model.Faq;
//import org.acme.repository.FaqRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@ApplicationScoped
//public class FaqService {
//
//    @Inject
//    FaqRepository faqRepository;
//
//    public List<Faq> getAllFaqs() {
//        return faqRepository.listAll();
//    }
//
//    public Optional<Faq> getFaqById(Long faqId) {
//        return Optional.ofNullable(faqRepository.findById(faqId));
//    }
//
//    @Transactional
//    public void createFaq(Faq faq) {
//        faqRepository.persist(faq);
//    }
//
//    @Transactional
//    public void updateFaq(Long faqId, Faq faqData) throws FaqNotFoundException {
//        Faq faq = faqRepository.findById(faqId);
//        if (faq == null) {
//            throw new FaqNotFoundException("FAQ with id " + faqId + " does not exist.");
//        }
//        faq.question = faqData.question;
//        faq.answer = faqData.answer;
//        faq.status = faqData.status;
//        faqRepository.persist(faq);
//    }
//
//    @Transactional
//    public boolean deleteFaq(Long faqId) {
//        Faq faq = faqRepository.findById(faqId);
//        if(faq != null) {
//            faqRepository.delete(faq);
//            return true;
//        }
//        return false;
//    }
//
//    public List<Faq> getFaqsByStatus(String status) {
//        return faqRepository.findbyStatus(status);
//    }
//
//    public boolean isAdminValid(Long adminId) {
//        return true;
//    }
//
//    public static class FaqNotFoundException extends Exception {
//        public FaqNotFoundException(String message) {
//            super(message);
//        }
//    }
//}
