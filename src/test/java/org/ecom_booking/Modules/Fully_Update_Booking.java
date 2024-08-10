package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Fully_Update_Booking {

    Integer bookingId = 2075; //Hardcoded the Value initially...
    String token = "0b3f9db1af583b9"; //Hardcoded the Value initially...
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String BASE_PATH = "/booking";
    String UPDATE_BASE_PATH = BASE_PATH+"/"+bookingId;
    String UPDATE_PAYLOAD = "{\n" +
            "    \"firstname\" : \"Balana\",\n" +
            "    \"lastname\" : \"Bala\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : \"2019-01-01\"\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"\n" +
            "}";
    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void fullyUpdatePUTMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(UPDATE_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token",token);
        requestSpecification.body(UPDATE_PAYLOAD);

        response = requestSpecification.when().put();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

    }
}
