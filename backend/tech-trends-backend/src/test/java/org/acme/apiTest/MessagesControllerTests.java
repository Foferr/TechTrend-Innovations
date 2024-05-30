package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessagesControllerTests {

    @Test
    @Order(1)
    public void testGetAllMessagesEndpoint() {
        ValidatableResponse response = given()
                .when().get("/messages")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(3));
        response.body("id", hasItems(1,2,3));
        response.body("chatHistory.id", hasItems(1));
        response.body("senderType", hasItems("user", "bot"));
        response.body("messageContent", hasItems("hola como estas", "soy una inteligencia artificial", "como puedo contactar a neoris?"));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
    }

    @Test
    @Order(1)
    public void testGetMessageByUserId() {
        ValidatableResponse response = given()
                .when().get("/messages/byUserId/1")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(3));
        response.body("id", hasItems(1,2,3));
        response.body("chatHistory.id", hasItems(1));
        response.body("senderType", hasItems("user", "bot"));
        response.body("messageContent", hasItems("hola como estas", "soy una inteligencia artificial", "como puedo contactar a neoris?"));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
    }

    @Test
    @Order(1)
    public void testGetMessageBySenderType() {
        given()
                .when()
                .get("/messages/bySenderType/bot")
                .then()
                .body("[0].id", equalTo(2))
                .body("[0].chatHistory.id", equalTo(1))
                .body("[0].senderType", equalTo("bot"))
                .body("[0].messageContent", equalTo("soy una inteligencia artificial"))
                .body("[0].createdAt", equalTo("2024-04-30T15:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetMessageByChatId() {
        ValidatableResponse response = given()
                .when().get("/messages/byChatId/1")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(3));
        response.body("id", hasItems(1,2,3));
        response.body("chatHistory.id", hasItems(1));
        response.body("senderType", hasItems("user", "bot"));
        response.body("messageContent", hasItems("hola como estas", "soy una inteligencia artificial", "como puedo contactar a neoris?"));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
    }

    @Test
    @Order(1)
    public void testGetMessageByUserIdAndChatId() {
        ValidatableResponse response = given()
                .when().get("/messages/byChatHistoryIdAndUserId/1/1")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(3));
        response.body("id", hasItems(1,2,3));
        response.body("chatHistory.id", hasItems(1));
        response.body("senderType", hasItems("user", "bot"));
        response.body("messageContent", hasItems("hola como estas", "soy una inteligencia artificial", "como puedo contactar a neoris?"));
        response.body("createdAt", hasItems("2024-04-30T14:00:00", "2024-04-30T15:00:00", "2024-04-30T16:00:00"));
    }
}
