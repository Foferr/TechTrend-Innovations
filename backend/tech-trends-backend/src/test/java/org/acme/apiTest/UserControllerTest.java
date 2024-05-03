package org.acme.apiTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.UserDTOs.UserPostDTO;
import org.acme.model.User;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {


    @Test
    @Order(1)
    public void testGetUserByIdEndpoint() {
        given()
                .when()
                .get("/user/1")
                .then()
                .body("id", equalTo(1))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("language", equalTo("en"))
                .body("birthday", equalTo("1990-05-15"))
                .body("email", equalTo("john.doe@example.com"))
                .body("userPassword", equalTo("password123"))
                .body("phone", equalTo("123456789"))
                .body("userType", equalTo("admin"))
                .body("createdAt", equalTo("2024-04-30T14:00:00"))
                .statusCode(200);
    }


    @Test
    @Order(1)
    public void testGetAllUsersEndpoint() {
           ValidatableResponse response = given()
                .when().get("/user")
                .then()
                .statusCode(200);

           response.body("size()", equalTo(4));
           response.body("email", hasItems("john.doe@example.com", "alex@gmail.com", "stalin@example.com"));
           response.body("firstName", hasItems("John", "Alex", "Joseph"));
           response.body("lastName", hasItems("Doe", "Rodriguez", "Stalin"));
    }

    @Test
    @Order(2)
    @Transactional
    public void testUpdateUserEndpoint() {
        UserPostDTO updatedUserDTO = new UserPostDTO();
        updatedUserDTO.setFirstName("UpdatedFirstName");
        updatedUserDTO.setLastName("UpdatedLastName");
        updatedUserDTO.setLanguage("eng");
        updatedUserDTO.setBirthday(LocalDate.of(1990, 1, 1));
        updatedUserDTO.setEmail("updated@example.com");
        updatedUserDTO.setUserPassword("updated123");
        updatedUserDTO.setPhone("987654321");
        updatedUserDTO.setUserType("user");

         given()
                .contentType(ContentType.JSON)
                .body(updatedUserDTO)
                .when()
                .put("/user/editUser/1")
                .then()
                .statusCode(200)
                .body("message", is("User updated successfully"));

        given()
                .when()
                .get("/user/1")
                .then()
                .body("id", equalTo(1))
                .body("firstName", equalTo("UpdatedFirstName"))
                .body("lastName", equalTo("UpdatedLastName"))
                .body("language", equalTo("eng"))
                .body("birthday", equalTo("1990-01-01"))
                .body("email", equalTo("updated@example.com"))
                .body("userPassword", equalTo("updated123"))
                .body("phone", equalTo("987654321"))
                .body("userType", equalTo("user"))
                .statusCode(200);
    }

    @Test
    @Order(3)
    @Transactional
    public void testDeleteUserEndpoint() {
        given()
                .when()
                .delete("/user/deleteUser/4")
                .then()
                .statusCode(200)
                .body("message", is("User deleted successfully"));

        given()
                .when()
                .get("/user/4")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

//    @Test
//    @Order(2)
//    @Transactional
//    public void testRegisterUserEndpoint() {
//        UserPostDTO userPostDTO = new UserPostDTO();
//        userPostDTO.setId(4L);
//        userPostDTO.setFirstName("Test");
//        userPostDTO.setLastName("User");
//        userPostDTO.setLanguage("eng");
//        userPostDTO.setBirthday(LocalDate.of(1990,1,1));
//        userPostDTO.setEmail("test@example.com");
//        userPostDTO.setUserPassword("test123");
//        userPostDTO.setPhone("123456789");
//        userPostDTO.setUserType("admin");
//        userPostDTO.setCreatedAt(LocalDateTime.of(2024,4,30,14,0));
//
//        // Send POST request to register a new user
//        ValidatableResponse response = given()
//                .contentType(ContentType.JSON)
//                .body(userPostDTO)
//                .when()
//                .post("/user/registerUser")
//                .then();
//
//        System.out.println("Status code: ");
//        System.out.println(response.extract().statusCode());
//        System.out.println("Response body: ");
//        System.out.println(response.extract().body().asPrettyString());
//
//        // Check if user creation was successful
////        assertEquals(201, response.extract().statusCode());
////        assertEquals("User created successfully", response.extract().body().asString());
//    }






//     @Test
//     @Order(2)
//     public void testRegisterUserEndpoint() {
//
//
//        JsonObject jsonObject = Json.createObjectBuilder()
//                .add("firstName", "Pepe")
//                .add("lastName", "Martinez")
//                .add("language", "esp")
//                .add("birthday", "2022-03-10")
//                .add("email", "pepe@gmail.com")
//                .add("userPassword", "password")
//                .add("phone", "123456789")
//                .add("userType", "admin")
//                .add("createdAt", "2022-03-10T12:15:50")
//                .build();
//
//         io.restassured.response.Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(jsonObject)
//                .when()
//                .post("/user/registerUser")
//                .thenReturn();
//
////                 .body("message", equalTo("User created successfully"));
//
//         int statusCode = response.statusCode();
//         String responseBody = response.body().asString();
//         System.out.println("Status code: ");
//         System.out.println(statusCode);
//         System.out.println("Response: ");
//         System.out.println(responseBody);
//     }

//    @Test
//    @Order(3)
//    public void testPostedUserIsPersisted() {
//        given()
//                .when()
//                .get("/user/4")
//                .then()
//                .body("id", equalTo(4))
//                .body("firstName", equalTo("Pepe"))
//                .body("lastName", equalTo("Martinez"))
//                .body("language", equalTo("esp"))
//                .body("birthday", equalTo("2022-03-10"))
//                .body("email", equalTo("pepe@gmail.com"))
//                .body("userPassword", equalTo("password"))
//                .body("phone", equalTo("123456789"))
//                .body("userType", equalTo("admin"))
//                .body("createdAt", equalTo("2022-03-10T12:15:50"))
//                .statusCode(Response.Status.OK.getStatusCode());
//    }




}
