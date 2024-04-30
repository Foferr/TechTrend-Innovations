package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.is;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @Test
    @Order(1)
    public void testGetUserByIdEndpoint() {
        given()
                .when()
                .get("/user/2")
                .then()
                .body("id", equalTo(1))
                .body("first_name", equalTo("John"))
                .body("last_name", equalTo("Doe"))
                .body("lang", equalTo("en"))
                .body("birthday", equalTo("1990-05-15"))
                .body("email", equalTo("john.doe@example.com"))
                .body("user_password", equalTo("password123"))
                .body("phone", equalTo("123456789"))
                .body("user_type", equalTo("base_user"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    public void testUserEndpoint() {
           ValidatableResponse response = given()
                .when().get("/user")
                .then()
                .statusCode(200);

        // To test posting a new user, you might serialize a User object and POST it
        // This requires setting up the database or mocking the service layer
        String responseBody = response.extract().asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        System.out.println("Raw response: ");
        System.out.println(response.extract());

        System.out.println("Response body:");
        System.out.println(responseBody);

        System.out.println("Response body:");
        System.out.println(jsonPath.prettify());
    }
//    @Test
//    public void testUserByIdEndpoint() {
//        given()
//                .when().get("/user/2")
//                .then()
//                .statusCode(200)
//                .body("firstName", is("frumencio"));
//    }
//
//    @Test
//    public void testDeleteUserEndpoint() {
//        given()
//                .when().delete("/user/deleteUser/1")
//                .then()
//                .statusCode(200)
//                .body("message", is("User deleted successfully"));
//    }

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
