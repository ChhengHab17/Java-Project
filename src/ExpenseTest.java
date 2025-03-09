import java.util.Scanner;
import Expense.ExpenseManager;

public class ExpenseTest {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Calculate Total");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter currency (USD): ");
                    String currency = scanner.nextLine();

                    expenseManager.addExpense(category, amount, date, "USD");
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
        }
    }
}
