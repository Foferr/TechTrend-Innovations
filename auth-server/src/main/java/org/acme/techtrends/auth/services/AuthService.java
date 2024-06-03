package org.acme.techtrends.auth.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.techtrends.auth.model.User;
import org.acme.techtrends.auth.model.UserDataForToken;
import org.acme.techtrends.auth.repository.UserRepository;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public UserDataForToken authenticate(String email, String password) {
        User user = userRepository.findbyCredentials(email, password);
        if (user != null) {
            if(!user.getUserType().isEmpty()) {
                return new UserDataForToken(user.id, user.getUserType());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
