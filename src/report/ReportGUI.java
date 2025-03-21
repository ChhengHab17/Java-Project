package report;

import javax.swing.*;

import Main.AppGui;

import java.awt.*;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ReportGUI extends JPanel {
    private AppGui parentFrame;
    private JLabel startDateLabel, endDateLabel, yearLabel, monthLabel;
    private JTextField idField, fileNamefield, startDateField, endDateField, yearField, monthField;
    private JComboBox<String> reportTypeCombo;
    private JButton generateButton, displayButton, retriveButton, bacButton;
    private JTextArea resultArea;
    JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel buttonPanel = new JPanel(new GridLayout(2,1,10,10));

    public ReportGUI(AppGui parentFrame) {
        this.parentFrame = parentFrame;
        setName("Report");
        setSize(600,820);
        
        setLayout(new BorderLayout(10, 10)); // BorderLayout for better control

        // === TOP PANEL (Form Fields in GridLayout) ===
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        reportTypeCombo = new JComboBox<>(new String[]{"Full Report", "Monthly Report", "Yearly Report", "Customizable Report"});
        bacButton = new JButton("Back");
        topPanel.add(bacButton);
        topPanel.add(Box.createHorizontalStrut(150));
        topPanel.add(reportTypeCombo);

        inputPanel.add(new JLabel("Report ID:"));
        idField = new JTextField(10);
        inputPanel.add(idField);

        inputPanel.add(new JLabel("File Name:"));
        fileNamefield = new JTextField(10);
        inputPanel.add(fileNamefield);

        startDateLabel = new JLabel("Start Date (YYYY-MM-DD): ");
        inputPanel.add(startDateLabel);
        startDateField = new JTextField(10);
        inputPanel.add(startDateField);

        endDateLabel = new JLabel("End Date (YYYY-MM-DD): ");
        inputPanel.add(endDateLabel);
        endDateField = new JTextField(10);
        inputPanel.add(endDateField);

        yearLabel = new JLabel("Year: ");
        inputPanel.add(yearLabel);
        yearField = new JTextField(10);  // Initialize yearField
        inputPanel.add(yearField);

        monthLabel = new JLabel("Month: ");
        inputPanel.add(monthLabel);
        monthField = new JTextField(10); // Initialize monthField
        inputPanel.add(monthField);

        idField.setPreferredSize(new Dimension(150,50));
        retriveButton = new JButton("Retrieve PDF");
        generateButton = new JButton("Generate PDF");
        displayButton = new JButton("Display Report");
        buttonPanel.add(generateButton);
        buttonPanel.add(retriveButton);
        inputPanel.add(displayButton);
        inputPanel.add(buttonPanel);

        // === BOTTOM PANEL (TextArea for Results) ===
        resultArea = new JTextArea(20, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        

        // === ADD PANELS TO FRAME ===
        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        reportTypeCombo.addActionListener(e -> updateFields());
        bacButton.addActionListener(e -> parentFrame.switchPanel("Home"));
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Report report = getReportFromInput();
                    report.generatePDF();
                    JOptionPane.showMessageDialog(null, "PDF Report Generated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Report report = getReportFromInput();
                    resultArea.setText(report.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        retriveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ReportPopup popup = new ReportPopup(null);
                    popup.showPopup();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        updateFields();
        setVisible(true);
    }
    private void updateFields() {
        String selectedType = (String) reportTypeCombo.getSelectedItem();
    
        inputPanel.removeAll(); // Remove all components
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Rebuild layout dynamically
    
        // Always add Report Type
        topPanel.add(reportTypeCombo);
    
        inputPanel.add(new JLabel("Report ID:"));
        inputPanel.add(idField);
        idField.setPreferredSize(new Dimension(150,50));
    
        inputPanel.add(new JLabel("File Name:"));
        inputPanel.add(fileNamefield);
    
        if (selectedType.equals("Monthly Report")) {
            inputPanel.add(new JLabel("Year (YYYY):"));
            inputPanel.add(yearField);
    
            inputPanel.add(new JLabel("Month (1-12):"));
            inputPanel.add(monthField);
        } else if (selectedType.equals("Yearly Report")) {
            inputPanel.add(new JLabel("Year (YYYY):"));
            inputPanel.add(yearField);
        }else if (selectedType.equals("Customizable Report")) {
            startDateLabel = new JLabel("Start Date (YYYY-MM-DD): ");
            inputPanel.add(startDateLabel);
            inputPanel.add(startDateField);
    
            endDateLabel = new JLabel("End Date (YYYY-MM-DD): ");
            inputPanel.add(endDateLabel);
            inputPanel.add(endDateField);
        }
        buttonPanel.add(generateButton);
        buttonPanel.add(retriveButton);
        inputPanel.add(buttonPanel);
        inputPanel.add(displayButton);
        
        inputPanel.revalidate(); // Refresh layout
        inputPanel.repaint();

        idField.setText("");
        fileNamefield.setText("");
        startDateField.setText("");
        endDateField.setText("");
        yearField.setText("");
        monthField.setText("");
    }
    private Report getReportFromInput() throws Exception {
        int id = Integer.parseInt(idField.getText());
        String fileName = fileNamefield.getText();
        String selectedType = (String) reportTypeCombo.getSelectedItem();

        if (selectedType.equals("Customizable Report")) {
            LocalDate startDate = LocalDate.parse(startDateField.getText());
            LocalDate endDate = LocalDate.parse(endDateField.getText());
            return new CustomizeReport(id, fileName, startDate, endDate);
        } else if (selectedType.equals("Monthly Report")) {
            int year = Integer.parseInt(yearField.getText());
            int month = Integer.parseInt(monthField.getText());
            return new MonthlyReport(id, fileName, year, month);
        } else if (selectedType.equals("Yearly Report")) {
            int year = Integer.parseInt(yearField.getText());
            return new YearlyReport(id, fileName, year);
        } else {
            return new Report(id, fileName);
        }
    }
    public static void main(String[] args) {
        
    }
}
