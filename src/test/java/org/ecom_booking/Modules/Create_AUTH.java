package org.ecom_booking.Modules;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Create_AUTH {

    String BASE_URL = "https://restful-booker.herokuapp.com";
    String AUTH_BASE_PATH = "/auth";
    String AUTH_PAYLOAD = "{\n" +
            "    \"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";
    String token;
    RequestSpecification requestSpecification = RestAssured.given();
    Response response;
    ValidatableResponse validatableResponse;

    @Test
    public void createAuthTokenPOSTMethod(){
        requestSpecification.baseUri(BASE_URL);
        requestSpecification.basePath(AUTH_BASE_PATH);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(AUTH_PAYLOAD);

        response = requestSpecification.when().post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);
        validatableResponse.log().all();

        token = response.then().log().all().extract().path("token");
        System.out.println("The Created Token is : "+token);
    }
}
