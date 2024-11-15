package tek.tdd.api.test;

import com.aventstack.extentreports.service.ExtentTestManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tek.tdd.api.models.EndPoint.EndPoints;
import tek.tdd.api.models.Requests.TokenRequest;
import tek.tdd.api.models.Response.AccountResponse;
import tek.tdd.base.ApiTestBase;
import tek.tdd.utility.DatabaseUtility;

import java.sql.*;

public class DatabaseConnectivityTest extends ApiTestBase {
    Connection connection;
    private static int accountId;

    @Test
    public void databaseConnectivity() {
        // create connection
        // create statement
        // execute query
        // get result set

        String url = "jdbc:mysql://tek-database-server.mysql.database.azure.com:3306/tek_insurance-app";
        String username = "tek_student_user";
        String password = "FEB_2024";

        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from primary_person;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("date_of_birth"));
            }

            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException("Failed to connect the database" + exception.getMessage());
        }


    }

    @Test
    public void returnLastIdFromPrimaryPerson() throws SQLException {
        String url = "jdbc:mysql://tek-database-server.mysql.database.azure.com:3306/tek_insurance-app";
        String username = "tek_student_user";
        String password = "FEB_2024";

        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = "select max(id) as madId from primary_person";
            ResultSet resultSet = statement.executeQuery(query);

            int lastId = resultSet.getInt("madId");
            System.out.println(lastId);


            connection.close();

        } catch (SQLException exception) {
            throw new RuntimeException("Failed to connect the database" + exception.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Test()
    public void getAccountWithDatabaseValidation() throws SQLException {
        DatabaseUtility dbUtility = new DatabaseUtility();
        String query = "select id, email, first_name from primary_person order by id desc limit 1;";
        ResultSet result = dbUtility.executeQuery(query);
        result.next();
        accountId = result.getInt("id");
        String expectedEmail = result.getString("email");
        String expectedFirstName = result.getString("first_name");

        Response response = getDefaultRequest().queryParam("primaryPersonId", DatabaseConnectivityTest.accountId)
                .when()
                .get(EndPoints.GET_PRIMARY_ACCOUNT.getValue())
                .then().statusCode(200)
                .extract().response();

        ExtentTestManager.getTest().info(response.asPrettyString());
        AccountResponse accountResponse = response.body().jsonPath().getObject("", AccountResponse.class);

        Assert.assertEquals(accountResponse.getId(), accountId);
        Assert.assertEquals(accountResponse.getEmail(), expectedEmail);
        Assert.assertEquals(accountResponse.getFirstName(), expectedFirstName);

    }
}
