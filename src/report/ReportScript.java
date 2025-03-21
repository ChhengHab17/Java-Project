package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Expense.Expense;
import Expense.ExpenseManager;

public class ReportScript extends Report {

    // Constructor that correctly calls the parent class constructor
    public ReportScript(int id, String fileName, LocalDate startDate, LocalDate endDate) {
        super(id, fileName, startDate, endDate);
    }

    // Database connection method
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
    }

    // Save PDF report to database
    public void saveToDatabase() {
        String query = "INSERT INTO reports (file_name, start_date, end_date, total_expenses, pdf_file) VALUES (?, ?, ?, ?, ?)";

        File pdfFile = new File(fileName + ".pdf");
        if (!pdfFile.exists()) {
            System.out.println("Error: PDF file not found! Generate the report first.");
            return;
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             FileInputStream pdfInput = new FileInputStream(pdfFile)) {

            pstmt.setString(1, fileName);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(endDate));
            pstmt.setDouble(4, totalExpenses);
            pstmt.setBinaryStream(5, pdfInput, (int) pdfFile.length());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        this.id = keys.getInt(1);
                        System.out.println("Report saved with ID: " + id);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void retrievePDF(int reportId, String outputFileName) {
        String query = "SELECT pdf_file FROM reports WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, reportId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                File outputFile = new File(outputFileName);
                FileOutputStream fos = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                InputStream pdfStream = rs.getBinaryStream("pdf_file");
                int bytesRead;

                while ((bytesRead = pdfStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                fos.close();
                System.out.println("PDF retrieved and saved as: " + outputFileName);
            } else {
                System.out.println("No PDF found for report ID: " + reportId);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT category, amount, date, currency FROM expenses WHERE date BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setObject(1, startDate);
                stmt.setObject(2, endDate);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("category");
                    String description = "";
                    double amount = rs.getDouble("amount");
                    String datestr = rs.getString("date");
                    String currency = rs.getString("currency");
                    Category categoryObj = new Category(category, description);
                    LocalDate date = LocalDate.parse(datestr);
                    Expense expense = new Expense(categoryObj, amount, date, currency);
                    expenses.add(expense);
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return expenses;
    }

    // Main method for testing
    public static void main(String[] args) {
        
    }
}
