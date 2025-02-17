package report;

import java.time.LocalDate;

public class YearlyReport extends Report implements Pdfvalidate{
    public YearlyReport(int id, String name, int year) {
        super(id, name, LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
    }

    @Override
    public void generatePDF(){
        super.generatePDF();
    }
}
