package Expense;

import report.Category;

public class Expense {
    private Category category;
    private double amount;
    private String date;
    private String currency;

    public Expense(Category category, double amount, String date, String currency) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmountInUSD() {
        if ("USD".equals(currency)) {
            return amount;
        } else if ("KHR".equals(currency)) {
            return amount / 4100.0;
        }
        return 0.0;
    }

    public String getDetails() {
        return "Category: " + category.getName() + " | Description: " + category.getDescription() +
               " | Amount: " + amount + " | Date: " + date + " | Currency: " + currency;
    }
}
