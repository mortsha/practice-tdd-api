package tek.tdd.api.test;

import com.aventstack.extentreports.service.ExtentTestManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.api.models.EndPoint.EndPoints;
import tek.tdd.api.models.Requests.TokenRequest;
import tek.tdd.api.models.Response.AccountType;
import tek.tdd.api.models.Response.TokenResponse;
import tek.tdd.base.ApiTestBase;

public class GetPrimaryAccountTest extends ApiTestBase {


    @Test
    public void getPrimaryAccount() {
        RequestSpecification request = getDefaultRequest();
        request.queryParam("primaryPersonId", 247);

        Response response = request.when().get(EndPoints.GET_PRIMARY_ACCOUNT.getValue());
        ExtentTestManager.getTest().info(response.getHeaders().toString());
        response.then().statusCode(200);
        response.prettyPrint();

        String actualEmail = response.body().jsonPath().getString("email");
        Assert.assertEquals(actualEmail, "something@gmail.com", "Email should match");

    }

    @Test
    public void validateGetAccountNotExist() {
        Response response = getDefaultRequest().queryParam("primaryPersonId", 25252525)
                .when()
                .get(EndPoints.GET_PRIMARY_ACCOUNT.getValue())
                .then()
                .statusCode(404)
                .extract()
                .response();
        String actualErrorMessage = response.body().jsonPath().getString("errorMessage");
        Assert.assertEquals(actualErrorMessage, "Account with id 25252525 not exist");

    }

    @Test
    public void convertResponseToPOJO(){
        TokenRequest tokenRequest = new TokenRequest("supervisor","tek_supervisor");
        Response response =  getDefaultRequest().body(tokenRequest)
                .when()
                .post(EndPoints.TOKEN.getValue())
                .then().statusCode(200)
                .extract()
                .response();

        ExtentTestManager.getTest().info(response.prettyPrint());

        // convert response to POJO
        TokenResponse token =  response.body().jsonPath().getObject("",TokenResponse.class);
        Assert.assertEquals(token.getUsername(), "supervisor");
        Assert.assertNotNull(token.getToken());
        Assert.assertEquals(token.getAccountType(), AccountType.CSR);

    }
}
