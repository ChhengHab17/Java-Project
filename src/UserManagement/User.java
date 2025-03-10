package UserManagement;

import java.util.*;

import databaseconnector.DatabaseConnection;

public class User {
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    private static ArrayList<String> loginHistory = new ArrayList<>();

    public User(String firstName, String lastName, String dob, String gender, String phoneNumber, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
        
        while(true){
            System.out.print("Enter phone number: ");
            this.phoneNumber = scanner.nextLine();
            if(!DatabaseConnection.phoneNumberExists(this.phoneNumber)){
                break;
            }else{
                System.out.println("Phone number already exists please try a new Phone number!!");
            }
        }

        while (true) {
            System.out.print("Enter email: ");
            this.email = scanner.nextLine();
            if(this.email.contains("@")){
                break;
            }else{
                System.out.println("invalid email please try again!!");
            }
        }
        System.out.print("Enter username: ");
        this.username = scanner.nextLine();
        System.out.print("Enter password: ");
        this.password = scanner.nextLine();

        // Store user credentials in the database and get the generated user ID
        //this line use to insert user data into database If something goes wrong, it will return 0 or -1 to indicate failure.
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
            // Add to login history
            loginHistory.add(username + " logged in at " + new Date());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            System.out.print("Forget password?(yes/no) : ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                //forgetPassword();
            }
            return false;
        }
    }

    // public void forgetPassword() {
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.print("Enter your username: ");
    //     String username = scanner.nextLine();
    //     // Retrieve password from the database
    //     String password = DatabaseConnection.getPassword(username);
    //     if (password != null) {
    //         System.out.println("Your password is: " + password);
    //     } else {
    //         System.out.println("Username not found!");
    //     }
    // }

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