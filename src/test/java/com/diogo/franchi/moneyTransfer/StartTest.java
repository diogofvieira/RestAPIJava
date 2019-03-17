package com.diogo.franchi.moneyTransfer;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;


public class StartTest {

    @Before
    public void setUp(){
        Start newRoutes = new Start();
        newRoutes.routes();
        awaitInitialization();

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;
    }

    @AfterClass
    public static void tearDown(){
        RestAssured.reset();
        stop();
    }

    @Test
    public void newAccountObjectsPOST() {
        given().body("{\"amount\": 200}").when().post("/account").then().assertThat().statusCode(201);
    }

    @Test
    public void newAccountFail(){
        given().body("").when().post("/account").then().assertThat().statusCode(400);
    }

    @Test
    public void getAllAccountsGET(){
        //Set Up
        given().body("{\"amount\": 100}").post("/account");
        given().body("{\"amount\": 300}").post("/account");
        //Test
        get("/accounts")
                .then()
                .assertThat()
                .statusCode(200)
                .body("amount", hasItems(100, 300));
    }

    @Test
    public void doTransferPOST(){
        //Set Up
        given().body("{\"amount\": 100}").post("/account");
        given().body("{\"amount\": 300}").post("/account");
        String accountNumberDebit = get("/accounts")
                .then()
                .extract()
                .jsonPath()
                .getString("find { it.amount==100 }.accountNumber");
        String accountNumberCredit = get("/accounts")
                .then()
                .extract()
                .jsonPath()
                .getString("find { it.amount==300 }.accountNumber");

        //Test 100 to 300 final 0
        given().body("{\"accountDebit\": " +
                      "\"" + accountNumberDebit + "\"," +
                     "\"accountCredit\": " +
                     "\"" + accountNumberCredit + "\"," +
                     "\"value\": 100}")
                .when()
                .post("/transfer")
                .then()
                .assertThat()
                .statusCode(200)
                .body("amount", equalTo(0));
    }

    @Test
    public void doTransferPOSTFail(){
        //Test
        given().body("{\"accountDebit\": " +
                "\"not a number\"," +
                "\"accountCredit\": " +
                "\"not a number\"," +
                "\"value\": 100}")
                .when()
                .post("/transfer")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void getAllAccountsGETNoMatchers(){
        //Set Up
        post("/accounts/delete");
        //Test
        get("/accounts").then().assertThat().statusCode(404);
    }

    @Test
    public void routeFail(){
        get("/not a valid route")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void internalErrorFail(){
        given().body("{{}}").when().post("/account").then().assertThat().statusCode(500);
    }


}
