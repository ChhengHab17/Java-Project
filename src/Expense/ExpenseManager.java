package Expense;

import java.util.ArrayList;
import java.util.Locale.Category;

public class ExpenseManager {
    private ArrayList<Expense> expenseList;
    private static final double EXCHANGE_RATE = 4100.0;

    public ExpenseManager() {
        expenseList = new ArrayList<>();
    }

    public void addExpense(Category category, double amount, String date, String currency) {
    Expense expense = new Expense(category, amount, date, currency);
    expenseList.add(expense);
    System.out.println("Expense added successfully: " + expense.getDetails());
}


    public void viewExpenses() {
        if (expenseList.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("\nExpense List:");
            for (Expense expense : expenseList) {
                double amountInUSD = expense.getAmountInUSD();
                double amountInKHR = amountInUSD * EXCHANGE_RATE;

                System.out.println(expense.getDetails() + 
                                " | USD: $" + amountInUSD + 
                                " | KHR: " + amountInKHR + "៛");
            }
        }
    }
        
    public void calculateTotal() {
        double totalUSD = 0;
        for (Expense expense : expenseList) {
            totalUSD += expense.getAmountInUSD();
        }
        double totalKHR = totalUSD * EXCHANGE_RATE;

        System.out.println("\nTotal Expenses:");
        System.out.println("USD: $" + totalUSD);
        System.out.println("KHR: " + totalKHR + "៛");
    }

    public void editExpense(Category category, double newAmount, String newDate) {
        boolean found = false;
        for (Expense expense : expenseList) {
            if (expense.getCategory().equals(category)) {
                expense.setAmount(newAmount);
                expense.setDate(newDate);
                System.out.println("Expense updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No expense found for category: " + category.getName());
        }
    }
    
    public void deleteExpense(Category category) {
        boolean deleted = false;
        for (int i = 0; i < expenseList.size(); i++) {
            if (expenseList.get(i).getCategory().equals(category)) {
                expenseList.remove(i);
                System.out.println("Expense in category \"" + category.getName() + "\" deleted.");
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            System.out.println("No expense found for category: " + category.getName());
        }
    }    
}
