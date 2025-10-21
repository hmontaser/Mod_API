package com.moddakir.TestCases;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LoginApiTest {

    private String baseUrl = "https://revamp-auth.moddakir.com";
    private String loginEndpoint = "/api/auth/public/login";
    private RequestSpecification request;
    public String validToken; // Store a valid JWT token here
    public String email= "hmontaser00@moddakir.com";
    public String password= "Abc@@12345";

    public LoginApiTest() {}

    public LoginApiTest(String email, String password) throws JSONException
    {
        this.email=email;
        this.password=password;
        setup();
        testSuccessfulLogin();
        //System.err.println("Success to obtain JWT token: " + validToken);
    }

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }

    @Test
    public void testSuccessfulLogin() throws JSONException {
        //Create a valid user object
        JSONObject requestBody = getJsonObject(email, password);
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        validToken= response.jsonPath().getString("data.accessToken");
        response.then().log().all();
    }

    @Test
    public void testLoginWithInvalidEmail() throws JSONException {

        //Create a valid user object
        JSONObject requestBody = getJsonObject("a90@modd.com", "Test@123");
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        response.then().log().all();
        Assert.assertEquals(response.jsonPath().getString("error.description"), "student not found");

    }

    @Test
    public void testLoginWithInvalidPassword() throws JSONException {

        //Create a valid user object
        JSONObject requestBody = getJsonObject("a30@modd.com", "Test@125");
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        response.then().log().all();
        //Assert.assertEquals(response.getStatusCode(), 401);
        Assert.assertEquals(response.jsonPath().getString("error.description"), "student not found");
    }

    @Test
    public void testLoginWithEmptyEmail() throws JSONException {
        //Create a valid user object
        JSONObject requestBody = getJsonObject("", "Test@123");
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        response.then().log().all();
    }

    @Test
    public void testLoginWithEmptyPassword() throws JSONException {
        //Create a valid user object
        JSONObject requestBody = getJsonObject("a18@modd.com", "");
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        response.then().log().all();
    }

    @Test
    public void testLoginWithEmptyEmailAndPassword() throws JSONException {
        //Create a valid user object
        JSONObject requestBody = getJsonObject("", "");
        //Send the POST request to the login endpoint
        Response response = request.body(requestBody.toString()).post(loginEndpoint);
        response.then().log().all();
    }


    private JSONObject getJsonObject(String email, String password) throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("email",email);
        requestBody.put("username", "test_user18");
        requestBody.put("password",password);
        return requestBody;
    }
}



