
import budget.BudgetManager;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        BudgetManager budgetManager = new BudgetManager();
        int choice;

        do 
        {
            System.out.println("\n==== Budget Management System ====");
            System.out.println("1. Set Budget");
            System.out.println("2. Edit Budget");
            System.out.println("3. Delete Budget");
            System.out.println("4. View Budget");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                System.out.println("Enter weekly budget: ");
                double weekly = scanner.nextDouble();
                System.out.println("Enter monthly budget");
                double monthly = scanner.nextDouble();
                budgetManager.createBudget(weekly, monthly);
                break;
                case 2:
                System.out.println("Enter new weekly budget: ");
                double newWeekly = scanner.nextDouble();
                System.out.println("Enter a new monthly budget: ");
                double newMonthly = scanner.nextDouble();
                budgetManager.modifyBudget(newWeekly, newMonthly);
                break;
                case 3:
                budgetManager.removeBudget();
                break;
                case 4:
                budgetManager.showBudget();
                break;
                case 5:
                System.out.println("Exiting the program");
                break;
                default:
                System.out.println("Invalid choice!");
                }
            } while (choice != 5);
        scanner.close();
    }
}
