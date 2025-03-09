import java.util.InputMismatchException;
import java.util.Scanner;

import Expense.*;
import report.*;

public class App {
    public void expenseMenu() {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Add Expense");
                System.out.println("2. View Expenses");
                System.out.println("3. Calculate Total");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the leftover newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter category: ");
                        String category = scanner.nextLine();

                        // Handle invalid input for amount (non-numeric values)
                        double amount = 0;
                        try {
                            System.out.print("Enter amount: ");
                            amount = scanner.nextDouble();
                            scanner.nextLine(); // consume the newline
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid amount. Please enter a valid numeric value.");
                            scanner.nextLine(); // Clear the invalid input
                            break; // Skip the rest of the case and go back to the menu
                        }

                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();

                        // Handle currency input (although we default to USD, it's good practice to validate)
                        System.out.print("Enter currency (USD): ");
                        String currency = scanner.nextLine();

                        // Add the expense
                        expenseManager.addExpense(category, amount, date, currency);
                        break;

                    case 2:
                        expenseManager.viewExpenses();
                        break;

                    case 3:
                        expenseManager.calculateTotal();
                        break;

                    case 4:
                        System.out.println("Exiting!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } catch (InputMismatchException e) {
                // Handles invalid input like non-integer values when choosing menu options
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                // Catch any unexpected errors and print a generic message
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace(); // Print stack trace for debugging purposes (optional)
            }
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.expenseMenu();
    }
}
