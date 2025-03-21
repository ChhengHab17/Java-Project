package Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import report.Category;

public class ExpenseManager {
    private List<Expense> expenseList;
    private static final double EXCHANGE_RATE = 4100.0;

    public ExpenseManager() {
        expenseList = new ArrayList<>();
    }

    public void addExpense(Category category, double amount, LocalDate date, String currency) {
        Expense expense = new Expense(category, amount, date, currency);
        expenseList.add(expense);
        System.out.println("Expense added successfully: " + expense.getDetails());
    }

    public void viewExpenses() {
        if (expenseList.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\nExpense List:");
        for (Expense expense : expenseList) {
            double amountInUSD = expense.getAmountInUSD();
            double amountInKHR = amountInUSD * EXCHANGE_RATE;

            System.out.println(expense.getDetails() + 
                               " | USD: $" + amountInUSD + 
                               " | KHR: " + amountInKHR + "៛");
        }
    }

    public double calculateTotalUSD() {
        double total = expenseList.stream().mapToDouble(Expense::getAmountInUSD).sum();
        return Math.round(total * 100.0) / 100.0;
    }

    public double calculateTotalKHR() {
        return calculateTotalUSD() * EXCHANGE_RATE;
    }

    public boolean editExpense(Category category, double newAmount, LocalDate newDate) {
        for (Expense expense : expenseList) {
            if (expense.getCategory().equals(category)) {
                expense.setAmount(newAmount);
                expense.setDate(newDate);
                return true;
            }
        }
        return false;
    }

    public boolean deleteExpense(Category category) {
        return expenseList.removeIf(expense -> expense.getCategory().equals(category));
    }

    public List<Expense> getExpenses() {
        return expenseList;
    }
}
