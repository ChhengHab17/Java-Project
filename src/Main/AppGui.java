package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import report.*;

public class AppGui extends JFrame{
    private JPanel mainPanel;
    private CardLayout cardLayout;
    public AppGui() {
        setTitle("Main menu");
        setSize(600, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel();
        

        JPanel homePanel = createHomePanel();
        ReportGUI reportPanel = new ReportGUI(this);

        mainPanel.setLayout(cardLayout);
        mainPanel.add("Home", homePanel);
        mainPanel.add("Report", reportPanel);

        add(mainPanel);
        setVisible(true);
    }
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        JLabel title = new JLabel("Expense Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        JButton expenseBtn = new JButton("Expense Management");
        JButton reportBtn = new JButton("Report Management");
        JButton budgetBtn = new JButton("Budget Management");
        JButton settingsBtn = new JButton("System Settings");
        JButton exitBtn = new JButton("Exit");

        // Switch to corresponding panels when buttons are clicked

        expenseBtn.addActionListener(e -> switchPanel("Expense"));
        reportBtn.addActionListener(e -> switchPanel("Report"));
        budgetBtn.addActionListener(e -> switchPanel("Budget"));
        settingsBtn.addActionListener(e -> switchPanel("Settings"));
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(title);
        panel.add(expenseBtn);
        panel.add(reportBtn);
        panel.add(budgetBtn);
        panel.add(settingsBtn);
        panel.add(exitBtn);

        return panel;
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppGui::new);
    }
}
