package budget;

public class Budget {
    private int userID;
    private double weeklyBudget;
    private double monthlyBudget;
    private String currency;
    private double convertedWeeklyBudget;
    private double convertedMonthlyBudget;

    public Budget(int userID, double weeklyBudget, double monthlyBudget) {
        this.userID = userID;
        this.weeklyBudget = weeklyBudget;
        this.monthlyBudget = monthlyBudget;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setBudget(double weeklyBudget, double monthlyBudget) {
        this.weeklyBudget = weeklyBudget;
        this.monthlyBudget = monthlyBudget;
        
    }

    public double getWeeklyBudget() {
        return weeklyBudget;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getConvertedWeeklyBudget() {
        return convertedWeeklyBudget;
    }

    public void setConvertedWeeklyBudget(double convertedWeeklyBudget) {
        this.convertedWeeklyBudget = convertedWeeklyBudget;
    }

    public double getConvertedMonthlyBudget() {
        return convertedMonthlyBudget;
    }

    public void setConvertedMonthlyBudget(double convertedMonthlyBudget) {
        this.convertedMonthlyBudget = convertedMonthlyBudget;
    }

    public void editBudget(double newWeekly, double newMonthly) {
        this.weeklyBudget = newWeekly;
        this.monthlyBudget = newMonthly;
    }
    public void deleteBudget() {
        this.weeklyBudget = 0;
        this.monthlyBudget = 0;
        this.currency = "USD";
        System.out.println("Budget for this User ID: " + userID + "has been deleted");
    }
    public String displayBudget() {
        return "User ID: " + userID + "\n" +
        "Weekly Budget: " + weeklyBudget + " " + currency + "\n" +
        "Monthly Budget: " + monthlyBudget + " " + currency + "\n" +
        "Converted Weekly Budget: " + convertedWeeklyBudget + " KHR\n" +
        "Converted Monthly Budget: " + convertedMonthlyBudget + " KHR";
    }
}
