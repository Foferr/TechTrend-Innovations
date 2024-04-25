package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.repository.ChatHistoryRepository;
import org.acme.repository.UserRepository;
import org.acme.service.ChatHistoryService.UserNotFoundException;

import java.util.List;
import java.util.Optional;


import org.acme.model.CompanyNews;
import org.acme.repository.CompanyNewsRepository;


@ApplicationScoped
public class CompanyNewsService {

    @Inject
    CompanyNewsRepository companyNewsRepository;

    @Inject
    UserRepository userRepository;

    //Implementar endpoint /companyNews/getAll (GET)
    public List<CompanyNews> getAllCompanyNews() {
        return companyNewsRepository.listAll();
    }
     
    // Implementar endpoint /companyNews/{companyNewsId} (GET)
    public List<CompanyNews> getCompanyNewsById(Long newsId) {
        return companyNewsRepository.findbyNewsId(newsId);
    }
    
    //Implementar endpoint /companyNews/{adminId} (GET)
    public List<CompanyNews> getCompanyNewsByUserId(Long userId) {
        return companyNewsRepository.findbyUserId(userId);
    }

    //Implementar endpoint /companyNews/{status} (GET)
    public List<CompanyNews> getCompanyNewsByStatus( String status) {
        return companyNewsRepository.findbyStatus(status);
 
    }
    

     @Transactional
    public void postInsertCompanyNews(Long adminId, CompanyNews createNews) throws UserNotFoundException {
        User user = userRepository.findById(adminId);
        if (user == null) {
            throw new UserNotFoundException("User with id " + adminId + " does not exist.");
        }
        /*
        para dejar realizar insert a usuarios admin
        if (!user.userType.equals("admin")) {
            throw new UserNotFoundException("User with id " + adminId + " does not admin.");
        }
        */
        createNews.setUser(user);
        companyNewsRepository.persist(createNews);
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
    public Optional<CompanyNews> updateCompanyNews(Long adminId,Long companyNewsId, String status) {
        
        CompanyNews companyNews = companyNewsRepository.findById(companyNewsId);
        if(companyNews != null) {
            companyNews.setStatus(status);
            companyNewsRepository.persist(companyNews); //Persist will trigger @PreUpdate
            return Optional.of(companyNews);
        }
        return Optional.empty();
    }

}
