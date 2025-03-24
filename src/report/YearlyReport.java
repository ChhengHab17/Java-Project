package report;

import java.time.LocalDate;

public class YearlyReport extends Report{
    public YearlyReport(String name, int year) {
        super(name, LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
    }
    @Override
    public String getTitle() {
        return "Yearly Report";
    }
    public String getDateRange() {
        return startDate + " to " + endDate;  // Now shows the correct date range
    }
    @Override
    public void generatePDF(){
        try {
            super.generatePDF();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        YearlyReport yearlyReport = new YearlyReport("Yearly_Report", 2025);
        yearlyReport.generatePDF();
    }
}
