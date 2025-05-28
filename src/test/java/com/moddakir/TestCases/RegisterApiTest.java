package com.moddakir.TestCases;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class RegisterApiTest {

    private String baseUrl = "http://3.80.91.142:8080";
    private String registerEndpoint = "/api/auth/public/student/register";
    private RequestSpecification request;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }

    @Test
    public void testSuccessfulRegistration() throws JSONException {
        //create a user
        JSONObject requestBody = getJsonObject("a20@modd.com","Test@123");

        /*given()
                .baseUri("http://3.80.91.142:8080")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
        .when()
                .post("/api/auth/public/student/register")
        .then()
                .log().all()
                .assertThat().body("data.student.email", equalTo("a5@modd.com"));
        */

        // Send the POST request to the register endpoint
        Response response = request.body(requestBody.toString()).post(registerEndpoint);
        response.then().log().all(); // Log the response to see any error details
        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("result"), "Succeeded");
        Assert.assertEquals(response.jsonPath().getString("data.student.email"), "a20@modd.com");
        Assert.assertEquals(response.jsonPath().getString("data.student.fullName"), "Test User");
        Assert.assertEquals(response.jsonPath().getString("data.student.gender"), "male");

    }

    @Test
    public void testEmailAlreadyExists() throws JSONException {
        //create a user
        JSONObject requestBody = getJsonObject("a19@modd.com","Test@123");
        // Send the POST request to the register endpoint
        Response response = request.body(requestBody.toString()).post(registerEndpoint);
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("error.message"), "email already completed registeration");

    }

    @Test
    public void testRegisterWithEmptyEmail() throws JSONException {
        //create a user
        JSONObject requestBody = getJsonObject("","Test@123");
        // Send the POST request to the register endpoint
        Response response = request.body(requestBody.toString()).post(registerEndpoint);
        response.then().log().all();

    }

    @Test
    public void testRegisterWithEmptyPassword() throws JSONException {
        //create a user
        JSONObject requestBody = getJsonObject("a19@modd.com","");
        // Send the POST request to the register endpoint
        Response response = request.body(requestBody.toString()).post(registerEndpoint);
        response.then().log().all();
    }

    @Test
    public void testRegisterWithinvalidEmail() throws JSONException {
        //create a user
        JSONObject requestBody = getJsonObject("test.com","Test@123");
        // Send the POST request to the register endpoint
        Response response = request.body(requestBody.toString()).post(registerEndpoint);
        response.then().log().all();

    }


    private JSONObject getJsonObject(String email, String password) throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("email",email);
        requestBody.put("username","test_user19");
        requestBody.put("password",password);
        requestBody.put("fullName","Test User");
        requestBody.put("gender","male");
        return requestBody;
    }


}
