package UserManagement;

import DatabaseConnector.DatabaseConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class User extends Person {
    private String username;
    private String password;
    private static ArrayList<String> loginHistory = new ArrayList<>();

    public User(String firstName, String lastName, String dob, String gender, String phoneNumber, String email, String username, String password) {
        super(firstName, lastName, dob, gender, phoneNumber, email);
        this.username = username;
        this.password = password;
    }

    // Override displayInfo to add user-specific information
    @Override
    public void displayInfo() {
        System.out.println("Username: " + username);
        super.displayInfo(); // Call parent class method first
    }

    // Override validateEmail to include database check
    @Override
    public boolean validateEmail() {
        return super.validateEmail() && !DatabaseConnection.emailExists(email);
    }

    // Override validatePhone to include database check
    @Override
    public boolean validatePhone() {
        return super.validatePhone() && !DatabaseConnection.phoneNumberExists(phoneNumber);
    }

    // Override updateProfile to update database
    // @Override
    // public void updateProfile(String phoneNumber, String email) {
    //     if (validatePhone() && validateEmail()) {
    //         super.updateProfile(phoneNumber, email);
    //         // Add database update logic here
    //     }
    // }

    // User-specific methods
    public void register() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter username: ");
                this.username = scanner.nextLine();
                if (!DatabaseConnection.usernameExists(this.username)) {
                    break;
                }
                System.out.println("Username already exists! Try a new one.");
            }

            while (true) {
                System.out.print("Enter email: ");
                this.email = scanner.nextLine();
                if (validateEmail()) {
                    break;
                }
                System.out.println("Invalid or existing email. Please try again.");
            }

            while (true) {
                System.out.print("Enter phone number: ");
                this.phoneNumber = scanner.nextLine();
                if (validatePhone()) {
                    break;
                }
                System.out.println("Invalid or existing phone number. Please try again.");
            }

            while (true) {
                System.out.print("Enter password (at least 8 characters): ");
                this.password = scanner.nextLine();
                if (this.password.length() >= 8) {
                    break;
                }
                System.out.println("Password must be at least 8 characters long. Please try again.");
            }

            int userId = DatabaseConnection.insertUser( phoneNumber, email, username, password);
            if (userId > 0) {
                System.out.println("Registration successful!");
                displayInfo(); // Show all information after registration
            } else {
                System.out.println("Registration failed.");
            }
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // =================== Login Method ===================
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\n====== Login Options ======");
            System.out.println("1. Login with Username");
            System.out.println("2. Login with Email");
            System.out.print("Choose login method: ");
            
            int loginChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            String identifier; // Will store either username or email
            String password;
            
            switch (loginChoice) {
                case 1:
                    System.out.print("Enter username: ");
                    identifier = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (DatabaseConnection.userExists(identifier, password)) {
                        System.out.println("Login successful!");
                        this.username = identifier;
                        loginHistory.add(identifier + " logged in at " + new java.util.Date());
                        return true;
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter email: ");
                    identifier = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (DatabaseConnection.emailLogin(identifier, password)) {
                        // Get username associated with this email
                        this.username = DatabaseConnection.getUsernameByEmail(identifier);
                        System.out.println("Login successful!");
                        loginHistory.add(this.username + " logged in with email at " + new java.util.Date());
                        return true;
                    }
                    break;
                    
                default:
                    System.out.println("Invalid login option.");
                    return false;
            }
            
            System.out.println("Invalid credentials.");
            return false;
            
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // =================== Display Login History ===================
    public void displayStats() {
        System.out.println("\nLogin History:");
        for (String log : loginHistory) {
            System.out.println(log);
        }
    }

}