package org.acme.apiTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.ChatHistoryDTOs.ChatHistoryPostRequestDTO;
import org.acme.DTO.UserDTOs.UserPostDTO;
import org.acme.model.ChatHistory;
import org.acme.model.User;
import org.acme.service.ChatHistoryService;
import org.acme.service.UserService;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;


@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChatHistoryControllerTests {



    @Test
    @Order(1)
    public void testGetAllChatHistoriesEndpoint() {
        ValidatableResponse response = given()
                .when()
                .get("/chatHistory")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(4));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
        response.body("updatedAt", hasItems("2024-05-02T14:00:00", "2024-05-30T14:00:00", "2024-06-30T14:00:00"));
        response.body("status", hasItems("active", "inactive"));
        response.body("title", hasItems("Title1", "Title2", "Title3", "Title4"));

    }

    @Test
    @Order(1)
    public void testGetChatHistoryByUserId() {
        ValidatableResponse response = given()
                .when()
                .get("/chatHistory/user/1")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(4));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
        response.body("updatedAt", hasItems("2024-05-02T14:00:00", "2024-05-30T14:00:00", "2024-06-30T14:00:00"));
        response.body("status", hasItems("active", "inactive"));
        response.body("title", hasItems("Title1", "Title2", "Title3", "Title4"));
    }

    @Test
    @Order(1)
    public void testGetChatHistoryByUserIdAndStatus() {
        given()
                .when()
                .get("/chatHistory/status/1/inactive")
                .then()
                .body("[0].id", equalTo(2))
                .body("[0].createdAt", equalTo("2024-04-30T16:00:00"))
                .body("[0].updatedAt", equalTo("2024-05-30T14:00:00"))
                .body("[0].status", equalTo("inactive"))
                .body("[0].title", equalTo("Title2"))
                .statusCode(200);

    }

    @Test
    @Order(1)
    public void testGetChatHistoryBySelfId() {
        given()
                .when()
                .get("/chatHistory/1")
                .then()
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("createdAt", equalTo("2024-04-30T14:00:00"))
                .body("updatedAt", equalTo("2024-05-02T14:00:00"))
                .body("status", equalTo("active"))
                .body("title", equalTo("Title1"))
                .statusCode(200);

    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateChatHistoryEndpoint() {
        String jsonInput = "{"
                + "\"title\": \"Updated title\","
                + "\"status\": \"updated\""
                + "}";

        given().contentType("application/json")
                .body(jsonInput)
                .when()
                .put("/chatHistory/1")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/chatHistory/1")
                .then()
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("createdAt", equalTo("2024-04-30T14:00:00"))
                .body("status", equalTo("updated"))
                .body("title", equalTo("Updated title"))
                .statusCode(200);
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteChatHistoryEndpoint() {
        given()
                .when()
                .delete("/chatHistory/4")
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/chatHistory/4")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

//    @Test
//    @TestTransaction
//    @Transactional
//    @Order(2)
//    public void testPostChatHistoryEndpoint() {
//        String requestBody = "{\"status\": \"active\"}";
//
//        given()
//                .pathParam("userId", 2)
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/chatHistory/user/{userId}")
//                .then()
//                .statusCode(201);
//    }

//    @Test
//    @TestTransaction
//    @Transactional
//    @Order(2)
//    public void testPostChatHistoryEndpointInvalidUser() {
//        String requestBody = "{\"status\": \"active\"}";
//
//        given()
//                .pathParam("userId", 9540259)
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/chatHistory/user/{userId}")
//                .then()
//                .statusCode(404);
//    }

//    @Test
//    @Order(1)
//    public void testGetChatHistoryByIdEndpoint() {
//
//        given()
//                .pathParam("chatHistoryId", 551)
//                .contentType("application/json")
//                .when()
//                .get("/chatHistory/{chatHistoryId}")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    @Order(1)
//    public void testGetChatHistoryByUserIdAndStatusEndpoint() {
//
//        given()
//                .pathParam("userId", 2)
//                .pathParam("status", "active")
//                .contentType("application/json")
//                .when()
//                .get("/chatHistory/status/{userId}/{status}")
//                .then()
//                .statusCode(200);
//    }

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
