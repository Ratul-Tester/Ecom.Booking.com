package org.ecom_booking.CRUD;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class EcomBookingEndToEndTesting {

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String AUTH_BASE_PATH = "/auth";
    String BASE_PATH = "/booking";
    String AUTH_PAYLOAD = "{\n" +
            "    \"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";
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


    String UPDATE_PAYLOAD = "{\n" +
            "    \"firstname\" : \"Ramesh\",\n" +
            "    \"lastname\" : \"Bala\",\n" +
            "    \"totalprice\" : 111,\n" +
            "    \"depositpaid\" : true,\n" +
            "    \"bookingdates\" : {\n" +
            "        \"checkin\" : \"2018-01-01\",\n" +
            "        \"checkout\" : \"2019-01-01\"\n" +
            "    },\n" +
            "    \"additionalneeds\" : \"Breakfast\"\n" +
            "}";

    String PARTIALLY_UPDATE_PAYLOAD = "{\n" +
            "    \"firstname\" : \"Ratul\",\n" +
            "    \"lastname\" : \"Nandy\"\n" +
            "}";

    String token;
    Integer bookingID;

    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Description("Token will be created")
    @Test(priority = 1)
    public void createAuthTokenPOSTMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(AUTH_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(AUTH_PAYLOAD);

        response = requestSpecification.when().post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

        token = response.then().log().all().extract().path("token");
        System.out.println("The Created Token is : " + token);
        System.out.println("End of creating token, and is stored for further process..");
    }

    @Description("BookingID will be created")
    @Test(priority = 2)
    public void createBookingPOSTMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(CREATE_PAYLOAD);

        response = requestSpecification.when().post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

        bookingID = response.then().log().all().extract().path("bookingid");
        System.out.println("The booking is created and ID no. is : " + bookingID);
        System.out.println("End of creating bookingID, and is stored for further process..");
    }

    @Description("Getting the newly created bookingID details")
    @Test(priority = 3)
    public void getParticularBookingGETMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH + "/" + bookingID);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().get();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
        System.out.println("End of getting the details of newly created booking");
    }

    @Description("Fully updating the new booking details")
    @Test(priority = 4)
    public void fullyUpdatePUTMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH + "/" + bookingID);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);
        requestSpecification.body(UPDATE_PAYLOAD);

        response = requestSpecification.when().put();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
        System.out.println("End of fully updating the new booking details");
    }

    @Description("Again partially updating the booking details")
    @Test(priority = 5)
    public void partiallyUpdatePUTMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH + "/" + bookingID);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);
        requestSpecification.body(PARTIALLY_UPDATE_PAYLOAD);

        response = requestSpecification.when().patch();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();
        System.out.println("End of partially updating the new booking details");
    }

    @Description("Deleting the existing booking details")
    @Test(priority = 6)
    public void deleteBookingDELETEMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH + "/" + bookingID);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.cookie("token", token);

        response = requestSpecification.when().delete();

        validatableResponse = response.then();
        validatableResponse.statusCode(201);
        validatableResponse.log().all();
        System.out.println("End of deleting the new booking details");
    }

    @Description("Checking the deleted bookingID")
    @Test(priority = 7)
    public void checkParticularBookingIDGETMethod() {
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(BASE_PATH + "/" + bookingID);
        requestSpecification.contentType(ContentType.JSON);

        response = requestSpecification.when().get();

        validatableResponse = response.then();
        validatableResponse.statusCode(404);
        validatableResponse.log().all();
        System.out.println("End of checking the deleted bookingID, and end of testing ecom.booking..");

    }

}
