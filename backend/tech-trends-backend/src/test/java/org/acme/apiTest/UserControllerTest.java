package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UserControllerTest {

    @Test
    public void testUserEndpoint() {
        RestAssured.given()
                .when().get("/user")
                .then()
                .statusCode(200);

        // To test posting a new user, you might serialize a User object and POST it
        // This requires setting up the database or mocking the service layer
    }
}
