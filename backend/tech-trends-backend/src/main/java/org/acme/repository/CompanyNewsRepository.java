
package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.CompanyNews;

import java.util.List;

@ApplicationScoped
public class CompanyNewsRepository implements PanacheRepository<CompanyNews> {
    //Panache repository implements the basic functionality
    // Custom database queries go here



    public List<CompanyNews> findbyNewsId(Long newsId) {
        return list("companynews_id", newsId);
    }

    public List<CompanyNews> findbyUserId(Long userId) {
        return list("user.id", userId);
    }

    public List<CompanyNews> findbyStatus(String status) {
        return list("status", status);

    }
}
