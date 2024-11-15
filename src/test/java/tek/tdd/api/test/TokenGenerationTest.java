package tek.tdd.api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tek.tdd.api.models.EndPoints;
import tek.tdd.base.ApiTestBase;

import java.util.Map;

public class TokenGenerationTest extends ApiTestBase {
    private static final Logger LOGGER = LogManager.getLogger(TokenGenerationTest.class);


    @Test
    public void generateValidToken() {
        RequestSpecification request = getDefaultRequest();
        Map<String, String> requestBody = getTokenRequestsBody("supervisor", "tek_supervisor");
        request.body(requestBody);

        Response response = request.when().post(EndPoints.TOKEN.getValue());
        response.then().statusCode(200);
        response.prettyPrint();
        LOGGER.info("Response is {}", response.asPrettyString());


    }

    @Test
    public void generateOperatorOnlyToken() {
        RequestSpecification request = getDefaultRequest();
        Map<String, String> requestBody = getTokenRequestsBody("operator_readonly", "Tek4u2024");
        request.body(requestBody);

        Response response = request.when().post(EndPoints.TOKEN.getValue());
        response.then().statusCode(200);
        response.prettyPrint();


    }

    @Test(dataProvider = "credentials")
    public void generateTokenWithCredentials(String username, String password) {


        RequestSpecification request = getDefaultRequest();
        Map<String, String> requestBody = getTokenRequestsBody(username, password);
        request.body(requestBody);

        Response response = request.when().post(EndPoints.TOKEN.getValue());
        response.then().statusCode(200);

        String actualUsername = response.body().jsonPath().getString("username");
        Assert.assertEquals(actualUsername, "supervisor", "username should match");

        String token = response.body().jsonPath().getString("token");
        Assert.assertNotNull(token);

        LOGGER.info("Response is {}", response.asPrettyString());


    }

    @Test(dataProvider = "negativeCredentials")
    public void negativeTokenWithErrors(String username, String password, int statusCode, String expectedErrorMessage) {
        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);
        Map<String, String> requestBody = getTokenRequestsBody(username, password);
        request.body(requestBody);

        Response response = request.when().post(EndPoints.TOKEN.getValue());
        response.then().statusCode(statusCode);
        response.prettyPrint();

        String actualError = response.body().jsonPath().getString("errorMessage");
        Assert.assertEquals(actualError, expectedErrorMessage, "Error Message should match");

    }

    @DataProvider
    private String[][] credentials() {
        return new String[][]{
                {"supervisor", "tek_supervisor"},
                {"operator_readonly", "Tek4u2024"}
        };
    }


    @DataProvider
    private Object[][] negativeCredentials() {
        return new Object[][]{
                {"super", "tek_supervisor", 404, "User super not found"},
                {"supervisor", "noPass", 400, "Password not match"},
        };
    }

}
