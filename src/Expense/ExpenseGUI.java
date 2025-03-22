package Expense;

import Expense.ExpenseManager;
import Main.AppGui;
import report.Category;
import Expense.Expense;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ExpenseGUI extends JPanel {
    private AppGui appGui;
    private ExpenseManager expenseManager;
    private JTextField txtCategory, txtDescription, txtAmount, txtCurrency, txtDate;
    private JLabel lblTotalUSD, lblTotalKHR;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public ExpenseGUI(AppGui appGui) {
        this.appGui = appGui;
        expenseManager = new ExpenseManager();
        this.setLayout(new BorderLayout()); // Ensure BorderLayout is set
        
        // Top Panel for input fields
        JButton backButton = new JButton("Back");
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);

        // Create input panel for expense entry
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        TitledBorder border = BorderFactory.createTitledBorder("Expense");
        border.setTitleFont(new Font("Arial", Font.BOLD, 28));
        border.setTitleJustification(TitledBorder.CENTER);
        inputPanel.setBorder(border);

        inputPanel.add(new JLabel("Category:"));
        txtCategory = new JTextField();
        inputPanel.add(txtCategory);

        inputPanel.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        inputPanel.add(txtDescription);

        inputPanel.add(new JLabel("Amount:"));
        txtAmount = new JTextField();
        inputPanel.add(txtAmount);

        inputPanel.add(new JLabel("Currency (USD/KHR):"));
        txtCurrency = new JTextField();
        inputPanel.add(txtCurrency);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        txtDate = new JTextField();
        inputPanel.add(txtDate);

        // Create a panel to hold both back button and input panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backPanel, BorderLayout.NORTH); // Back button at the top
        topPanel.add(inputPanel, BorderLayout.CENTER); // Input fields below

        add(topPanel, BorderLayout.NORTH);

        // Center Table for displaying expenses
        tableModel = new DefaultTableModel(new String[]{"Category", "Description", "Amount", "Currency", "Date"}, 0);
        expenseTable = new JTable(tableModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // Bottom Panel for buttons and totals
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JButton btnAdd = new JButton("Add");
        JButton btnView = new JButton("Reload");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        bottomPanel.add(buttonPanel);

        // Panel for displaying totals
        JPanel totalPanel = new JPanel(new GridLayout(1, 2));
        lblTotalUSD = new JLabel("Total USD: $0.0");
        lblTotalKHR = new JLabel("Total KHR: 0៛");
        totalPanel.add(lblTotalUSD);
        totalPanel.add(lblTotalKHR);
        bottomPanel.add(totalPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        backButton.addActionListener(e -> appGui.switchPanel("Home"));
        btnAdd.addActionListener(e -> addExpense());
        btnView.addActionListener(e -> viewExpenses());
        btnEdit.addActionListener(e -> editExpense());
        btnDelete.addActionListener(e -> deleteExpense());

        setVisible(true);
    }

    private void addExpense() {
        try {
            String categoryName = txtCategory.getText();
            String description = txtDescription.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            String currency = txtCurrency.getText();
            LocalDate date = LocalDate.parse(txtDate.getText());

            Category category = new Category(categoryName, description);
            expenseManager.addExpense(category, amount, date, currency);
            JOptionPane.showMessageDialog(this, "Expense added successfully!");

            clearFields();
            viewExpenses(); // Refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your values.");
        }
    }

    private void viewExpenses() {
        tableModel.setRowCount(0); // Clear previous data
        for (Expense expense : expenseManager.getExpenses()) {
            tableModel.addRow(new Object[]{
                expense.getCategory().getName(),
                expense.getCategory().getDescription(),
                expense.getAmount(),
                expense.getCurrency(),
                expense.getDate()
            });
        }
        updateTotal();
    }

    private void editExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to edit.");
            return;
        }

        String categoryName = (String) tableModel.getValueAt(selectedRow, 0);
        String description = (String) tableModel.getValueAt(selectedRow, 1);
        Category category = new Category(categoryName, description);

        try {
            double newAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter new amount:"));
            LocalDate newDate = LocalDate.parse(JOptionPane.showInputDialog("Enter new date (YYYY-MM-DD):"));

            if (expenseManager.editExpense(category, newAmount, newDate)) {
                JOptionPane.showMessageDialog(this, "Expense updated successfully!");
                viewExpenses();
            } else {
                JOptionPane.showMessageDialog(this, "Expense not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void deleteExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
            return;
        }

        String categoryName = (String) tableModel.getValueAt(selectedRow, 0);
        String description = (String) tableModel.getValueAt(selectedRow, 1);
        Category category = new Category(categoryName, description);

        if (expenseManager.deleteExpense(category)) {
            JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
            viewExpenses();
        } else {
            JOptionPane.showMessageDialog(this, "Expense not found.");
        }
    }

    private void updateTotal() {
        lblTotalUSD.setText("Total USD: $" + expenseManager.calculateTotalUSD());
        lblTotalKHR.setText("Total KHR: " + expenseManager.calculateTotalKHR() + "៛");
    }

    private void clearFields() {
        txtCategory.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtCurrency.setText("");
        txtDate.setText("");
    }
}
