package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.CompanyNews;
import org.acme.model.User;
import org.acme.repository.CompanyNewsRepository;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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
}