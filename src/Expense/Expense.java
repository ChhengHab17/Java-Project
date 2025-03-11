package Expense;

public class Expense {
    private String category;
    private String detail;
    private double amount;
    private String date;
    private int user_id;
    private int expense_id;
    private String currency;
    private static final double EXCHANGE_RATE = 4100.0; 

    public Expense(String category, double amount, String date, String currency) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.currency = currency; 
    }

    public double getAmountInUSD() {
        return amount;
    }

    public double getAmountInKHR() {
        return amount * EXCHANGE_RATE;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return "Category: " + category + ", Amount: $" + amount + 
               " (KHR " + getAmountInKHR() + "), Date: " + date;
    }
}

