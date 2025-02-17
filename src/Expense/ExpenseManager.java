package Expense;

import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expenseList;

    public ExpenseManager() {
        expenseList = new ArrayList<>();
    }

    public void addExpense(String category, String detail, double amount, String date) {
        Expense expense = new Expense(category, detail, amount, date);
        expenseList.add(expense);
        System.out.println("Expense added successfully.");
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

    public void deleteExpense(int id) {
       
    }
}
