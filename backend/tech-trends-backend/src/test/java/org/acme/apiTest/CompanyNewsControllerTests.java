package org.acme.apiTest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.acme.model.CompanyNews;
import org.acme.model.User;
import org.acme.service.CompanyNewsService;
import org.acme.service.UserService;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


@QuarkusTest
public class CompanyNewsControllerTests {



    @Test
    public void testGetAllCompanyNewsEndpoint() {
        given()
                .when().get("/companyNews")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCompanyNewsByUserId() {
        given()
                .when().get("/companyNews/user/2")
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
    public void testGetCompanyNewsByNewsIdEndpoint() {

        given()
                .pathParam("companyNewsId", 551)
                .contentType("application/json")
                .when()
                .get("/companyNews/{companyNewsId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCompanyNewsByStatusEndpoint() {

        given()
                .pathParam("userId", 2)
                .pathParam("status", "active")
                .contentType("application/json")
                .when()
                .get("/companyNews/status/{userId}/{status}")
                .then()
                .statusCode(200);
    }
    
    @Test
    public void testGetCompanyNewsByAdminIdEndpoint() {

        given()
                .pathParam("adminId", 2)
                .contentType("application/json")
                .when()
                .get("/companyNews/adminId/{userId}/{adminId}")
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
