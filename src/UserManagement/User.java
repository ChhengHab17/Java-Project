package UserManagement;
//register require email password and username.
//in the login method also provide a forget password function
import java.util.Scanner;
public class User {
    private String username;
    private String password;
    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // Method to register a user
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        this.username = scanner.nextLine();
        System.out.print("Enter password: ");
        this.password = scanner.nextLine();
        System.out.println("User registered successfully!");
        
    }
    // Method to login a user
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            System.out.print("Forget password?(yes/no) : ");
            String response = scanner.nextLine();
            scanner.close();
                if (response.equalsIgnoreCase("yes")) {
                    forgetpasword();
                }
            return false;
        
        }
        
        
    }

    //Method to forget password
    public void forgetpasword(){
        System.out.println("hello");
    }
    public static void main(String[] args) {
        User user = new User("", "");
        user.register();
        user.login();
    }
}
