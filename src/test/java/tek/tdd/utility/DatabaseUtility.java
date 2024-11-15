package tek.tdd.utility;

import com.aventstack.extentreports.service.ExtentTestManager;
import tek.tdd.base.BaseSetup;

import java.sql.*;

public class DatabaseUtility extends BaseSetup {
    private static Connection connection;

    public ResultSet executeQuery(String query) {
        String url = getProperty("db.url");
        String username = getProperty("db.username");
        String password = getProperty("db.password");

        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (SQLException exception) {
            ExtentTestManager.getTest().fail(exception.getMessage());
            throw new RuntimeException("Failed to execute the query" + exception.getMessage());
        }
    }
}
