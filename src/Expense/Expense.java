package Expense;

public class Expense {
    private String category;
    private String detail;
    private double amount;
    private String date;
    private int user_id;
    private int expense_id;
    private String currency;

    public Expense(String category, double amount, String date, String currency) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.currency = currency; 
    }

    public double getAmount() {
        return amount;
    }

    public String getDetails() {
        return "Category: " + category + ", Amount: " + amount + " " + currency + ", Date: " + date;
    }
}

