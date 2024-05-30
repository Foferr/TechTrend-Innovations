package org.acme.apiTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.model.CompanyNews;
import org.acme.model.User;
import org.acme.service.CompanyNewsService;
import org.acme.service.UserService;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;


@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyNewsControllerTests {



    @Test
    @Order(1)
    public void testGetAllCompanyNewsEndpoint() {
        ValidatableResponse response = given()
                .when()
                .get("/companyNews")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(4));
        response.body("id", hasItems(1,2,3));
        response.body("title", hasItems("Primer titulo", "Segundo titulo", "Tercer titulo"));
        response.body("newsContent", hasItems("primer contenido", "segundo contenido", "tercer contenido"));
        response.body("user.id", hasItems(1));
        response.body("status", hasItems("published", "drafted"));
        response.body("createdAt", hasItems("2024-04-30T15:00:00", "2024-04-30T16:00:00", "2024-04-30T17:00:00"));
    }

    @Test
    @Order(1)
    public void testGetCompanyNewsByAdminId() {
        given()
                .when()
                .get("/companyNews/byAdminId/1")
                .then()
                .body("[0].id", equalTo(1))
                .body("[0].title", equalTo("Primer titulo"))
                .body("[0].newsContent", equalTo("primer contenido"))
                .body("[0].user.id", equalTo(1))
                .body("[0].status", equalTo("published"))
                .body("[0].createdAt", equalTo("2024-04-30T15:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetCompanyNewsByNewsId() {
        given()
                .when()
                .get("/companyNews/byId/1")
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("Primer titulo"))
                .body("newsContent", equalTo("primer contenido"))
                .body("user.id", equalTo(1))
                .body("status", equalTo("published"))
                .body("createdAt", equalTo("2024-04-30T15:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetCompanyNewsByStatus() {
        ValidatableResponse response = given()
                .when()
                .get("/companyNews/byStatus/published")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(3));
        response.body("id", hasItems(1,3));
        response.body("title", hasItems("Primer titulo", "Tercer titulo"));
        response.body("newsContent", hasItems("primer contenido", "tercer contenido"));
        response.body("user.id", hasItems(1,3));
        response.body("status", hasItems("published"));
        response.body("createdAt", hasItems("2024-04-30T15:00:00", "2024-04-30T17:00:00"));
    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateCompanyNewsEndpoint() {
        String status = "drafted";

        given()
                .queryParam("status", status)
                .when()
                .put("/companyNews/1/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("title", equalTo("Primer titulo"))
                .body("newsContent", equalTo("primer contenido"))
                .body("user.id", equalTo(1))
                .body("status", equalTo("drafted"))
                .body("createdAt", equalTo("2024-04-30T15:00:00"));

        given()
                .when()
                .get("/companyNews/byId/1")
                .then()
                .body("id", equalTo(1))
                .body("title", equalTo("Primer titulo"))
                .body("newsContent", equalTo("primer contenido"))
                .body("user.id", equalTo(1))
                .body("status", equalTo("drafted"))
                .body("createdAt", equalTo("2024-04-30T15:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteCompanyNewsEndpoint() {
        given()
                .when()
                .delete("/companyNews/4")
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/companyNews/4")
                .then()
                .statusCode(405);
    }

//    @Test
//    @TestTransaction
//    @Transactional
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
