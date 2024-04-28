package org.acme.techtrends.auth.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.techtrends.auth.model.User;
import org.acme.techtrends.auth.repository.UserRepository;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public String authenticate(String email, String password) {
        User user = userRepository.findbyCredentials(email, password);
        if (user != null) {
            return user.getUserType();
        }
        return null;
    }
}
