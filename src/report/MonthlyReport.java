package report;

import java.time.LocalDate;

public class MonthlyReport extends Report implements Pdfvalidate{
    public MonthlyReport(int id, String name, int year, int month){
        super(id, name, LocalDate.of(year, month, 1), LocalDate.of(year, month,LocalDate.of(year, month, 1).lengthOfMonth()));
    }
    @Override
    public void generatePDF() {
        super.generatePDF();
    }
}
