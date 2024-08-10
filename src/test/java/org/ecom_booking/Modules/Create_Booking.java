package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Create_Booking {
    String BASE_URL = "https://restful-booker.herokuapp.com";
    String CREATE_BASE_PATH = "/booking";
    String CREATE_PAYLOAD = "{\n" +
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
    Integer bookingID;

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void createBookingPOSTMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(CREATE_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(CREATE_PAYLOAD);

        response = requestSpecification.when().post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

        bookingID = response.then().log().all().extract().path("bookingid");
        System.out.println("The Created Token is : "+bookingID);

    }
}
