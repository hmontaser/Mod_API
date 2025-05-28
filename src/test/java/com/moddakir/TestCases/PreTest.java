

package com.moddakir.TestCases;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PreTest {

    @Test
    public void getAllUsers() {
        given()
                .baseUri("https://reqres.in/")
        .when()
                .get("/api/users?page=2")
        .then()
                .log().all();
    }
}
