package org.acme.techtrends.auth.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.techtrends.auth.model.User;
import org.acme.techtrends.auth.model.UserDataForToken;
import org.acme.techtrends.auth.repository.UserRepository;
import org.acme.techtrends.auth.utils.EncryptionUtil;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public UserDataForToken authenticate(String email, String password) {
        try {
            String encryptedEmail = EncryptionUtil.encrypt(email);
            String encryptedPassword = EncryptionUtil.encrypt(password);
            User user = userRepository.findbyCredentials(encryptedEmail, encryptedPassword);
            if (user != null) {
                if(!user.getUserType().isEmpty()) {
                    return new UserDataForToken(user.id, user.getUserType());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Something when wrong when authenticating credentials");
        }

    }
}