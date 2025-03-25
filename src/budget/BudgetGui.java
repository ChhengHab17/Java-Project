package budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetGui {
    private BudgetManager budgetManager;
    private JFrame frame;
    private JTextField weeklyField, monthlyField, currencyField, userIDField;
    private JTextArea outputArea;

    public BudgetGui() {
        budgetManager = null; // Initially, no userID
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Budget Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("User ID:"));
        userIDField = new JTextField();
        inputPanel.add(userIDField);

        inputPanel.add(new JLabel("Weekly Budget:"));
        weeklyField = new JTextField();
        inputPanel.add(weeklyField);

        inputPanel.add(new JLabel("Monthly Budget:"));
        monthlyField = new JTextField();
        inputPanel.add(monthlyField);

        inputPanel.add(new JLabel("Currency (USD/KHR):"));
        currencyField = new JTextField();
        inputPanel.add(currencyField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Output area to display budget information
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2,5,5));

        JButton createButton = new JButton("Create Budget");
        JButton modifyButton = new JButton("Modify Budget");
        JButton removeButton = new JButton("Remove Budget");
        JButton vieButton = new JButton("View Budget");

        // Action Listeners for buttons
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBudget();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyBudget();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeBudget();
            }
        });

        vieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewBudget();
            }
        });

        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(vieButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void createBudget() {
        try {
            int userID = Integer.parseInt(userIDField.getText());
            double weekly = Double.parseDouble(weeklyField.getText());
            double monthly = Double.parseDouble(monthlyField.getText());
            String currency = currencyField.getText().trim().toUpperCase();

            if (budgetManager == null || budgetManager.userExists(userID)) {
                budgetManager = new BudgetManager(userID);
            }

            budgetManager.createBudget(weekly, monthly, currency);

            outputArea.setText("Budget Created Successfully!");
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Invalid input. Please enter numeric values for budget.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void modifyBudget() {
        try {
            int userID = Integer.parseInt(userIDField.getText());
            double weekly = Double.parseDouble(weeklyField.getText());
            double monthly = Double.parseDouble(monthlyField.getText());
            String currency = currencyField.getText().trim();

            if (budgetManager == null || budgetManager.userExists(userID)) {
                budgetManager = new BudgetManager(userID);
            }

            budgetManager.modifyBudget(weekly, monthly, currency);

            outputArea.setText("Budget Modified Successfully!");
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Invalid input. Please enter numeric values for budget.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private void removeBudget() {
        try {
            int userID = Integer.parseInt(userIDField.getText());

            if (budgetManager == null || budgetManager.userExists(userID)) {
                budgetManager = new BudgetManager(userID);
            }

            budgetManager.removeBudget();

            outputArea.setText("Budget Removed Successfully!");
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Invalid User ID.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    // private void loadBudget() {
    //     try {
    //         int userID = Integer.parseInt(userIDField.getText());

    //         if (budgetManager == null || budgetManager.userExists(userID)) {
    //             budgetManager = new BudgetManager(userID);
    //         }

    //         budgetManager.getBudgetFromDatabase();

    //     } catch (NumberFormatException e) {
    //         outputArea.setText("Error: Invalid User ID.");
    //     } catch (Exception e) {
    //         outputArea.setText("Error: " + e.getMessage());
    //     }
    // }
    private void viewBudget() {
        try {
            int userID = Integer.parseInt(userIDField.getText());

            if (budgetManager == null || budgetManager.userExists(userID)) {
                budgetManager = new BudgetManager(userID);
            }

            // Fetch budget details from the database and display them
            String budgetDetails = budgetManager.getBudgetDetails();

            if (budgetDetails != null) {
                outputArea.setText(budgetDetails); // Display the budget details in the output area
            } else {
                outputArea.setText("No budget found for this User ID: " + userID);
            }

        } catch (NumberFormatException e) {
            outputArea.setText("Error: Invalid User ID.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BudgetGui();
            }
        });
    }
}
