package Main;

import javax.swing.*;
import Expense.ExpenseGUI;
import Expense.ExpenseManager;
import Systemsetting.Usersettinggui;
import UserManagement.Usergui;
import budget.BudgetGui;
import report.*;
import java.awt.*;
import java.awt.event.*;

public class AppGui extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ExpenseGUI expensePanel;
    private Usersettinggui userSetting;
    ExpenseManager expenseManager = new ExpenseManager();

    public AppGui() {
        setTitle("Main menu");
        setSize(600, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel();;
        JPanel homePanel = createHomePanel();
        ReportGUI reportPanel = new ReportGUI(this);
        expensePanel = new ExpenseGUI(this);
        userSetting = new Usersettinggui(this);
        BudgetGui budgetPanel = new BudgetGui(this);

        mainPanel.setLayout(cardLayout);
        mainPanel.add("Home", homePanel);
        mainPanel.add("Report", reportPanel);
        mainPanel.add("Expense", expensePanel);
        mainPanel.add("Settings", userSetting);
        mainPanel.add("Budget", budgetPanel);
        cardLayout.show(mainPanel, "Login");
        add(mainPanel);

        setVisible(true);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());  
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title (Centered)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Expense Management System");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(titlePanel, BorderLayout.NORTH);
    
        // Main content panel with vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 100)));
    
    
        // Buttons (Centered)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 20, 20)); // 5 buttons in a single column
        buttonPanel.setMaximumSize(new Dimension(400, 500)); // Restrict max size
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JButton expenseBtn = createStyledButton("Expense Management");
        JButton reportBtn = createStyledButton("Report Management");
        JButton budgetBtn = createStyledButton("Budget Management");
        JButton settingsBtn = createStyledButton("System Settings");
        JButton exitBtn = createStyledButton("Exit");
        
        exitBtn.setBackground(Color.GRAY);
        exitBtn.setForeground(Color.WHITE);

        expenseBtn.addActionListener(e -> switchPanel("Expense"));
        reportBtn.addActionListener(e -> switchPanel("Report"));
        budgetBtn.addActionListener(e -> switchPanel("Budget"));
        settingsBtn.addActionListener(e -> switchPanel("Settings"));
        exitBtn.addActionListener(e -> System.exit(0));
    
        buttonPanel.add(expenseBtn);
        buttonPanel.add(reportBtn);
        buttonPanel.add(budgetBtn);
        buttonPanel.add(settingsBtn);
        buttonPanel.add(exitBtn);
    
        contentPanel.add(buttonPanel);
        panel.add(contentPanel, BorderLayout.CENTER);
    
        return panel;

    }
    
    
    

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setMargin(new Insets(5, 15, 5, 15)); // Add padding inside the button
        button.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10), // Rounded border
                BorderFactory.createEmptyBorder(5, 15, 5, 15) // Extra padding inside
        ));
        button.setPreferredSize(new Dimension(300,30));
        return button;
    }

    public void switchPanel(String panelName) {
        if (!Session.isUserLoggedIn() && !panelName.equals("Home")) {
            JOptionPane.showMessageDialog(this, "Please log in first!", "Access Denied", JOptionPane.WARNING_MESSAGE);
            return;
        }
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Usergui());
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}







class RoundedBorder implements javax.swing.border.Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
