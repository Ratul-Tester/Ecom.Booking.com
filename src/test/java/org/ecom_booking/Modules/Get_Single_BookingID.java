package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Get_Single_BookingID {
    Integer bookingId = 2075; //Hardcoded the Value initially...
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";
    String BOOKINGID_BASE_PATH = BASE_PATH+"/"+bookingId;

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void getParticularBookingGETMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BOOKINGID_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().get();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

    }
}
