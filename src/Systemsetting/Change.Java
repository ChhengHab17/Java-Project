package Systemsetting;

import DatabaseConnector.DatabaseConnection;

public class Change {
    private String username;

    public Change(String username) {
        this.username = username;
    }

    // Method to change password
    public boolean changePassword(String newPassword) {
        return DatabaseConnection.updatePassword(username, newPassword);
    }

    // Method to change username
    public boolean changeUsername(String newUsername) {
        return DatabaseConnection.updateUsername(username, newUsername);
    }

    // Method to change phone number
    public boolean changePhoneNumber(String newPhoneNumber) {
        return DatabaseConnection.updatePhoneNumber(username, newPhoneNumber);
    }

    // Method to change email
    public boolean changeEmail(String newEmail) {
        return DatabaseConnection.updateEmail(username, newEmail);
    }
}