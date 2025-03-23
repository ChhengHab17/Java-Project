package Systemsetting;

import javax.swing.*;
import java.awt.*;
import DatabaseConnector.DatabaseConnection;


public class Usersettinggui extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    private JTextField newUsernameField;
    private JPasswordField newPasswordField, confirmPasswordField;
    private JTextField newPhoneField;

    private String loggedInUser; // Stores the logged-in username

    public Usersettinggui() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "Login");

        // Menu Panel
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        // Settings Panels
        mainPanel.add(createChangePasswordPanel(), "ChangePassword");
        mainPanel.add(createChangeUsernamePanel(), "ChangeUsername");
        mainPanel.add(createChangePhonePanel(), "ChangePhone");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(loginUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);
        panel.add(loginButton);

        loginButton.addActionListener(e -> loginUser());

        return panel;
    }

    private void loginUser() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isValidUser = DatabaseConnection.userExists(username, password);
        if (isValidUser) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loggedInUser = username;
            cardLayout.show(mainPanel, "Menu");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("User Settings"));

        JButton changePasswordButton = new JButton("Change Password");
        JButton changeUsernameButton = new JButton("Change Username");
        JButton changePhoneButton = new JButton("Change Phone Number");
        JButton logoutButton = new JButton("Logout");

        panel.add(changePasswordButton);
        panel.add(changeUsernameButton);
        panel.add(changePhoneButton);
        panel.add(logoutButton);

        changePasswordButton.addActionListener(e -> cardLayout.show(mainPanel, "ChangePassword"));
        changeUsernameButton.addActionListener(e -> cardLayout.show(mainPanel, "ChangeUsername"));
        changePhoneButton.addActionListener(e -> cardLayout.show(mainPanel, "ChangePhone"));
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        return panel;
    }

    private JPanel createChangePasswordPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Change Password"));

        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        JButton saveButton = new JButton("Save");

        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(saveButton);

        saveButton.addActionListener(e -> updatePassword());

        return panel;
    }

    private void updatePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseConnection.updatePassword(loggedInUser, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Menu");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createChangeUsernamePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Change Username"));

        newUsernameField = new JTextField();
        JButton saveButton = new JButton("Save");

        panel.add(new JLabel("New Username:"));
        panel.add(newUsernameField);
        panel.add(saveButton);

        saveButton.addActionListener(e -> updateUsername());

        return panel;
    }

    private void updateUsername() {
        String newUsername = newUsernameField.getText();

        if (newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseConnection.updateUsername(loggedInUser, newUsername);
        if (success) {
            JOptionPane.showMessageDialog(this, "Username updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loggedInUser = newUsername;
            cardLayout.show(mainPanel, "Menu");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createChangePhonePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Change Phone Number"));

        newPhoneField = new JTextField();
        JButton saveButton = new JButton("Save");

        panel.add(new JLabel("New Phone Number:"));
        panel.add(newPhoneField);
        panel.add(saveButton);

        saveButton.addActionListener(e -> updatePhone());

        return panel;
    }

    private void updatePhone() {
        String newPhoneNumber = newPhoneField.getText();

        if (newPhoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseConnection.updatePhonenumber(loggedInUser, newPhoneNumber);
        if (success) {
            JOptionPane.showMessageDialog(this, "Phone number updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Menu");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update phone number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Usersettinggui());
            frame.setSize(400, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}