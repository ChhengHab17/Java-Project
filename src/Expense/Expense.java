package Expense;

public class Expense {
    private String category;
    private String detail;
    private double amount;
    private String date;
    private int id;

    public Expense(String category, String detail, double amount, String date) {
        this.category = category;
        this.detail = detail;
        this.amount = amount;
        this.date = date;
    }

    public String getDetails() {
        return "Category: " + category + ", Detail: " + detail + ", Amount: $" + amount + ", Date: " + date;
    }
}

