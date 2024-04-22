package org.acme.repositoryTest;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.model.User;
import org.acme.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

//QuarkusTest is used for integration testing,
// in this case between the postgresql database and the quarkus backend
@QuarkusTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    public void testUserPersistence() {
        User newUser = new User();
        newUser.firstName = "Pepito";
        newUser.lastName = "Marquez";
        newUser.language = "esp";
        newUser.birthday = LocalDate.parse("2018-11-01");
        newUser.email = "pepito@gmail.com";
        newUser.userPassword = "Pepito123";
        newUser.phone = "123456789";
        newUser.userType = "base_user";

        userRepository.persist(newUser);
        userRepository.flush();

        User foundUser = userRepository.find("email", newUser.email).firstResult();
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("Pepito", foundUser.firstName);


    }
}
