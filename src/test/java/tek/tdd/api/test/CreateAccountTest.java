package tek.tdd.api.test;

import com.aventstack.extentreports.service.ExtentTestManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.api.models.EndPoint.EndPoints;
import tek.tdd.api.models.Requests.AccountRequest;
import tek.tdd.api.models.Response.AccountResponse;
import tek.tdd.base.ApiTestBase;

public class CreateAccountTest extends ApiTestBase {


    @Test
    public void createValidAccount() {
        //  created valid account / add-primary-account
        // verify status code 201
        // use POJO Request Body

        AccountRequest request = AccountRequest.builder()
                .email("anytigns44@gmail.com")
                .firstName("Anh")
                .lastName("Joo")
                .title("Mr.")
                .gender("MALE")
                .maritalStatus("SINGLE")
                .employmentStatus("YES")
                .dateOfBirth("2000-10-10")
                .build();

        Response response = getDefaultRequest().body(request)
                .when().post(EndPoints.ADD_PRIMARY_ACCOUNT.getValue())
                .then().statusCode(201)
                .extract().response();

        ExtentTestManager.getTest().info(response.asPrettyString());

        AccountResponse accountResponse = response.body().jsonPath().getObject("", AccountResponse.class);
        Assert.assertNotNull(accountResponse);


    }
}
