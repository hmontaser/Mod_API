package com.moddakir.TestCases;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class AllAvailablePackages {

    private String baseUrl = "http://3.80.91.142:8081";
    private String allAvailablePackagesEndpoint = "/api/core/private/all-available-packages";
    private RequestSpecification requestAvailablePackage;
    private String validToken; // Store a valid JWT token here

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        requestAvailablePackage = given();
        requestAvailablePackage.header("Content-Type", "application/json");

    }

    @Test
    public void testSuccessfulReturnAllAvailablePackagesWithValidCurrency() throws JSONException {
        JSONObject requestBodyAvailablePackages = new JSONObject();
        requestBodyAvailablePackages.put("currency","USD");
        LoginApiTest loginApiTest=new LoginApiTest("a.mohamed@moddakir.com","Test@123");
        validToken = loginApiTest.validToken;
        if (validToken != null)
            requestAvailablePackage.header("Authorization", "Bearer " + validToken); // Set the authorization header for all tests

        Response response = requestAvailablePackage.body(requestBodyAvailablePackages.toString()).post(allAvailablePackagesEndpoint);
        response.then().log().all();
    }

    @Test
    public void testAllAvailablePackagesWithInValidCurrency() throws JSONException {
        JSONObject requestBodyAvailablePackages = new JSONObject();
        requestBodyAvailablePackages.put("currency","SSS");
        LoginApiTest loginApiTest=new LoginApiTest("a.mohamed@moddakir.com","Test@123");
        validToken = loginApiTest.validToken;
        if (validToken != null)
            requestAvailablePackage.header("Authorization", "Bearer " + validToken); // Set the authorization header for all tests

        Response response = requestAvailablePackage.body(requestBodyAvailablePackages.toString()).post(allAvailablePackagesEndpoint);
        response.then().log().all();
    }



}
