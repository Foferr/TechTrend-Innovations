package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.User;
import org.acme.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public void createUser(User user) {
        userRepository.persist(user);
    }

    public List<User> getAllUsers() {
        return userRepository.listAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.id);
        if (existingUser == null) {
            // Handle the case where the user is not found
            throw new RuntimeException("User not found with id: " + user.id);
        }

        // Update the fields of the existing user with the new values
        existingUser.firstName = user.firstName != null ? user.firstName : existingUser.firstName;
        existingUser.lastName = user.lastName != null ? user.lastName : existingUser.lastName;
        existingUser.language = user.language != null ? user.language : existingUser.language;
        existingUser.birthday = user.birthday != null ? user.birthday : existingUser.birthday;
        existingUser.email = user.email != null ? user.email : existingUser.email;
        existingUser.userPassword = user.userPassword != null ? user.userPassword : existingUser.userPassword;
        existingUser.phone = user.phone != null ? user.phone : existingUser.phone;
        existingUser.userType = user.userType != null ? user.userType : existingUser.userType;

        userRepository.persist(existingUser);
    }


    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
