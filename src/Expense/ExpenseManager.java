package Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO expenses (category, description, amount, date, currency) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setString(2, description);
            stmt.setDouble(3, amount);
            stmt.setDate(4, Date.valueOf(date));
            stmt.setString(5, currency);
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
                     "FROM expenses";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        String sql = "UPDATE expenses SET amount = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newAmount);
            stmt.setDate(2, Date.valueOf(newDate));
            stmt.setInt(3, id);
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
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
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