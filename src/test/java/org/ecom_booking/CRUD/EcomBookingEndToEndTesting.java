package org.ecom_booking.CRUD;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.Objects;

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

        String fullResponseJSONData = response.asString();
        System.out.println(fullResponseJSONData);

        Assert.assertNotNull(token, "Error");
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

        //validatableResponse.body("firstname", Matchers.equalTo("Balana"));
        //validatableResponse.body("lastname", Matchers.equalTo("Bala"));

        bookingID = response.then().log().all().extract().path("bookingid");
        String f = response.then().log().all().extract().path("booking.firstname");
        System.out.println("name: " + f);
        Assert.assertEquals("Balana", f);
        System.out.println("The booking is created and ID no. is : " + bookingID);
        System.out.println("End of creating bookingID, and is stored for further process..");

        String fullResponseJSONData = response.asString();

        JsonPath jsonPath = new JsonPath(fullResponseJSONData);
        Integer bookingIDJsonPathExtracted = jsonPath.getInt("bookingid");
        String firstNameJsonPathExtracted = jsonPath.getString("booking.firstname");
        String lastNameJsonPathExtracted = jsonPath.getString("booking.lastname");
        Integer totalPriceJsonPathExtracted = jsonPath.getInt("booking.totalprice");
        Boolean depositPaidJsonPathExtracted = jsonPath.getBoolean("booking.depositpaid");
        String bookingDatesJsonPathExtracted = jsonPath.getString("booking.bookingdates");
        String additionalneedsJsonPathExtracted = jsonPath.getString("booking.additionalneeds");

        System.out.println(bookingIDJsonPathExtracted);
        System.out.println(firstNameJsonPathExtracted);
        System.out.println(lastNameJsonPathExtracted);
        System.out.println(totalPriceJsonPathExtracted);
        System.out.println(depositPaidJsonPathExtracted);
        System.out.println(bookingDatesJsonPathExtracted);
        System.out.println(additionalneedsJsonPathExtracted);
                                    //TestNG Assertion
        Assert.assertEquals(firstNameJsonPathExtracted, "Balana");
        Assert.assertEquals(lastNameJsonPathExtracted, "Bala");
        Assert.assertEquals(totalPriceJsonPathExtracted, 111);
        Assert.assertEquals(depositPaidJsonPathExtracted, true);
        Assert.assertEquals(bookingDatesJsonPathExtracted, "[checkin:2018-01-01, checkout:2019-01-01]");
        Assert.assertEquals(additionalneedsJsonPathExtracted, "Breakfast");
                                    //AssertJ Assertions
        assertThat(bookingIDJsonPathExtracted).isNotZero().isNotNegative();
        assertThat(totalPriceJsonPathExtracted).isNotZero().isNotNegative();

        System.out.println(Objects.equals(f, firstNameJsonPathExtracted));

        System.out.println("All assertions are passed");

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

        String fullResponse = response.asString();

        JsonPath jsonPath = new JsonPath(fullResponse);
        String firstName = jsonPath.getString("firstname");
        String lastName = jsonPath.getString("lastname");
        String totalPrice = jsonPath.getString("totalprice");
        String depositPaid = jsonPath.getString("depositpaid");
        String bookingDates = jsonPath.getString("bookingdates");
        String additionalNeeds = jsonPath.getString("additionalneeds");
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(totalPrice);
        System.out.println(depositPaid);
        System.out.println(bookingDates);
        System.out.println(additionalNeeds);
                                    //TestNG Assertions
        Assert.assertEquals(firstName,"Balana");
        Assert.assertEquals(lastName,"Bala");
        Assert.assertEquals(totalPrice,"111");
        Assert.assertEquals(depositPaid,"true");
        Assert.assertEquals(bookingDates,"[checkin:2018-01-01, checkout:2019-01-01]");
        Assert.assertEquals(additionalNeeds,"Breakfast");
                                    //AssertJ Assertions
        assertThat(firstName).isNotBlank().isNotEmpty().isEqualTo("Balana").isAlphabetic();
        assertThat(lastName).isNotBlank().isNotEmpty().isEqualTo("Bala").isAlphabetic();
        assertThat(totalPrice).isNotBlank().isNotEmpty().containsOnlyDigits().isEqualTo("111");
        assertThat(depositPaid).asBoolean().isTrue();
        assertThat(bookingDates).isEqualTo("[checkin:2018-01-01, checkout:2019-01-01]");
        assertThat(additionalNeeds).isEqualTo("Breakfast");

        System.out.println("All assertions are passed");

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
        String firname = response.then().log().all().extract().path("firstname");
        Assert.assertEquals("Ramesh", firname);
        System.out.println(firname);
        System.out.println("End of fully updating the new booking details");

        String fullResponse = response.asString();

        JsonPath jsonPath = new JsonPath(fullResponse);

        String fname = jsonPath.getString("firstname");
        String lname = jsonPath.getString("lastname");
        String totalPrice = jsonPath.getString("totalprice");
        String depositPaid = jsonPath.getString("depositpaid");
        String bookingDates = jsonPath.getString("bookingdates");
        String additionalNeeds = jsonPath.getString("additionalneeds");

        Assert.assertEquals(fname,"Ramesh");
        Assert.assertEquals(lname,"Bala");
        Assert.assertEquals(totalPrice,"111");
        Assert.assertEquals(depositPaid,"true");
        Assert.assertEquals(bookingDates,"[checkin:2018-01-01, checkout:2019-01-01]");
        Assert.assertEquals(additionalNeeds,"Breakfast");

        System.out.println("All assertions are passed");

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
        String fname = response.then().log().all().extract().path("firstname");
        Assert.assertEquals("Ratul", fname);
        System.out.println(fname);
        System.out.println("End of partially updating the new booking details");

        String fullRespone = response.asString();

        JsonPath jsonPath = new JsonPath(fullRespone);

        String finame = jsonPath.getString("firstname");
        String laname = jsonPath.getString("lastname");

        Assert.assertEquals(finame,"Ratul");
        Assert.assertEquals(laname,"Nandy");

        System.out.println("All assertions are passed");



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
