package UserManagement;

import DatabaseConnector.DatabaseConnection;
import Systemsetting.Usersetting;
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
        super.displayInfo(); // Call parent class method first
        System.out.println("Username: " + username);
        System.out.println("Login History:");
        for (String log : loginHistory) {
            System.out.println(log);
        }
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
    @Override
    public void updateProfile(String phoneNumber, String email) {
        if (validatePhone() && validateEmail()) {
            super.updateProfile(phoneNumber, email);
            // Add database update logic here
        }
    }

    // User-specific methods
    public void register() {
        Scanner scanner = new Scanner(System.in);
        try {
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
                if (validatePhone()) {
                    break;
                }
                System.out.println("Invalid or existing phone number. Please try again.");
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
                System.out.print("Enter username: ");
                this.username = scanner.nextLine();
                if (!DatabaseConnection.usernameExists(this.username)) {
                    break;
                }
                System.out.println("Username already exists! Try a new one.");
            }

            System.out.print("Enter password: ");
            this.password = scanner.nextLine();

            int userId = DatabaseConnection.insertUser(firstName, lastName, dob, gender, phoneNumber, email, username, password);
            if (userId > 0) {
                System.out.println("Registration successful! Your user ID is: " + userId);
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

    // =================== Choose Settings ===================
    public void chooseSetting() {
        Scanner scanner = new Scanner(System.in);
        Usersetting userSetting = new Usersetting(this.username); // Pass the logged-in username

        try {
            System.out.println("\n⚙️ Settings Menu:");
            System.out.println("1. Change Password");
            System.out.println("2. Change Username");
            System.out.println("3. Change Phone Number");
            System.out.println("4. Change Email");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userSetting.changePassword(); // Call the changePassword method
                    break;
                case 2:
                    userSetting.changeUsername(); // Call the changeUsername method
                    break;
                case 3:
                    userSetting.changePhonenumber(); // Call the changePhoneNumber method
                    break;
                case 4:
                    userSetting.changeEmail(); // Call the changeEmail method
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // =================== Main Method ===================
            public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            User user = new User("", "", "", "", "", "", "", "");
    
            while (true) {
                System.out.println("\n====== User Management System ======");
                System.out.println("1. Register New User");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
    
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
    
                    switch (choice) {
                        case 1:
                            user.register();
                            break;
                        case 2:
                            if (user.login()) {
                                while (true) {
                                    System.out.println("\n====== User Menu ======");
                                    System.out.println("1. Display Information");
                                    System.out.println("2. Change Settings");
                                    System.out.println("3. View Login History");
                                    System.out.println("4. Logout");
                                    System.out.print("Enter your choice: ");
    
                                    int userChoice = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
    
                                    switch (userChoice) {
                                        case 1:
                                            user.displayInfo();
                                            break;
                                        case 2:
                                            user.chooseSetting();
                                            break;
                                        case 3:
                                            user.displayStats();
                                            break;
                                        case 4:
                                            System.out.println("Logging out...");
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Please try again.");
                                    }
                                    if (userChoice == 4) break;
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Thank you for using User Management System!");
                            scanner.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }
}