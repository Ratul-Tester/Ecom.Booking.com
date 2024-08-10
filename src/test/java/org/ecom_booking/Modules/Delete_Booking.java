package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Delete_Booking {

    Integer bookingId = 4807; //Hardcoded the Value initially...
    String token = "64427b91ddb080e"; //Hardcoded the Value initially...
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";
    String DELETE_BASE_PATH = BASE_PATH+"/"+bookingId;

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void deleteBookingDELETEMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(DELETE_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);

        response = requestSpecification.when().delete();

        validatableResponse = response.then();
        validatableResponse.statusCode(201);
        validatableResponse.log().all();

    }
}
