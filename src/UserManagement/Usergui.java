package UserManagement;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import DatabaseConnector.DatabaseConnection;
import Main.Session;

public class Usergui extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField loginUsernameField, registerFirstNameField, registerLastNameField,
            registerDobField, registerPhoneNumberField, registerEmailField, registerUsernameField;
    private JPasswordField loginPasswordField, registerPasswordField, confirmPasswordField;
    private JComboBox<String> genderComboBox;

    private String loggedInUser; // Stores the logged-in username


    public Usergui() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Main Menu Panel
        JPanel mainMenuPanel = createMainMenuPanel();
        mainPanel.add(mainMenuPanel, "MainMenu");

        // Login Panel
        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, "Login");

        // Register Panel
        JPanel registerPanel = createRegisterPanel();
        mainPanel.add(registerPanel, "Register");

        // User Menu Panel
        // JPanel userMenuPanel = createUserMenuPanel();
        // mainPanel.add(userMenuPanel, "UserMenu");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "MainMenu");
    }

    private JPanel createMainMenuPanel() {
        // Create a panel for buttons with a larger size
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Main Menu",TitledBorder.CENTER, TitledBorder.TOP));
        panel.setPreferredSize(new Dimension(400, 400)); // Force bigger size
        panel.setMaximumSize(new Dimension(400, 400));
        panel.setMinimumSize(new Dimension(400, 400));
    
        // Create buttons
        JButton registerButton = new JButton("Register New User");
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");
    
        // Set button size
        Dimension buttonSize = new Dimension(250, 60);
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
    
        // Center align buttons
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Add buttons with spacing
        panel.add(Box.createVerticalGlue()); // Pushes content to center
        panel.add(registerButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        exitButton.addActionListener(e -> System.exit(0));
    
        // Wrapper panel to ensure the panel is centered and sized correctly
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setPreferredSize(new Dimension(600, 500)); // Make the whole container bigger
        wrapperPanel.add(panel, BorderLayout.CENTER);
    
        return wrapperPanel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));
        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Username or Email:"));
        panel.add(loginUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);
        panel.add(loginButton);
        panel.add(backButton);

        loginButton.addActionListener(e -> loginUser());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Register"));

        registerFirstNameField = new JTextField();
        registerLastNameField = new JTextField();
        registerDobField = new JTextField();
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        registerPhoneNumberField = new JTextField();
        registerEmailField = new JTextField();
        registerUsernameField = new JTextField();
        registerPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("First Name:"));
        panel.add(registerFirstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(registerLastNameField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(registerDobField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderComboBox);
        panel.add(new JLabel("Phone Number:"));
        panel.add(registerPhoneNumberField);
        panel.add(new JLabel("Email:"));
        panel.add(registerEmailField);
        panel.add(new JLabel("Username:"));
        panel.add(registerUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(registerPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(registerButton);
        panel.add(backButton);

        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return panel;
    }

    private void loginUser() {
        String usernameOrEmail = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isValidUser = DatabaseConnection.userExists(usernameOrEmail, password) ||
                DatabaseConnection.emailLogin(usernameOrEmail, password);
        int userId = DatabaseConnection.getUserId(usernameOrEmail, password);
        if (isValidUser) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loggedInUser = usernameOrEmail;
            Session.setUserId(userId);
            SwingUtilities.getWindowAncestor(this).dispose();

        // Open the main application window (Replace with your actual main page class)
        SwingUtilities.invokeLater(() -> {
            Main.AppGui mainGui = new Main.AppGui();
            mainGui.setVisible(true);
        });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        String firstName = registerFirstNameField.getText();
        String lastName = registerLastNameField.getText();
        String dob = registerDobField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String phoneNumber = registerPhoneNumberField.getText();
        String email = registerEmailField.getText();
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || phoneNumber.isEmpty() ||
                email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = DatabaseConnection.insertUser(firstName, lastName, dob, gender, phoneNumber, email, username, password) > 0;
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MainMenu");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        
    }
}