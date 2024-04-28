package org.acme.techtrends.auth.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.techtrends.auth.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    //Panache repository implements the basic functionality
    // Custom database queries go here

    public User findbyCredentials(String email, String userPassword) {
        return find("email = ?1 and userPassword = ?2", email, userPassword).firstResult();
    }
}
