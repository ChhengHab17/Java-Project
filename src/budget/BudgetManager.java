package budget;

import java.sql.*;


public class BudgetManager {
    private Budget budget;
    private int userID;
    private static final double EXCHANGE_RATE = 4100.0;

    public BudgetManager(int userID) {
        this.userID = userID;
        this.budget = new Budget(userID, 0, 0);
    }
    public void createBudget(double weekly, double monthly, String currency) {
        double convertedWeekly = convertCurrency(weekly, currency);
        double convertedMonthly = convertCurrency(monthly, currency);

        budget.setBudget(weekly, monthly);
        saveToDatabase(userID, weekly, monthly, currency, convertedWeekly, convertedMonthly);
    }
    public void modifyBudget(double newWeekly, double newMonthly, String currency) {
        double convertedWeekly = convertCurrency(newWeekly, currency);
        double convertedMonthly = convertCurrency(newMonthly, currency);

        budget.editBudget(newWeekly, newMonthly);
        System.out.println();
        saveToDatabase(userID, newWeekly, newMonthly, currency, convertedWeekly, convertedMonthly);
    }
    public void removeBudget() {
        budget.deleteBudget();
        deleteBudgetFromDatabase(userID);
    }
    public void loadBudget() {
        budget.displayBudget();
        getBudgetDetails();
    }
    public String getBudgetDetails() {
        String sql = "SELECT weeklyBudget, monthlyBudget, currency, convertedWeeklyBudget, convertedMonthlyBudget, setDate FROM Budget WHERE userID = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double weekly = rs.getDouble("weeklyBudget");
                double monthly = rs.getDouble("monthlyBudget");
                String currency = rs.getString("currency");
                double convertedWeekly = rs.getDouble("convertedWeeklyBudget");
                double convertedMonthly = rs.getDouble("convertedMonthlyBudget");
                Date setDate = rs.getDate("setDate");
    
                java.time.LocalDate localSetDate = setDate.toLocalDate();
    
                return "User ID: " + userID + "\n" +
                       "Weekly Budget in USD: " + weekly + " USD\n" +
                       "Monthly Budget in USD: " + monthly + " USD\n" +
                       "Weekly Budget in KHR: " + convertedWeekly + " KHR\n" +
                       "Monthly Budget in KHR: " + convertedMonthly + " KHR\n" +
                       "Set Date: " + localSetDate;
            } else {
                return null;
            }
    
        } catch (SQLException e) {
            System.out.println("Error retrieving budget details: " + e.getMessage());
            return null;
        }
    }
    
    private void saveToDatabase(int userID, double weekly, double monthly, String currency, double convertedWeekly, double convertedMonthly) {
        String sql = "INSERT INTO Budget (userID, weeklyBudget, monthlyBudget, currency, convertedWeeklyBudget, convertedMonthlyBudget, setDate) " + 
                     "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE " +
                     "weeklyBudget = VALUES(weeklyBudget), " + 
                     "monthlyBudget = VALUES(monthlyBudget), " + 
                     "currency = VALUES(currency), " +
                     "convertedWeeklyBudget = VALUES(convertedWeeklyBudget), " +
                     "convertedMonthlyBudget = VALUES(convertedMonthlyBudget), " +
                     "setDate = VALUES(setDate)";

        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

        
            stmt.setInt(1, userID);
            stmt.setDouble(2, weekly);
            stmt.setDouble(3, monthly);
            stmt.setString(4, currency);
            stmt.setDouble(5, convertedWeekly);
            stmt.setDouble(6, convertedMonthly);
            stmt.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now()));


            stmt.executeUpdate();
            System.out.println("=========================================");
            System.out.println("Budget saved to databse successfully!");
            } catch (SQLException e) {
                System.out.println("=========================================");
                System.out.println("Error saving budget: " + e.getMessage());
            }
    }

    private void deleteBudgetFromDatabase(int userID) {
        String sqlCheck = "SELECT * FROM Budget where userID = ?";
        String sqlDelete = "DELETE FROM Budget WHERE userID = ?";


        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement checkStmt = connection.prepareStatement(sqlCheck);
            PreparedStatement deleteStmt = connection.prepareStatement(sqlDelete)) {

            checkStmt.setInt(1, userID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                deleteStmt.setInt(1, userID);
                deleteStmt.executeUpdate();
                System.out.println("=========================================");
                System.out.println("Budget deleted successfully.");
            } else {
                System.out.println("=========================================");
                System.out.println("No budget found for this ID:" + userID);
            }
        }
        catch (SQLException e) {
            System.out.println("=========================================");
            System.out.println("Error deleting budget: " + e.getMessage());
        }
    }

    private double convertCurrency(double amount, String currency) {
        if (currency.equalsIgnoreCase("KHR")) {
            return amount * EXCHANGE_RATE;
        } else if (currency.equalsIgnoreCase("USD")) {
            return amount;  
        } else {
            System.out.println("Unsupported Currency.");
            return amount;
        }
    }

    public boolean userExists(int userID) {
        String sql = "SELECT COUNT(*) FROM Budget WHERE userID = ?";
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            } 
        } catch (SQLException e) {
            System.out.println("Error checking for user ID existence: " + e.getMessage());
        }
        return false;
    }
}

