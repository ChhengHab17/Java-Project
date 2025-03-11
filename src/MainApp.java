import java.util.Scanner;

import Expense.ExpenseManager;

public class MainApp {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Calculate Total Expenses");
            System.out.println("4. Delete Expense");
            System.out.println("5. Edit Expense");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    System.out.print("Enter currency (USD/KHR): ");
                    String currency = scanner.nextLine().toUpperCase();

                    expenseManager.addExpense(category, amount, date, currency);
                    break;

                case 2:
                    expenseManager.viewExpenses();
                    break;

                case 3:
                    expenseManager.calculateTotal();
                    break;

                case 4:
                    System.out.print("Enter category to delete: ");
                    String deleteCategory = scanner.nextLine();
                    expenseManager.deleteExpense(deleteCategory);
                    break;

                case 5:
                    System.out.print("Enter category to edit: ");
                    String editCategory = scanner.nextLine();

                    System.out.print("Enter new amount: ");
                    double newAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter new date (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();

                    expenseManager.editExpense(editCategory, newAmount, newDate);
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
