package tek.tdd.api.test;

import com.aventstack.extentreports.service.ExtentTestManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.api.models.EndPoint.EndPoints;
import tek.tdd.api.models.Requests.TokenRequest;
import tek.tdd.api.models.Response.PlanCodeList;
import tek.tdd.api.models.Response.PlanCodeResponse;
import tek.tdd.base.ApiTestBase;

import java.util.List;

public class PlanCodeTest extends ApiTestBase {

    private static final Logger LOGGER = LogManager.getLogger(PlanCodeTest.class);

    @Test
    public void validatePlanCode() {
        TokenRequest tokenRequest = new TokenRequest("supervisor", "tek_supervisor");

        String token = getDefaultRequest().body(tokenRequest)
                .when()
                .post(EndPoints.TOKEN.getValue())
                .then().statusCode(200)
                .extract().response().jsonPath().getString("token");

        Response response = getDefaultRequest()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(EndPoints.GET_ALL_PLAN_CODE.getValue())
                .then()
                .statusCode(200)
                .extract()
                .response();

        ExtentTestManager.getTest().info(response.asPrettyString());
        LOGGER.info(response.asPrettyString());

        List<PlanCodeResponse> planCodes = response.body().jsonPath().getList("", PlanCodeResponse.class);
        Assert.assertNotNull(planCodes);

        Assert.assertEquals(planCodes.size(), 4);

        for (PlanCodeResponse planCode : planCodes) {
            Assert.assertFalse(planCode.isPlanExpired());
        }

    }
}
