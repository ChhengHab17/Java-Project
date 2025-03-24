package report;

import java.time.LocalDate;

public class CustomizeReport extends Report{
    public CustomizeReport(String fileName, LocalDate startDate, LocalDate endDate) {
        super(fileName, startDate, endDate); // Pass directly to parent constructor
    }
    public String getTitle(){
        return "Report";
    }
    public String getDateRange() {
        return startDate + " to " + endDate;  // Now shows the correct date range
    }
    public void generatePDF() throws Exception {
        super.generatePDF();
    }
    
    public static void main(String[] args) {
        
    }
}



