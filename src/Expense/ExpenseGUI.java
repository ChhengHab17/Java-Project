package Expense;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Main.AppGui;
import Main.Session;

import java.sql.*;


public class ExpenseGUI extends JPanel {
    private AppGui parrent;
    private ExpenseManager expenseManager;
    private JTextField txtCategory, txtDescription, txtAmount, txtCurrency, txtDate;
    private JLabel lblTotalUSD, lblTotalKHR;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public ExpenseGUI(AppGui parrent) {
        expenseManager = new ExpenseManager();
        this.parrent = parrent;
        setLayout(new BorderLayout());

        // Top Panel with Back Button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Back Button Panel (Aligned to Left)
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> parrent.switchPanel("Home"));
        backPanel.add(btnBack);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        TitledBorder border = BorderFactory.createTitledBorder("Expense");
        border.setTitleFont(new Font("Arial", Font.BOLD, 20));
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

        // Adding components in order
        topPanel.add(backPanel);
        topPanel.add(inputPanel);

        add(topPanel, BorderLayout.NORTH);

        // Table for displaying expenses
        tableModel = new DefaultTableModel(new String[]{"ID", "Category", "Description", "Amount", "Currency", "Date"}, 0);
        expenseTable = new JTable(tableModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // Bottom Panel (Buttons & Totals)
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JButton btnAdd = new JButton("Add");
        JButton btnView = new JButton("View");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        bottomPanel.add(buttonPanel);

        // Panel for Total Expenses
        JPanel totalPanel = new JPanel(new GridLayout(1, 2));
        lblTotalUSD = new JLabel("Total USD: $0.00");
        lblTotalKHR = new JLabel("Total KHR: 0៛");
        totalPanel.add(lblTotalUSD);
        totalPanel.add(lblTotalKHR);
        bottomPanel.add(totalPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Event Listeners
        btnAdd.addActionListener(e -> addExpense());
        btnView.addActionListener(e -> viewExpenses());
        btnEdit.addActionListener(e -> editExpense());
        btnDelete.addActionListener(e -> deleteExpense());

        setVisible(true);
    }
    private void addExpense() {
        try {
            String category = txtCategory.getText();
            String description = txtDescription.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            String currency = txtCurrency.getText().toUpperCase();
            LocalDate date = LocalDate.parse(txtDate.getText());
            if (!currency.equals("USD") && !currency.equals("KHR")) {
                JOptionPane.showMessageDialog(this, "Currency must be 'USD' or 'KHR'.");
                return;
            }
            expenseManager.addExpense(category, description, amount, date, currency);
            JOptionPane.showMessageDialog(this, "Expense added successfully!");
            clearFields();
            viewExpenses(); // Refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your values.");
        }
    }
    private void viewExpenses() {
        tableModel.setRowCount(0); // Clear previous data
        String sql = "SELECT * FROM expenses WHERE user_id = ?";
        int userId = Session.getUserId(); // Get the current user's ID from the session
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId); // Set the user_id parameter in the SQL query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String category = rs.getString("category");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    String currency = rs.getString("currency");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    tableModel.addRow(new Object[]{id, category, description, amount, currency, date});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTotal(); // Assuming this method updates the total expense after viewing
    }    private void editExpense() {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expense to edit.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            double newAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter new amount:"));
            LocalDate newDate = LocalDate.parse(JOptionPane.showInputDialog("Enter new date (YYYY-MM-DD):"));
            expenseManager.editExpense(id, newAmount, newDate);
            JOptionPane.showMessageDialog(this, "Expense updated successfully!");
            viewExpenses();
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
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this expense?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            expenseManager.deleteExpense(id);
            JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
            viewExpenses();
        }
    }
    public void updateTotal() {
        int userId = Session.getUserId(); // Retrieve the logged-in user ID
    
        String sql = "SELECT " +
                     "SUM(CASE WHEN currency = 'USD' THEN amount ELSE amount / 4100 END) AS total_in_usd, " +
                     "SUM(CASE WHEN currency = 'KHR' THEN amount ELSE amount * 4100 END) AS total_in_khr " +
                     "FROM expenses WHERE user_id = ?"; // Filter by user_id
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId); // Set user ID in the query
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Handle potential null values from the SUM() function
                    double totalUSD = rs.getDouble("total_in_usd");
                    if (rs.wasNull()) totalUSD = 0.0; // If it's null, set it to 0
    
                    double totalKHR = rs.getDouble("total_in_khr");
                    if (rs.wasNull()) totalKHR = 0.0; // If it's null, set it to 0
    
                    // Update the labels with formatted values
                    lblTotalUSD.setText("Total USD: $" + String.format("%.2f", totalUSD));
                    lblTotalKHR.setText("Total KHR: " + String.format("%.2f", totalKHR) + "៛");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
        txtCategory.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtCurrency.setText("");
        txtDate.setText("");
    }

    public static void main(String[] args) {
        new AppGui();
    }
}
