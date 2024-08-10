package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Booking_App_HealthCheck extends Create_AUTH {

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String PING_BASE_PATH = "/ping";

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void appHealthGETMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(PING_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().get();

        validatableResponse = response.then();
        validatableResponse.statusCode(201);
        validatableResponse.log().all();

    }
}
