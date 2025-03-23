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
        setLayout(new BorderLayout());

        // 🌟 HEADER: Expense Management System 🌟
        JLabel headerLabel = new JLabel("Expense Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 🏠 Add panels
        JPanel loginPanel = createLoginPanel();
        JPanel menuPanel = createMenuPanel();
        JPanel changePasswordPanel = createChangePasswordPanel();
        JPanel changeUsernamePanel = createChangeUsernamePanel();
        JPanel changePhonePanel = createChangePhonePanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(changePasswordPanel, "ChangePassword"); // ✅ Ensured it is added
        mainPanel.add(changeUsernamePanel, "ChangeUsername");
        mainPanel.add(changePhonePanel, "ChangePhone");

        // 📌 Adding to Main Layout
        add(headerLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Login"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        loginUsernameField = new JTextField(15);
        loginPasswordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(loginUsernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(loginPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(loginButton, gbc);

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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Change Password"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        newPasswordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        JButton saveButton = new JButton("Save");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1; panel.add(newPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; panel.add(confirmPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> updatePassword());

        return panel;
    }

    private void updatePassword() {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
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

    private JPanel createChangePhonePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Change Phone Number"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        newPhoneField = new JTextField(15);
        JButton saveButton = new JButton("Save");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("New Phone Number:"), gbc);
        gbc.gridx = 1; panel.add(newPhoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(saveButton, gbc);

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

    private JPanel createChangeUsernamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Change Username"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        newUsernameField = new JTextField(15);
        JButton saveButton = new JButton("Save");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("New Username:"), gbc);
        gbc.gridx = 1; panel.add(newUsernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(saveButton, gbc);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Usersettinggui());
            frame.setSize(400, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
