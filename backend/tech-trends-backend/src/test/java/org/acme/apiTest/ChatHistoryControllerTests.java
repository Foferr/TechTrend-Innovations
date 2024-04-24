package org.acme.apiTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.service.ChatHistoryService;
import org.acme.service.UserService;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


@QuarkusTest
public class ChatHistoryControllerTests {



    @Test
    public void testGetAllChatHistoriesEndpoint() {
        given()
                .when().get("/chatHistory")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetChatHistoryByUserId() {
        given()
                .when().get("/chatHistory/user/2")
                .then()
                .statusCode(200);
    }

    @Test
    @TestTransaction
    @Transactional
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
