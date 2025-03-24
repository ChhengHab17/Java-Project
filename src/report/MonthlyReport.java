package report;

import java.time.LocalDate;

public class MonthlyReport extends Report{
    public MonthlyReport(String fileName, int year, int month){
        // Calculate start and end dates for the month
        super(fileName, LocalDate.of(year, month, 1), LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()));
    }
    @Override
    public String getTitle(){
        return "Monthly Report";
    }
    public String getDateRange() {
        return startDate + " to " + endDate;  // Now shows the correct date range
    }

    @Override
    public void generatePDF() throws Exception {
        super.generatePDF();
    }
    public static void main(String[] args) {
        MonthlyReport monthlyReport = new MonthlyReport("Monthly_Report", 2025, 2);
        
    }
}
