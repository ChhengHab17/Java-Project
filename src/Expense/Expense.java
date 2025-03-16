package Expense;

public class Expense {
    private String category;
    private double amount;
    private String date;
    private String currency;
    private static final double EXCHANGE_RATE = 4100.0;

    public Expense(String category, double amount, String date, String currency) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }

    public double getAmountInUSD() {
        if (currency.equalsIgnoreCase("KHR")) {
            return amount / EXCHANGE_RATE;
        }
        return amount;
    }

    public double getAmountInKHR() {
        if (currency.equalsIgnoreCase("USD")) {
            return amount * EXCHANGE_RATE;
        }
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return "Category: " + category + ", Amount: " + amount + " " + currency + ", Date: " + date;
    }
}
