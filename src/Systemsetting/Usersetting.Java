package Systemsetting;

import DatabaseConnector.DatabaseConnection;
import java.util.Scanner;

public class Usersetting {
    private String username;
    private Scanner scanner;

    public Usersetting(String username) {
        this.username = username;
        this.scanner = new Scanner(System.in);
    }

    public void changePassword() {
        System.out.println("\n🔒 Password Reset Process 🔒");
    
        // Debugging: Print the username
        System.out.println("Debug: Username for password reset: " + username);
    
        // Step 1: Ask for current password
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
    
        // Step 2: Verify password with DB
        String storedPassword = DatabaseConnection.getPassword(username);
        if (storedPassword == null) {
            System.out.println("⚠️ Error: Unable to retrieve your password. Try again later.");
            return;
        }
        if (!storedPassword.equals(currentPassword)) {
            System.out.println("❌ Incorrect password. Password reset failed.");
            return;
        }
    
        // Step 3: Ask for a new password
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
    
        // Step 4: Confirm password change
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();
    
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Passwords do not match. Please try again.");
            return;
        }
    
        // Step 5: Update password in DB
        if (DatabaseConnection.updatePassword(username, newPassword)) {
            System.out.println("✅ Password changed successfully!");
        } else {
            System.out.println("❌ Failed to update password. Try again later.");
        }
    }


    public void changeUsername() {
        System.out.println("\n🔒 Username Change Process 🔒");
    
        // Step 1: Ask for old username and password
        System.out.print("Enter your current username: ");
        String currentUsername = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces
    
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
    
        // Step 2: Verify username and password with DB
        System.out.println("Debug: Verifying username '" + currentUsername + "' and password.");
        String storedPassword = DatabaseConnection.getPassword(currentUsername);
        if (storedPassword == null) {
            System.out.println("⚠️ Error: Unable to retrieve your password. Please check your username.");
            return;
        }
        if (!storedPassword.equals(currentPassword)) {
            System.out.println("❌ Incorrect password. Username change failed.");
            return;
        }
    
        // Step 3: Ask for a new username
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine().trim();
    
        // Step 4: Confirm new username
        System.out.print("Confirm new username: ");
        String confirmUsername = scanner.nextLine().trim();
    
        if (!newUsername.equals(confirmUsername)) {
            System.out.println("❌ Usernames do not match. Please try again.");
            return;
        }
    
        // Step 5: Update username in DB
        if (DatabaseConnection.updateUsername(currentUsername, newUsername)) {
            System.out.println("✅ Username changed successfully!");
            this.username = newUsername; // Update the username in the current session
        } else {
            System.out.println("❌ Failed to update username. Try again later.");
        }
    }
}
