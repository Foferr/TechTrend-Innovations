package org.acme.apiTest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import org.acme.DTO.EventLogDTOs.EventLogPutDTO;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventControllerTests {

    @Test
    @Order(1)
    public void testGetAllEventLogsEndpoint() {
        ValidatableResponse response = given()
                .when()
                .get("/eventLog")
                .then()
                .statusCode(200);


        response.body("size()", equalTo(4));
        response.body("id", hasItems(1,2,3));
        response.body("userId", hasItems(1,3));
        response.body("eventTarget", hasItems("click", "button"));
        response.body("createdAt", hasItems("2024-04-28", "2024-04-29", "2024-04-30"));
        response.body("metadata", hasItems("{key: value}", "{name: alex}", "{place: mty}"));
    }

    @Test
    @Order(1)
    public void testGetFaqByUserId() {
        given()
                .when()
                .get("/eventLog/byUserId/3")
                .then()
                .body("[0].id", equalTo(3))
                .body("[0].userId", equalTo(3))
                .body("[0].eventTarget", equalTo("button"))
                .body("[0].createdAt", equalTo("2024-04-30"))
                .body("[0].metadata", equalTo("{place: mty}"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetFaqByDate() {
        given()
                .when()
                .get("/eventLog/byDate/2024-04-28")
                .then()
                .body("[0].id", equalTo(1))
                .body("[0].userId", equalTo(1))
                .body("[0].eventTarget", equalTo("click"))
                .body("[0].createdAt", equalTo("2024-04-28"))
                .body("[0].metadata", equalTo("{key: value}"))
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void testGetFaqByEventTarget() {
        given()
                .when()
                .get("/eventLog/byTarget/click")
                .then()
                .body("[0].id", equalTo(1))
                .body("[0].userId", equalTo(1))
                .body("[0].eventTarget", equalTo("click"))
                .body("[0].createdAt", equalTo("2024-04-28"))
                .body("[0].metadata", equalTo("{key: value}"))
                .statusCode(200);
    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateEventLogEndpoint() {
        EventLogPutDTO eventLogPutDTO = new EventLogPutDTO();
        eventLogPutDTO.setUserId(2L);
        eventLogPutDTO.setEventTarget("test");
        eventLogPutDTO.setMetadata("{test: correct}");

        given()
                .contentType(ContentType.JSON)
                .body(eventLogPutDTO)
                .when()
                .put("/eventLog/1")
                .then()
                .statusCode(200)
                .body("message", is("Event updated successfully"));
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteEventLogEndpoint() {
        given()
                .when()
                .delete("/eventLog/4")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/companyNews/4")
                .then()
                .statusCode(405);
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteAllEventLogsEndpoint() {
        given()
                .when()
                .delete("/eventLog")
                .then()
                .statusCode(202);

        given()
                .when()
                .get("/eventLog/")
                .then()
                .statusCode(404);
    }
}
