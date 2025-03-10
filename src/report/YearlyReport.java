package report;

import java.time.LocalDate;

public class YearlyReport extends Report implements Pdfvalidate{
    public YearlyReport(int id, String name, int year) {
        super(id, name, LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
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
        super.generatePDF();
    }
    public static void main(String[] args) {
        YearlyReport yearlyReport = new YearlyReport(1, "Yearly_Report", 2025);
        yearlyReport.generatePDF();
    }
}
