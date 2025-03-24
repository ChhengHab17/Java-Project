package Systemsetting;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import DatabaseConnector.DatabaseConnection;
import Main.*;



public class Usersettinggui extends JPanel {
    private AppGui parrent;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private String loggedInUser; // Stores the logged-in username

    public Usersettinggui(AppGui parrent) {
        this.parrent = parrent;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Menu Panel
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

    }


    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Ensures perfect centering
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"User Settings", TitledBorder.CENTER,TitledBorder.TOP));

        
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Align vertically
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(500,600));

        JButton changePasswordButton = new JButton("Change Password");
        JButton changeUsernameButton = new JButton("Change Username");
        JButton changePhoneButton = new JButton("Change Phone Number");
        JButton logoutButton = new JButton("Back");

        Dimension buttonSize = new Dimension(400, 80);
        changePasswordButton.setMaximumSize(buttonSize);
        changeUsernameButton.setMaximumSize(buttonSize);
        changePhoneButton.setMaximumSize(buttonSize);
        logoutButton.setMaximumSize(buttonSize);

        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeUsernameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePhoneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        buttonPanel.add(changePasswordButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(changeUsernameButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(changePhoneButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        

        changePasswordButton.addActionListener(e -> showChangePasswordDialog());
        changeUsernameButton.addActionListener(e -> showChangeUsernameDialog());
        changePhoneButton.addActionListener(e -> showChangePhoneDialog());
        logoutButton.addActionListener(e -> parrent.switchPanel("Home"));

        return panel;
    }

    private void showChangePasswordDialog() {
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
    
        Object[] message = {
            "New Password:", newPasswordField,
            "Confirm Password:", confirmPasswordField
        };
    
        int option = JOptionPane.showConfirmDialog(this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
    
            if (newPassword.isEmpty() || !newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int user_id = Session.getUserId();
                boolean success = DatabaseConnection.updatePassword(user_id, newPassword);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    private void showChangeUsernameDialog() {
        JTextField newUsernameField = new JTextField();
    
        Object[] message = {"New Username:", newUsernameField};
    
        int option = JOptionPane.showConfirmDialog(this, message, "Change Username", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String newUsername = newUsernameField.getText();
            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int user_id = Session.getUserId();
                boolean success = DatabaseConnection.updateUsername(user_id, newUsername);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Username updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update username.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    private void showChangePhoneDialog() {
        JTextField newPhoneField = new JTextField();
    
        Object[] message = {"New Phone Number:", newPhoneField};
    
        int option = JOptionPane.showConfirmDialog(this, message, "Change Phone Number", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String newPhoneNumber = newPhoneField.getText();
            if (newPhoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Phone number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int user_id = Session.getUserId();
                boolean success = DatabaseConnection.updatePhonenumber(user_id, newPhoneNumber);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Phone number updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        // SwingUtilities.invokeLater(() -> {
        //     JFrame frame = new JFrame("User Management");
        //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //     frame.getContentPane().add(new Usersettinggui(null));
        //     frame.setSize(300, 200);
        //     frame.setLocationRelativeTo(null);
        //     frame.setVisible(true);
        // });
    }
}