package UserManagement;

import javax.swing.*;
import java.awt.*;
import DatabaseConnector.DatabaseConnection;

public class Usergui extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField loginUsernameField, registerFirstNameField, registerLastNameField,
            registerDobField, registerPhoneNumberField, registerEmailField, registerUsernameField;
    private JPasswordField loginPasswordField, registerPasswordField, confirmPasswordField;
    private JComboBox<String> genderComboBox;

    public Usergui() {
        setLayout(new BorderLayout());

        // 🌟 HEADER: Expense Management System 🌟
        JLabel headerLabel = new JLabel("Expense Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🔄 Main Content (CardLayout)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 🏠 Add panels
        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createRegisterPanel(), "Register");

        // 📌 Adding to Main Layout
        add(headerLabel, BorderLayout.NORTH); // 📌 Header at the Top
        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "MainMenu");
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Main Menu"));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        exitButton.addActionListener(e -> System.exit(0));

        return panel;
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
        JButton backButton = new JButton("Back");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username/Email:"), gbc);
        gbc.gridx = 1; panel.add(loginUsernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(loginPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(loginButton, gbc);
        gbc.gridx = 1; panel.add(backButton, gbc);

        loginButton.addActionListener(e -> loginUser());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Register"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        registerFirstNameField = new JTextField(15);
        registerLastNameField = new JTextField(15);
        registerDobField = new JTextField(10);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        registerPhoneNumberField = new JTextField(15);
        registerEmailField = new JTextField(15);
        registerUsernameField = new JTextField(15);
        registerPasswordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; panel.add(registerFirstNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; panel.add(registerLastNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("DOB (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(registerDobField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1; panel.add(genderComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1; panel.add(registerPhoneNumberField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(registerEmailField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(registerUsernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(registerPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 8; panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; panel.add(confirmPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 9; panel.add(registerButton, gbc);
        gbc.gridx = 1; panel.add(backButton, gbc);

        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        return panel;
    }

    private void loginUser() {
        String usernameOrEmail = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (DatabaseConnection.userExists(usernameOrEmail, password) || DatabaseConnection.emailLogin(usernameOrEmail, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Proceed to dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        String firstName = registerFirstNameField.getText();
        String lastName = registerLastNameField.getText();
        String dob = registerDobField.getText();
        String gender = genderComboBox.getSelectedItem().toString();
        String phone = registerPhoneNumberField.getText();
        String email = registerEmailField.getText();
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (DatabaseConnection.insertUser(firstName, lastName, dob, gender, phone, email, username, password) > 0) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MainMenu");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Usergui());
            frame.setSize(500, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
