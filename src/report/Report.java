package report;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.awt.Color;
import Expense.Expense;

public class Report {
    protected int id;
    protected String fileName;
    protected List<Expense> expenses;
    protected double totalExpenses;
    LocalDate startDate;
    LocalDate endDate;
    public Report(){
        
    }
    public Report(int id, String fileName){
        this.id = id;
        this.fileName = fileName;
        this.startDate = LocalDate.of(2021, 1, 1);
        this.endDate = LocalDate.now();
        this.expenses = new ArrayList<>();
        this.totalExpenses = 0;
    }
    public Report(int id, String fileName, LocalDate startDate, LocalDate endDate){
        this.id = id;
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
    
    @Override
    public String toString() {
        expenses = ReportScript.getExpensesByDateRange(startDate, endDate);
        totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
        StringBuilder expenseDetails = new StringBuilder();
    
        if (expenses.isEmpty()) {
            expenseDetails.append("No expenses recorded.");
        } else {
            for (Expense exp : expenses) {
                expenseDetails.append(exp.toString()).append("\n"); // Add a new line after each expense
            }
        }
        return "Report: " + fileName + "\n" +
               "ID: " + id + "\n" +
               "Date Range: " + getDateRange() + "\n" +
               "Expenses: " + "\n" + expenseDetails.toString() +
               "Total: $" + totalExpenses;
    }
    
    public String getTitle(){
        return "MainReport";
    }
    public void generatePDF(){
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

            //left side
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, startY);
            contentStream.showText("Report ID: " + id);
            contentStream.endText();
            startY -= lineHeight;
            //Right side
            String allDate = getDateRange();
            float dateRangeWidth = font.getStringWidth(allDate) / 1000 * 14;
            float dateRangeX = pageWidth - margin - dateRangeWidth;
            contentStream.beginText();
            contentStream.newLineAtOffset(dateRangeX, startY);
            contentStream.showText(allDate);
            contentStream.endText();
            //Expenses
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, startY);
            contentStream.showText("Expenses: ");
            contentStream.endText();
            startY -= lineHeight;
            expenses = ReportScript.getExpensesByDateRange(startDate, endDate);
            for (Expense expense : expenses) {
                totalExpenses += expense.getAmount();
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 20, startY);
                contentStream.showText(expense.toString());
                contentStream.endText();
                startY -= lineHeight;
            }
            //total Expense

            String totalText = "Total: $" + totalExpenses;
            float totalWidth = font.getStringWidth(totalText) / 1000 * 14;
            float totalX = pageWidth - margin - totalWidth;
            startY -= (lineHeight + 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(totalX, startY);
            contentStream.showText(totalText);
            contentStream.endText();
            contentStream.close();
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            document.save(desktopPath + fileName + ".pdf");
            document.close();
            System.out.println("PDF generated successfully.");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Report report = new Report(1, "MainReport");
        report.generatePDF();
    }
}
