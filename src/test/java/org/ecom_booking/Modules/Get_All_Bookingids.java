package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Get_All_Bookingids {
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BOOKING_BASE_PATH = "/booking";

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void getAllBookingIdsGETMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BOOKING_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().log().all().get();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);

    }
}
