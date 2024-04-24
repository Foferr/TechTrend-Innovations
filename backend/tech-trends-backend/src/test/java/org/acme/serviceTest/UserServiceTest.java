package org.acme.serviceTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.model.User;
import org.acme.repository.UserRepository;
import org.acme.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;


import java.util.Collections;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;


    @Test
    public void testGetAllUsers() {
        Assertions.assertFalse(userService.getAllUsers().isEmpty(), "The list of users should not be empty");
    }

    @Test
    public void testGetUserById() {
        Assertions.assertNotNull(userService.getUserById(2L), "The user with id 2 should exist");
    }

    // @Test
    // public void testCreateUser() {
    //     User user = new User();
    //     user.firstName = "John";
    //     user.lastName = "Doe";
    //     user.language = "English";
    //     user.birthday = LocalDate.of(1990, 1, 1);
    //     user.email = "alololol@gmail.com";
    //     user.userPassword = "password";
    //     user.phone = "1234567890";
    //     user.userType = "admin";
    //     user.createdAt = LocalDateTime.of(2022, 3, 10, 0, 0);


    //     userService.createUser(user);

    //     Assertions.assertFalse(userService.getAllUsers().isEmpty(), "The list of users should not be empty");

    //     User createdUser = userService.getAllUsers().get(0);
    //     Assertions.assertEquals(user.firstName, createdUser.firstName, "The first name of the created user should match");
    //     Assertions.assertEquals(user.lastName, createdUser.lastName, "The last name of the created user should match");
    //     Assertions.assertEquals(user.language, createdUser.language, "The language of the created user should match");
    //     Assertions.assertEquals(user.email, createdUser.email, "The email of the created user should match");
    //     Assertions.assertEquals(user.userPassword, createdUser.userPassword, "The user password of the created user should match");
    //     Assertions.assertEquals(user.userType, createdUser.userType, "The user type of the created user should match");
    // }

    // @Test
    // public void testUpdateUser() {
    //     User user = new User();
    //     user.firstName = "John";
    //     user.lastName = "Doe";
    //     user.language = "English";
    //     user.birthday = LocalDate.of(1990, 1, 1);
    //     user.email = "aosaolsolaolso@gmail.com";
    //     user.userPassword = "password";
    //     user.phone = "1234567890";
    //     user.userType = "admin";
    //     user.createdAt = LocalDateTime.of(2022, 3, 10, 0, 0); // Assuming you want midnight as the time


    //     userService.createUser(user);

    //     User createdUser = userService.getAllUsers().get(0);
    //     user.firstName = "John";
    //     user.lastName = "Doe";
    //     user.language = "English";
    //     user.birthday = LocalDate.of(1990, 1, 1);
    //     user.email = "a@gmail.com";
    //     user.userPassword = "password";
    //     user.phone = "1234567890";
    //     user.userType = "admin";
    //     user.createdAt = LocalDateTime.of(2022, 3, 10, 0, 0); // Assuming you want midnight as the time


    //     userService.updateUser(createdUser);

    //     User updatedUser = userService.getAllUsers().get(0);
    //     Assertions.assertEquals(createdUser.firstName, updatedUser.firstName, "The first name of the updated user should match");
    //     Assertions.assertEquals(createdUser.lastName, updatedUser.lastName, "The last name of the updated user should match");
    //     Assertions.assertEquals(createdUser.language, updatedUser.language, "The language of the updated user should match");
    //     Assertions.assertEquals(createdUser.email, updatedUser.email, "The email of the updated user should match");
    //     Assertions.assertEquals(createdUser.userPassword, updatedUser.userPassword, "The user password of the updated user should match");
    //     Assertions.assertEquals(createdUser.userType, updatedUser.userType, "The user type of the updated user should match");


    // }

}
