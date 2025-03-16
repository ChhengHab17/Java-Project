package UserManagement;

import DatabaseConnector.DatabaseConnection;
import java.util.*;
import Systemsetting.Usersetting;
import Systemsetting.Useracc;

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
    } catch (Exception e) {
        System.out.println("An error occurred. Please try again.");
    }
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();
    
            if (DatabaseConnection.userExists(enteredUsername, enteredPassword)) {
                System.out.println("Login successful!");
                
                // Update the username field of the User object
                this.username = enteredUsername;
    
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void displayStats() {
        System.out.println("\nLogin History:");
        for (String log : loginHistory) {
            System.out.println(log);
        }
    }


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
            scanner.nextLine();  // Consume newline
    
            switch (choice) {
                case 1:
                    userSetting.changePassword(); // Call the changePassword method
                    break;
                case 2:
                    userSetting.changeUsername(); // Call the changeUsername method
                    break;
                case 3:
                    System.out.println("Change Phone Number feature not implemented yet.");
                    break;
                case 4:
                    System.out.println("Change Email feature not implemented yet.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
        public static void main(String[] args) {
        User user = new User("", "", "", "", "", "", "", "");
        //user.register();
        if (user.login()) {
            user.chooseSetting();
            user.displayStats();
        }
    }
}



