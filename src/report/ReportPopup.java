package report;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class ReportPopup {
    private JFrame parentFrame;  // Main frame
    private JDialog popupDialog; // Popup window
    private JList<String> reportList;
    private DefaultListModel<String> listModel;
    private HashMap<String, Integer> reportMap;

    public ReportPopup(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        reportMap = new HashMap<>();
    }

    public void showPopup() {

        popupDialog = new JDialog(parentFrame, "Select a Reports", true);
        popupDialog.setSize(400,300);
        popupDialog.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        reportList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(reportList);

        JButton retrieveButton = new JButton("Retrieve Report");
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveSelectedReport();
            }
        });
        popupDialog.add(scrollPane, BorderLayout.CENTER);
        popupDialog.add(retrieveButton, BorderLayout.SOUTH);

        loadReportsFromDatabase();
        popupDialog.setLocationRelativeTo(parentFrame); // Center on parent
        popupDialog.setVisible(true);


    }
    private void loadReportsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
            String query = "SELECT id, file_name FROM reports";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            listModel.clear();
            reportMap.clear();
            while (rs.next()) {

                int id = rs.getInt("id");
                String fileName = rs.getString("file_name");
                listModel.addElement(fileName);
                reportMap.put(fileName, id);
            }

            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(popupDialog, "Error loading reports: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retrieveSelectedReport() {
        String selectedReport = reportList.getSelectedValue();

        if (selectedReport != null) {
            try {
                int reportId = reportMap.get(selectedReport);
                ReportScript.retrievePDF(reportId,selectedReport);
                JOptionPane.showMessageDialog(popupDialog, "PDF retrieved for: " + selectedReport);
                popupDialog.dispose(); // Close popup after selection
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(popupDialog, "Error retrieving report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(popupDialog, "Please select a report!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
    public static void main(String[] args) {
        ReportPopup popup = new ReportPopup(new JFrame());
        popup.showPopup();
    }
}
