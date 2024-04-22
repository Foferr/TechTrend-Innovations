package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    //Panache repository implements the basic functionality
    // Custom database queries go here
}
