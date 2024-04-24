package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;

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
    @Test
    public void testUserByIdEndpoint() {
        RestAssured.given()
                .when().get("/user/2")
                .then()
                .statusCode(200)
                .body("firstName", is("frumencio"));
    }

    @Test
    public void testDeleteUserEndpoint() {
        RestAssured.given()
                .when().delete("/user/deleteUser/1")
                .then()
                .statusCode(200)
                .body("message", is("User deleted successfully"));
    }

    // @Test
    // public void testUpdateUserEndpoint() {
    //     RestAssured.given()
    //             .when().put("/user/editUser/2")
    //             .then()
    //             .statusCode(200)
    //             .body("message", is("User updated successfully"));
    // }

    // @Test
    // public void testRegisterUserEndpoint() {
    //     String jsonData = "{\"firstName\":\"string\",\"lastName\":\"string\",\"language\":\"string\",\"birthday\":\"2022-03-10\",\"email\":\"string\",\"userPassword\":\"string\",\"phone\":\"string\",\"userType\":\"string\",\"createdAt\":\"2022-03-10T12:15:50\"}";

    //     RestAssured.given()
    //             .contentType(ContentType.JSON)
    //             .body(jsonData)
    //             .when()
    //             .post("/user/registerUser")
    //             .then()
    //             .statusCode(201)
    //             .body("message", equalTo("User created successfully"));
    // }


}
