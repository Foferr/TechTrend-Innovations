package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedCompanyNewsDTO;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.repository.ChatHistoryRepository;
import org.acme.repository.UserRepository;
import org.acme.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.acme.model.CompanyNews;
import org.acme.repository.CompanyNewsRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;


@ApplicationScoped
public class CompanyNewsService {

    @Inject
    CompanyNewsRepository companyNewsRepository;

    @Inject
    UserRepository userRepository;

    //Implementar endpoint /companyNews/getAll (GET)
    public List<DecryptedCompanyNewsDTO> getAllCompanyNews() {
        List<CompanyNews> companyNews = companyNewsRepository.listAll();
        List<DecryptedCompanyNewsDTO> DTOs = new ArrayList<>();

        for(CompanyNews companyNew : companyNews) {
            DTOs.add(DTOUtils.mapToDTO(companyNew));
        }
        return DTOs;
    }

    // Implementar endpoint /companyNews/{companyNewsId} (GET)
    public Optional<DecryptedCompanyNewsDTO> getCompanyNewsById(Long newsId) {
        CompanyNews companyNews = companyNewsRepository.findById(newsId);
        if(companyNews == null) {
            return Optional.empty();
        }
        try {
            DecryptedCompanyNewsDTO dto = DTOUtils.mapToDTO(companyNews);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't decrypt company news data: " + e);
        }
    }

    //Implementar endpoint /companyNews/{adminId} (GET)
    public List<DecryptedCompanyNewsDTO> getCompanyNewsByUserId(Long userId) {
        List<CompanyNews> companyNews = companyNewsRepository.findbyUserId(userId);
        List<DecryptedCompanyNewsDTO> DTOs = new ArrayList<>();

        for(CompanyNews companyNew: companyNews) {
            DTOs.add(DTOUtils.mapToDTO(companyNew));
        }
        return DTOs;
    }

    //Implementar endpoint /companyNews/{status} (GET)
    public List<DecryptedCompanyNewsDTO> getCompanyNewsByStatus( String status) {
        try {
            String encryptedStatus = EncryptionUtil.encrypt(status);
            List<CompanyNews> companyNews = companyNewsRepository.findbyStatus(encryptedStatus);
            List<DecryptedCompanyNewsDTO> DTOs = new ArrayList<>();

            for(CompanyNews companyNew: companyNews) {
                DTOs.add(DTOUtils.mapToDTO(companyNew));
            }
            return DTOs;
        } catch (Exception e) {
            throw new RuntimeException("Could parse encrypted data: " + e);
        }
    }


    @Transactional
    public void createCompanyNews(Long adminId, CompanyNews companyNews) throws UserNotFoundException {
        try {
            User user = userRepository.findById(adminId);
            if (user == null || user.id == null) {
                throw new UserNotFoundException("User with id" + adminId + "doesn't exist or isn't an admin.");
            }
            companyNews.setUser(user);
            companyNews.setTitle(EncryptionUtil.encrypt(companyNews.getTitle()));
            companyNews.setNewsContent(EncryptionUtil.encrypt(companyNews.getNewsContent()));
            companyNews.setStatus(EncryptionUtil.encrypt(companyNews.getStatus()));
            companyNewsRepository.persist(companyNews);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't encrypt data");
        }
    }

    @Transactional
    public boolean postDeleteCompanyNews(Long companyNewsId) {

        CompanyNews companyNews = companyNewsRepository.findById(companyNewsId);
        if(companyNews != null) {
            companyNewsRepository.delete(companyNews);
            return true;
        }
        return false;
    }

    @Transactional
    public void updateCompanyNews(Long adminId, Long companyNewsId, CompanyNews companyNews) throws CompanyNewsNotFoundException {
        CompanyNews existingCompanyNews = companyNewsRepository.findById(companyNewsId);
        if (existingCompanyNews == null) {
            throw new CompanyNewsNotFoundException("FAQ with id " + companyNewsId + " does not exist.");
        }
        try {
            existingCompanyNews.setTitle(companyNews.getTitle() != null ? EncryptionUtil.encrypt(companyNews.getTitle()) : existingCompanyNews.getTitle());
            existingCompanyNews.setNewsContent(companyNews.getNewsContent() != null ? EncryptionUtil.encrypt(companyNews.getNewsContent()) : existingCompanyNews.getNewsContent());
            existingCompanyNews.setStatus(companyNews.getStatus() != null ? EncryptionUtil.encrypt(companyNews.getStatus()) : existingCompanyNews.getStatus());
            companyNewsRepository.persist(existingCompanyNews);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting company news data");
        }
    }

    public static class CompanyNewsNotFoundException extends Exception {
        public CompanyNewsNotFoundException(String message) {
            super(message);
        }
    }
}
