import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Expense.*;
import report.*;
import systemsetting.*;
import UserManagement.User;

import java.time.LocalDate;

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
    public void reportMenu() {
        int id;
        String name;
        LocalDate startDate = null;
        LocalDate endDate = null;
        int month;
        int year;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Generate Report");
                System.out.println("2. Generate Monthly Reports");
                System.out.println("3. Generate Yearly Reports");
                System.out.println("4. Generate Customize Reports");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the leftover newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter report id: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter report name: ");
                        name = scanner.nextLine();
                        Report report = new Report(id, name);
                        // Generate the report
                        report.generatePDF();
                        break;

                    case 2:
                        System.out.print("Enter report id: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter report name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter Year: ");
                        year = scanner.nextInt();
                        System.out.print("Enter Month: ");
                        month = scanner.nextInt();
                        MonthlyReport monthlyReport = new MonthlyReport(id, name, year, month);
                        monthlyReport.generatePDF();
                        break;

                    case 3:
                        System.out.print("Enter report id: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter report name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter Year: ");
                        year = scanner.nextInt();
                        YearlyReport yearlyReport = new YearlyReport(id, name, year);
                        yearlyReport.generatePDF();
                        break;

                    case 4:
                        System.out.print("Enter report id: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter report name: ");
                        name = scanner.nextLine();

                        // Handling date input
                        boolean validDates = false;
                        while (!validDates) {
                            try {
                                System.out.print("Enter Start Date (YYYY-MM-DD): ");
                                startDate = LocalDate.parse(scanner.nextLine());
                                System.out.print("Enter End Date (YYYY-MM-DD): ");
                                endDate = LocalDate.parse(scanner.nextLine());
                                validDates = true; // If no exception, dates are valid
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                            }
                        }

                        // Create and generate the custom report
                        CustomizeReport customizeReport = new CustomizeReport(id, name, startDate, endDate);
                        customizeReport.generatePDF();
                        break;

                    case 5:
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
    public void userMenu(){
        Scanner scanner = new Scanner(System.in);
        User user = new User(null, null, null, null, null, null, null, null);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Display Login History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        user.login();
                        break;
                    case 3:
                        user.displayStats();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    public void settingMenu(){
        
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.userMenu();
        // app.expenseMenu();
    }
}
