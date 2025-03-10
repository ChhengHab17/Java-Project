package UserManagement;

import DatabaseConnector.DatabaseConnection;
import java.util.*;
import Systemsetting.Usersetting;


public class User extends Person {
    private String username;
    private String password;
    private static ArrayList<String> loginHistory = new ArrayList<>();

    public User(String firstName, String lastName, String dob, String gender, String phoneNumber, String email, String username, String password) {
        super(firstName, lastName, dob, gender, phoneNumber, email); // Call superclass constructor
        this.username = username;
        this.password = password;
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        this.firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        this.lastName = scanner.nextLine();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        this.dob = scanner.nextLine();
        System.out.print("Enter gender: ");
        this.gender = scanner.nextLine();
        
        while (true) {
            System.out.print("Enter phone number: ");
            this.phoneNumber = scanner.nextLine();
            if (!DatabaseConnection.phoneNumberExists(this.phoneNumber)) {
                break;
            } else {
                System.out.println("Phone number already exists! Try a new one.");
            }
        }

        while (true) {
            System.out.print("Enter email: ");
            this.email = scanner.nextLine();
            if (this.email.contains("@") && !DatabaseConnection.emailExists(this.email)) {
                break;
            } else {
                System.out.println("Invalid or existing email. Please try again.");
            }
        }

        while (true) {
            System.out.print("Enter username: ");
            this.username = scanner.nextLine();
            if (!DatabaseConnection.usernameExists(this.username)){
                break;
            }else {
                System.out.println("Username already exists! Try a new one. ");
            }
        }
        System.out.print("Enter password: ");
        this.password = scanner.nextLine();

        int userId = DatabaseConnection.insertUser(this.firstName, this.lastName, this.dob, this.gender, this.phoneNumber, this.email, this.username, this.password);
        if (userId > 0) {
            System.out.println("User registered successfully! Your user ID is: " + userId);
        } else {
            System.out.println("Failed to register user.");
        }
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (DatabaseConnection.userExists(username, password)) {
            System.out.println("Login successful!");
            loginHistory.add(username + " logged in at " + new Date());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            System.out.print("Forgot password? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                // forgetPassword();
            }
            return false;
        }
    }

    public void displayStats() {
        System.out.println("\nLogin History:");
        for (String log : loginHistory) {
            System.out.println(log);
        }
    }


    public static void main(String[] args) {
        User user = new User("", "", "", "", "", "", "", "");
        user.register();
        user.login();
        user.displayStats();
    }
}
