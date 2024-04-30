package org.acme.apiTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.service.ChatHistoryService;
import org.acme.service.UserService;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChatHistoryControllerTests {



    @Test
    @Order(1)
    public void testGetAllChatHistoriesEndpoint() {
        ValidatableResponse response =  given()
                .when()
                 .get("/chatHistory")
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

    @Test
    @Order(1)
    public void testGetChatHistoryByUserId() {
        given()
                .when().get("/chatHistory/user/2")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Transactional
    @Order(2)
    public void testPostChatHistoryEndpoint() {
        String requestBody = "{\"status\": \"active\"}";

        given()
                .pathParam("userId", 2)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/chatHistory/user/{userId}")
                .then()
                .statusCode(201);
    }

    @Test
    @TestTransaction
    @Transactional
    @Order(2)
    public void testPostChatHistoryEndpointInvalidUser() {
        String requestBody = "{\"status\": \"active\"}";

        given()
                .pathParam("userId", 9540259)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/chatHistory/user/{userId}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(1)
    public void testGetChatHistoryByIdEndpoint() {

        given()
                .pathParam("chatHistoryId", 551)
                .contentType("application/json")
                .when()
                .get("/chatHistory/{chatHistoryId}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetChatHistoryByUserIdAndStatusEndpoint() {

        given()
                .pathParam("userId", 2)
                .pathParam("status", "active")
                .contentType("application/json")
                .when()
                .get("/chatHistory/status/{userId}/{status}")
                .then()
                .statusCode(200);
    }

//    @Test
//    @TestTransaction
//    @Transactional
//    public void testUpdateChatHistoryEndpoint() {
//        given()
//                .pathParam("chatHistoryId", 501)
//                .queryParam("status", "updatedStatus")
//                .when()
//                .put("/chatHistory/{chatHistoryId}")
//                .then()
//                .statusCode(200);
//    }

//    @Test
//    @TestTransaction
//    @Transactional
//    public void testDeleteChatHistoryEndpoint() {
//        given()
//                .pathParam("chatHistoryId", 501)
//                .when()
//                .delete("/chatHistory/{chatHistoryId}")
//                .then()
//                .statusCode(204);
//    }
}
