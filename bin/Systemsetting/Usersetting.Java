package Systemsetting;

import DatabaseConnector.DatabaseConnection;

public class Usersetting extends Useracc {

    public UserSetting(String username) {
        super(username);  // Call superclass constructor
    }

    public boolean changePassword(String newPassword) {
        if (DatabaseConnection.userExists(username, oldPassword)) {
            return DatabaseConnection.updatePassword(username, newPassword);
        }else {
            System.out.println("Old password is incorrect!");
            return false;
        }
    }

    public boolean changeUsername(String newUsername) {
        boolean updated = DatabaseConnection.updateUsername(username, newUsername);
        if (updated) {
            setUsername(newUsername);  // Update in memory
        }
        return updated;
    }

    public boolean changePhoneNumber(String newPhoneNumber) {
        return DatabaseConnection.updatePhoneNumber(username, newPhoneNumber);
    }

    public boolean changeEmail(String newEmail) {
        return DatabaseConnection.updateEmail(username, newEmail);
    }
}

