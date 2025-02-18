package DatabaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root"; // Default user in XAMPP
        String password = ""; // Default password is empty in XAMPP

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Insert data
            String insertSql = "INSERT INTO users (name, email) VALUES ('poly Doe', 'poly@example.com')";
            try (Statement statement = connection.createStatement()) {
                int rowsInserted = statement.executeUpdate(insertSql);
                if (rowsInserted > 0) {
                    System.out.println("Data inserted successfully!");
                }
            }

            // Select data
            String selectSql = "SELECT * FROM users";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Email: " + resultSet.getString("email"));
                    System.out.println("---------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}