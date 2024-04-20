package org.acme.serviceTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.model.User;
import org.acme.repository.UserRepository;
import org.acme.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;


    @Test
    public void testGetAllUsers() {
        Assertions.assertFalse(userService.getAllUsers().isEmpty(), "The list of users should not be empty");
    }
}
