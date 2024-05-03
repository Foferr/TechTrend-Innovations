package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import org.acme.DTO.EventLogDTOs.EventLogPutDTO;
import org.acme.DTO.FaqDTOs.FaqUpdateRequestDTO;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FaqControllerTests {

    @Test
    @Order(1)
    public void testGetAllUsersEndpoint() {
        ValidatableResponse response = given()
                .when().get("/FAQ/getAll")
                .then()
                .statusCode(200);

        response.body("size()", equalTo(4));
        response.body("id", hasItems(1,2,3));
        response.body("question", hasItems("What is neoris?", "How to contact neoris", "Who created this web app?"));
        response.body("answer", hasItems("neoris is a digital...", "Go the about page and...", "TechTrends is..."));
        response.body("admin.id", hasItems(1,3));
        response.body("status", hasItems("published", "drafted"));
        response.body("createdAt", hasItems("2024-04-30T14:00:00"));
    }

    @Test
    @Order(1)
    public void testGetFaqByAdminId() {
        given()
                .when()
                .get("/FAQ/getByAdminId/1")
                .then()
                .body("[0].id", equalTo(1))
                .body("[0].question", equalTo("What is neoris?"))
                .body("[0].answer", equalTo("neoris is a digital..."))
                .body("[0].admin.id", equalTo(1))
                .body("[0].status", equalTo("published"))
                .body("[0].createdAt", equalTo("2024-04-30T14:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetFaqByStatus() {
        given()
                .when()
                .get("/FAQ/status/drafted")
                .then()
                .body("[0].id", equalTo(2))
                .body("[0].question", equalTo("How to contact neoris"))
                .body("[0].answer", equalTo("Go the about page and..."))
                .body("[0].admin.id", equalTo(3))
                .body("[0].status", equalTo("drafted"))
                .body("[0].createdAt", equalTo("2024-04-30T14:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetFaqBySelfId() {
        given()
                .when()
                .get("/FAQ/1")
                .then()
                .body("id", equalTo(1))
                .body("question", equalTo("What is neoris?"))
                .body("answer", equalTo("neoris is a digital..."))
                .body("admin.id", equalTo(1))
                .body("status", equalTo("published"))
                .body("createdAt", equalTo("2024-04-30T14:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateFaqEndpoint() {
        FaqUpdateRequestDTO faqUpdateRequestDTO = new FaqUpdateRequestDTO();
        faqUpdateRequestDTO.setQuestion("test");
        faqUpdateRequestDTO.setAnswer("test");
        faqUpdateRequestDTO.setStatus("test");

        given()
                .contentType(ContentType.JSON)
                .body(faqUpdateRequestDTO)
                .when()
                .put("/FAQ/1/1")
                .then()
                .statusCode(200)
                .body("message", is("FAQ updated successfully"));

        given()
                .when()
                .get("/FAQ/1")
                .then()
                .body("id", equalTo(1))
                .body("question", equalTo("test"))
                .body("answer", equalTo("test"))
                .body("admin.id", equalTo(1))
                .body("status", equalTo("test"))
                .body("createdAt", equalTo("2024-04-30T14:00:00"))
                .statusCode(200);
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteFaqEndpoint() {
        given()
                .when()
                .delete("/FAQ/1/4")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/FAQ/4")
                .then()
                .statusCode(404);
    }
}
