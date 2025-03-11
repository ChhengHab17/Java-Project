package Expense;

import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expenseList;

    public ExpenseManager() {
        expenseList = new ArrayList<>();
    }

    public void addExpense(String category, double amount, String date, String currency) {
        Expense expense = new Expense(category, amount, date, currency);
        expenseList.add(expense);
        System.out.println("Expense added successfully: " + amount + currency);
    }

    public void viewExpenses() {
        if (expenseList.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense expense : expenseList) {
                System.out.println(expense.getDetails());
            }
        }
    }

    public void calculateTotal() {
        double total = 0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        System.out.println("Total Expenses: $" + total);
    }

    
}
