package UserManagement;

import java.util.*;

public class User {
    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    private static Map<String, String> userCredentials = new HashMap<>();
    //use map to quick access to a password when user login (username must be unique)
    private static Set<String> activeUsers = new HashSet<>();
    //use set to store active users
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
        System.out.print("Enter phone number: ");
        this.phoneNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        this.email = scanner.nextLine();
        System.out.print("Enter username: ");
        this.username = scanner.nextLine();
        System.out.print("Enter password: ");
        this.password = scanner.nextLine();
        
        // store usercredential into map
        userCredentials.put(this.username, this.password);
        System.out.println("User registered successfully!");
    }

    
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            System.out.println("Login successful!");
            // Add to active users set
            activeUsers.add(username);
            // Add to login history
            loginHistory.add(username + " logged in at " + new Date());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            System.out.print("Forget password?(yes/no) : ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                forgetPassword();
            }
            return false;
        }
    }

    
    public void forgetPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (userCredentials.containsKey(username)) {
            System.out.println("Your password is: " + userCredentials.get(username));
        } else {
            System.out.println("Username not found!");
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