package budget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Main.AppGui;
import Main.Session;

public class BudgetGui extends JPanel {
    private AppGui parent;
    private BudgetManager budgetManager;
    private JTextField weeklyField, monthlyField, currencyField;
    private JTextArea outputArea;

    public BudgetGui(AppGui parent) {
        this.parent = parent;
        this.budgetManager = null;
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        JPanel bacPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> parent.switchPanel("Home"));
        bacPanel.add(backButton);
        topPanel.add(bacPanel);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10,10,10,10));
        inputPanel.add(new JLabel("Weekly Budget:"));
        weeklyField = new JTextField();
        inputPanel.add(weeklyField);

        inputPanel.add(new JLabel("Monthly Budget:"));
        monthlyField = new JTextField();
        inputPanel.add(monthlyField);

        inputPanel.add(new JLabel("Currency (USD/KHR):"));
        currencyField = new JTextField();
        inputPanel.add(currencyField);
        topPanel.add(inputPanel);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JButton createButton = new JButton("Create Budget");
        JButton modifyButton = new JButton("Modify Budget");
        JButton removeButton = new JButton("Remove Budget");
        JButton viewButton = new JButton("View Budget");

        createButton.addActionListener(e -> createBudget());
        modifyButton.addActionListener(e -> modifyBudget());
        removeButton.addActionListener(e -> removeBudget());
        viewButton.addActionListener(e -> viewBudget());

        buttonPanel.add(createButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createBudget() {
        try {
            int userID = Session.getUserId();
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
            int userID = Session.getUserId();
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
            int userID = Session.getUserId();

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

    private void viewBudget() {
        try {
            int userID = Session.getUserId();

            if (budgetManager == null || budgetManager.userExists(userID)) {
                budgetManager = new BudgetManager(userID);
            }

            String budgetDetails = budgetManager.getBudgetDetails();

            if (budgetDetails != null) {
                outputArea.setText(budgetDetails);
            } else {
                outputArea.setText("No budget found for this User ID: " + userID);
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Invalid User ID.");
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }
}