package Expense;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ExpenseGUI extends JPanel {
    private ExpenseManager expenseManager;
    private JTextField txtCategory, txtDescription, txtAmount, txtCurrency, txtDate;
    private JLabel lblTotalUSD, lblTotalKHR;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public ExpenseGUI() {
        expenseManager = new ExpenseManager();

        setLayout(new BorderLayout());

        // Top Panel for Input Fields
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

        add(inputPanel, BorderLayout.NORTH);

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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expense")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                String currency = rs.getString("currency");
                LocalDate date = rs.getDate("date").toLocalDate();

                tableModel.addRow(new Object[]{id, category, description, amount, currency, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTotal();
    }

    private void editExpense() {
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

    private void updateTotal() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT SUM(CASE WHEN currency = 'USD' THEN amount ELSE amount / 4100 END) AS total_in_usd, " +
                     "SUM(CASE WHEN currency = 'KHR' THEN amount ELSE amount * 4100 END) AS total_in_khr FROM expense")) {

            if (rs.next()) {
                double totalUSD = rs.getDouble("total_in_usd");
                double totalKHR = rs.getDouble("total_in_khr");

                lblTotalUSD.setText("Total USD: $" + String.format("%.2f", totalUSD));
                lblTotalKHR.setText("Total KHR: " + String.format("%.2f", totalKHR) + "៛");
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
}
