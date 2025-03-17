package Expense;

import java.time.LocalDate;

public class Expense {
    private String category;
    private String detail;
    private double amount;
    private LocalDate date;
    private int user_id;
    private int expense_id;
    private String currency;
    private static final double EXCHANGE_RATE = 4100.0; 

    public Expense(String category, double amount, LocalDate date, String currency) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.currency = currency; 
    }
    public String getCurrency() {
        return currency;
    }
    public LocalDate getDate() {
        return date;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return String.format("%s: %.2f %s (%s)",
            category, amount, currency, date);
    }
    public String getDetails() {
        return "Category: " + category + ", Amount: $" + amount + 
               " (KHR " + getAmountInKHR() + "), Date: " + date;
    }
}

