package report;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Main.Session;

import java.awt.event.*;
import java.io.File;
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
            String query = "SELECT id, file_name FROM reports where user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Session.getUserId());
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

                // Open a file chooser for saving
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save PDF File");
                fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
                fileChooser.setSelectedFile(new File(selectedReport + ".pdf"));

                int userSelection = fileChooser.showSaveDialog(popupDialog);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();

                    // Ensure file has .pdf extension
                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }

                    // Retrieve and save the PDF
                    ReportScript.retrievePDF(reportId, filePath);

                    JOptionPane.showMessageDialog(popupDialog, "PDF saved successfully at: " + filePath);
                    popupDialog.dispose(); // Close popup after selection
                }

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
