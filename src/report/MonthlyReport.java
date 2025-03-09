package report;

import java.time.LocalDate;

public class MonthlyReport extends Report implements Pdfvalidate{
    public MonthlyReport(int id, String fileName, int year, int month){
        // Calculate start and end dates for the month
        super(id, fileName, LocalDate.of(year, month, 1), LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth()));
    }
    @Override
    public String getTitle(){
        return "Monthly Report";
    }
    public String getDateRange() {
        return startDate + " to " + endDate;  // Now shows the correct date range
    }

    @Override
    public void generatePDF() {
        super.generatePDF();
    }
    public static void main(String[] args) {
        MonthlyReport monthlyReport = new MonthlyReport(1, "Monthly_Report", 2025, 2);
        monthlyReport.generatePDF();
    }
}
