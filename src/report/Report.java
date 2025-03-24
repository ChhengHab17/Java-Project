package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.crypto.Data;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import DatabaseConnector.DatabaseConnection;

import java.awt.Color;
import Expense.Expense;
import Main.Session;

public class Report {
    protected int id;
    protected String fileName;
    protected List<Expense> expenses;
    protected double totalExpenses;
    LocalDate startDate;
    LocalDate endDate;
    public Report(){
        
    }
    public Report(String fileName){
        this.fileName = fileName;
        this.startDate = LocalDate.of(2000, 1, 1);
        this.endDate = LocalDate.now();
        this.expenses = new ArrayList<>();
        this.totalExpenses = 0;
    }
    public Report(String fileName, LocalDate startDate, LocalDate endDate){
        this.fileName = fileName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = new ArrayList<>();
        this.totalExpenses = 0;
    }
    public void setName(String fileName){
        this.fileName = fileName;
    }
    public String getName(){
        return fileName;
    }
    public String getDateRange(){
        return startDate + " - " + endDate;
    }
    public double calcTotalExpenses(){
        expenses = ReportScript.getExpensesByDateRange(startDate, endDate);
        
        // Initialize variables for total expenses in USD and KHR
        double totalUSD = 0.0;
        double totalKHR = 0.0;
        double exchangeRate = 4100.0;
    
        StringBuilder expenseDetails = new StringBuilder();
        
        if (expenses.isEmpty()) {
            expenseDetails.append("No expenses recorded.");
        } else {
            // Loop through each expense to calculate totals and build the expense details
            for (Expense exp : expenses) {
                double amount = exp.getAmount();
                String currency = exp.getCurrency();  // Assume Expense class has getCurrency method
                
                if ("USD".equals(currency)) {
                    totalUSD += amount;  // Add USD directly to the totalUSD
                } else if ("KHR".equals(currency)) {
                    totalKHR += amount;  // Add KHR directly to the totalKHR
                }
                
                // Append the expense details to the expenseDetails string
                expenseDetails.append(exp.toString()).append("\n");
            }
        }
    
        // Convert total KHR to USD and add it to totalUSD
        return totalUSD += totalKHR / exchangeRate;
    }
    
    @Override
    public String toString() {
       double totalUSD = calcTotalExpenses();
       double newTotalKHR = totalUSD * 4100;
       expenses = ReportScript.getExpensesByDateRange(startDate, endDate);
       StringBuilder expenseDetails = new StringBuilder();
       for (Expense exp : expenses) {
            expenseDetails.append(exp.toString()).append("\n");
        }
        // Return the full report with total in USD (including converted KHR)
        return "Report: " + fileName + "\n" +
               "ID: " + id + "\n" +
               "Date Range: " + getDateRange() + "\n" +
               "Expenses: \n" + expenseDetails.toString() +
               "Total in USD: $" + String.format("%.2f", totalUSD) + "\n" + // Total in USD
               "Total in KHR: " + String.format("%.2f", newTotalKHR) + "៛";
    }
    
    public String getTitle(){
        return "MainReport";
    }
    public void generatePDF() throws Exception{
        try{
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\arial.ttf"));
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float pageWidth = page.getMediaBox().getWidth();
            float margin = 50;
            float startY = 700;  // Starting Y position
            float lineHeight = 20; // Space between lines
            //Title
            String title = getTitle();
            contentStream.setFont(font, 18);
            contentStream.setNonStrokingColor(Color.blue);
            float titleWidth = font.getStringWidth(title) / 1000 * 18;
            float titleX = (pageWidth - titleWidth) / 2;
            // Write the title
            contentStream.beginText();
            contentStream.newLineAtOffset(titleX, startY);
            contentStream.showText(title);
            contentStream.endText();
            startY -= lineHeight;

            //Draw a line
            contentStream.setStrokingColor(Color.black);
            contentStream.moveTo(margin, startY);
            contentStream.lineTo(pageWidth -margin, startY);
            contentStream.stroke();
            startY -= (lineHeight + 10);

            //content page
            contentStream.setFont(font, 14);
            contentStream.setNonStrokingColor(Color.black);

            //Right side
            String allDate = getDateRange();
            float dateRangeWidth = font.getStringWidth(allDate) / 1000 * 14;
            float dateRangeX = pageWidth - margin - dateRangeWidth;
            contentStream.beginText();
            contentStream.newLineAtOffset(dateRangeX, startY);
            contentStream.showText(allDate);
            contentStream.endText();

            startY -= (lineHeight + 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, startY);
            contentStream.showText("Report Name: " + fileName);
            contentStream.endText();
            //Expenses
            startY -= (lineHeight + 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, startY);
            contentStream.showText("Expenses: ");
            contentStream.endText();
            startY -= lineHeight;
            expenses = ReportScript.getExpensesByDateRange(startDate, endDate);
            for (Expense expense : expenses) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 20, startY);
                contentStream.showText(expense.toString());
                contentStream.endText();
                startY -= lineHeight;
            }
            //total Expense
            totalExpenses = calcTotalExpenses();
            double totalExpenseInKHR = totalExpenses * 4100;
            String totalText = "Total: $" + String.format("%.2f", totalExpenses);
            String totalTextKHR = "Total: " + String.format("%.2f", totalExpenseInKHR) + " Riels";

            float totalWidth = font.getStringWidth(totalText) / 1000 * 14;
            float totalX = pageWidth - margin - totalWidth -10;
            startY -= (lineHeight + 10);

            contentStream.beginText();
            contentStream.newLineAtOffset(totalX, startY);
            contentStream.showText(totalText);
            contentStream.endText();
            startY -= lineHeight; // Move to the next line

            // Now show the KHR total on the next line
            contentStream.beginText();
            contentStream.newLineAtOffset(totalX, startY);
            contentStream.showText(totalTextKHR); // Show KHR total
            contentStream.endText();

            contentStream.close();
           
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save to PDF");
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));

            String finalFileName = (fileName != null && !fileName.isEmpty()) ? fileName : "DefaultReportName";
            fileChooser.setSelectedFile(new File(finalFileName + ".pdf"));
            
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                document.close();
                throw new Exception("User canceled file selection.");
            }
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
    
                // Ensure .pdf extension
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }
    
                document.save(filePath);
                System.out.println("PDF saved to: " + filePath);
            } else {
                System.out.println("Save operation canceled.");
            }

            document.close();
            saveToDatabase();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveToDatabase() {
    int userId = Session.getUserId();  // Get the logged-in user's ID
    String query = "INSERT INTO reports (user_id, file_name, start_date, end_date, total_expenses, pdf_file) VALUES (?, ?, ?, ?, ?, ?)";
    String desktopPath = System.getProperty("user.home") + "/Desktop/";
    String newFilename = desktopPath + fileName + ".pdf";
    File pdfFile = new File(newFilename);
    if (!pdfFile.exists()) {
        System.out.println("Error: PDF file not found! Generate the report first.");
        return;
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
         FileInputStream pdfInput = new FileInputStream(pdfFile)) {

        pstmt.setInt(1, userId);  // Set user_id in the query
        pstmt.setString(2, fileName);
        pstmt.setDate(3, Date.valueOf(startDate));
        pstmt.setDate(4, Date.valueOf(endDate));
        pstmt.setDouble(5, totalExpenses);  // Assuming you have this field
        pstmt.setBinaryStream(6, pdfInput, (int) pdfFile.length());

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
    public static void main(String[] args) throws Exception {
        Report report = new Report( "MainReport");
        report.generatePDF();
    }
}
