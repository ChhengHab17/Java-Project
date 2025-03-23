package report;

import java.time.LocalDate;

public class CustomizeReport extends Report implements Pdfvalidate{
    public CustomizeReport(String fileName, LocalDate startDate, LocalDate endDate) {
        super(fileName, startDate, endDate); // Pass directly to parent constructor
    }
    public String getTitle(){
        return "Report";
    }
    public String getDateRange() {
        return startDate + " to " + endDate;  // Now shows the correct date range
    }
    public void generatePDF() {
        super.generatePDF();
    }
    
    public static void main(String[] args) {
        CustomizeReport customReport = new CustomizeReport( "Custom_Report", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 9)); 
        customReport.generatePDF();
    }
}



