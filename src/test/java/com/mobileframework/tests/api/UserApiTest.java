package com.mobileframework.tests.api;

import com.mobileframework.api.models.CreateUserRequest;
import com.mobileframework.api.models.CreateUserResponse;
import com.mobileframework.api.models.SingleUserResponse;
import com.mobileframework.api.specs.ApiSpecs;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;

public class UserApiTest {

    @Test
    public void createUserReturnsCreatedWithId() {
        CreateUserRequest request = new CreateUserRequest("Inna", "QA Automation");

        CreateUserResponse response = given()
                .spec(ApiSpecs.requestSpec())
                .body(request)
                .when()
                .post("/api/users")
                .then()
                .spec(ApiSpecs.createdSpec())
                .extract()
                .as(CreateUserResponse.class);

        assertNotNull(response.id(), "Created user should have an id");
        assertEquals(response.name(), "Inna");
    }

    @Test
    public void getUserReturnsCorrectUserData() {

        SingleUserResponse response = given().spec(ApiSpecs.requestSpec())
                .when()
                .get("/api/users/2")
                .then()
                .spec(ApiSpecs.successSpec())
                .body("data.id", equalTo(2))
                .extract().as(SingleUserResponse.class);

        assertEquals(response.data().id(), 2, "User id should be 2");
        assertNotNull(response.data().email(), "Email should not be null");
        assertTrue(response.data().email().contains("@"), "Email should contain @");

    }

    @Test
    public void getNonExistingUserReturnsNotFound() {
        given()
                .spec(ApiSpecs.requestSpec())
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }
}
