package Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Main.Session;

public class ExpenseManager {
    private static final double EXCHANGE_RATE = 4100.0;
    private Connection connection;

    public ExpenseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Expense
    public void addExpense(String category, String description, double amount, LocalDate date, String currency) {
        String sql = "INSERT INTO expenses (user_id, category, description, amount, date, currency) VALUES (?, ?, ?, ?, ?, ?)";
        int userId = Session.getUserId();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, category);
            stmt.setString(3, description);
            stmt.setDouble(4, amount);
            stmt.setDate(5, Date.valueOf(date));
            stmt.setString(6, currency);
            stmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View Expenses
    public void viewExpenses() {
        String sql = "SELECT * FROM expenses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                String currency = rs.getString("currency");
                System.out.println("ID: " + id + ", Category: " + category + ", Description: " + description +
                        ", Amount: " + amount + " " + currency + ", Date: " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Calculate Total Expense
    public void calculateTotal() {
        String sql = "SELECT " +
                     "SUM(CASE WHEN currency = 'USD' THEN amount ELSE amount / 4100 END) AS total_in_usd, " +
                     "SUM(CASE WHEN currency = 'KHR' THEN amount ELSE amount * 4100 END) AS total_in_khr " +
                     "FROM expenses WHERE user_id = ?";
        int userId = Session.getUserId();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalUSD = rs.getDouble("total_in_usd");
                double totalKHR = rs.getDouble("total_in_khr");
                System.out.println("Total in USD: $" + totalUSD);
                System.out.println("Total in KHR: " + totalKHR + "៛");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit Expense
    public void editExpense(int id, double newAmount, LocalDate newDate) {
        String sql = "UPDATE expenses SET amount = ?, date = ? WHERE id = ? AND user_id = ?";
        int userId = Session.getUserId();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setDate(2, Date.valueOf(newDate));
            stmt.setInt(3, id);
            stmt.setInt(4, userId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Expense updated successfully.");
            } else {
                System.out.println("No expense found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteExpense(int id) {
        String sql = "DELETE FROM expenses WHERE id = ? AND user_id = ?";
        int userId = Session.getUserId();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Expense deleted successfully.");
            } else {
                System.out.println("No expense found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}