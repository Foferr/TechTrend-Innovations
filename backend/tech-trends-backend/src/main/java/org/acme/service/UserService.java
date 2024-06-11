package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.DTO.decryptionDTOs.DecryptedUserDTO;
import org.acme.model.User;
import org.acme.repository.UserRepository;
import org.acme.utils.DTOUtils;
import org.acme.utils.EncryptionUtil;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Inject
    UserRepository userRepository;

    public boolean emailExists(String email) {
        try {
            return userRepository.existsByEmail(EncryptionUtil.encrypt(email));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't verify if email exists", e);
        }
    }

    @Transactional
    public void createUser(User user) {
        try {
            user.setUserPassword(EncryptionUtil.encrypt(user.getUserPassword()));
            user.setEmail(EncryptionUtil.encrypt(user.getEmail()));
            user.setFirstName(EncryptionUtil.encrypt(user.getFirstName()));
            user.setLanguage(EncryptionUtil.encrypt(user.getLanguage()));
            user.setLastName(EncryptionUtil.encrypt(user.getLastName()));
            user.setPhone(EncryptionUtil.encrypt(user.getPhone()));
            user.setUserType(EncryptionUtil.encrypt(user.getUserType()));

            userRepository.persist(user);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting user data", e);
        }
    }

//    private static final int ITERATIONS = 10000;
//    private static final int KEY_LENGTH = 256;
//
//    @Transactional
//    public void createUser(User user) {
//        String hashedPassword = hashedCredential(user.getUserPassword());
//        String hashedEmail = hashedCredential(user.getEmail());
//        user.setUserPassword(hashedPassword);
//        user.setEmail(hashedEmail);
//        userRepository.persist(user);
//    }
//
//    private String hashedCredential(String password) {
//        char[] passwordChars = password.toCharArray();
//        byte[] salt = generateSalt();
//        PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, ITERATIONS, KEY_LENGTH);
//        try {
//            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//            byte[] hash = skf.generateSecret(spec).getEncoded();
//            return Base64.getEncoder().encodeToString(hash);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new RuntimeException("Error hashing password", e);
//        }
//    }
//
//    private byte[] generateSalt() {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        return salt;
//    }

    public List<DecryptedUserDTO> getAllUsers() {
        List<User> users = userRepository.listAll();
        List<DecryptedUserDTO> DTOs = new ArrayList<>();

        for (User user : users) {
            DTOs.add(DTOUtils.mapToDTO(user));
        }
        return DTOs;
    }

    public Optional<DecryptedUserDTO> getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Optional.empty();
        }
        try {
            DecryptedUserDTO dto = DTOUtils.mapToDTO(user);
            return Optional.of(dto);
        } catch (Exception e) {
            // Log the exception
            LOGGER.log(Level.SEVERE, "Error while mapping user to DTO", e);
            return Optional.empty();
        }
    }


    @Transactional
    public void updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        try {
            existingUser.setLanguage(user.getLanguage() != null ? EncryptionUtil.encrypt(user.getLanguage()) : existingUser.getLanguage());
            existingUser.setFirstName(user.getFirstName() != null ? EncryptionUtil.encrypt(user.getFirstName()) : existingUser.getFirstName());
            existingUser.setLastName(user.getLastName() != null ? EncryptionUtil.encrypt(user.getLastName()) : existingUser.getLastName());
            existingUser.setLanguage(user.getLanguage() != null ? user.getLanguage() : existingUser.getLanguage());
            existingUser.setBirthday(user.getBirthday() != null ? user.getBirthday() : existingUser.getBirthday());
            existingUser.setEmail(user.getEmail() != null ? EncryptionUtil.encrypt(user.getEmail()) : existingUser.getEmail());
            existingUser.setUserPassword(user.getUserPassword() != null ? EncryptionUtil.encrypt(user.getUserPassword()) : existingUser.getUserPassword());
            existingUser.setPhone(user.getPhone() != null ? EncryptionUtil.encrypt(user.getPhone()) : existingUser.getPhone());
            existingUser.setUserType(user.getUserType() != null ? EncryptionUtil.encrypt(user.getUserType()) : existingUser.getUserType());
            existingUser.setCountry(user.getCountry() != null ? user.getCountry() : existingUser.getCountry());
            userRepository.persist(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting user data", e);
        }
    }


    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean isAdminValid(Long adminId) {
        try {
            User admin = userRepository.findById(adminId);
            if (admin == null) {
                return false;
            }
            String userType = admin.getUserType();
            userType = EncryptionUtil.decrypt(userType);
            return userType.equals("admin");
        } catch (Exception e) {
            throw new RuntimeException("Couldn't verify if provided id has permits");
        }
    }
}
