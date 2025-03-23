package DatabaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root"; // Default user in XAMPP
    private static final String PASSWORD = ""; // Default password is empty in XAMPP

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int insertUser(String firstName, String lastName, String dob, String gender, String phoneNumber, String email, String username, String password) {
        String insertSql = "INSERT INTO newusers (first_name, last_name, dob, gender, phone_number, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dob)); 
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, username);
            preparedStatement.setString(8, password);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return generated id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if registration failed
    }

    public static boolean userExists(String username, String password) {
        String selectSql = "SELECT * FROM newusers WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPassword(String username) {
        String selectSql = "SELECT password FROM newusers WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error in getPassword: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean phoneNumberExists(String phoneNumber) {
        String selectSql = "SELECT * FROM newusers WHERE phone_number = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean emailExists(String email){
        String selectsql = "SELECT * FROM newusers WHERE email = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectsql)){
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
            } catch (SQLException e){
                e.printStackTrace();
                return false;
            }
    }
    
    public static boolean usernameExists(String username) {
        String selectSql = "SELECT * FROM newusers WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        String updateSql = "UPDATE newusers SET password = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
    
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean updateUsername(String oldUsername, String newUsername) {
        String updateSql = "UPDATE newusers SET username = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Database error in updateUsername: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePhonenumber(String username, String newPhonenumber ){
        String updateSql = "UPDATE newusers SET phone_number = ? WHERE username = ?";
        try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(updateSql)){
            preparedStatement.setString(1, newPhonenumber);
            preparedStatement.setString(2, username);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e){
            System.out.println("Database error in updatePhonenumber: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean emailLogin(String email, String password) {
    String sql = "SELECT * FROM newusers WHERE email = ? AND password = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    } catch (SQLException e) {
        System.out.println("Database error in emailLogin: " + e.getMessage());
        return false;
    }
}

public static String getUsernameByEmail(String email) {
    String sql = "SELECT username FROM newusers WHERE email = ?";
    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("username");
        }
    } catch (SQLException e) {
        System.out.println("Database error in getUsernameByEmail: " + e.getMessage());
    }
    return null;
}
}