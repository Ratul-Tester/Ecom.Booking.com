package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Partially_Update_Booking {

    Integer bookingId = 2075; //Hardcoded the Value initially...
    String token = "d1850a417cec2cd"; //Hardcoded the Value initially...
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";
    String PARTIALLY_UPDATE_BASE_PATH = BASE_PATH+"/"+bookingId;
    String PARTIALLY_UPDATE_PAYLOAD = "{\n" +
            "    \"firstname\" : \"Ratul\",\n" +
            "    \"lastname\" : \"Nandy\"\n" +
            "}";
    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void partiallyUpdatePUTMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(PARTIALLY_UPDATE_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);
        requestSpecification.body(PARTIALLY_UPDATE_PAYLOAD);

        response = requestSpecification.when().patch();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

    }
}
