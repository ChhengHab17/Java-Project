package budget;

public class Budget {

    private double weeklyBudget;
    private double monthlyBudget;

    public Budget(double weekly, double monthly) {
        this.weeklyBudget = weekly;
        this.monthlyBudget = monthly;
    } 
    public void setBudget(double weekly, double monthly) {
        this.weeklyBudget = weekly;
        this.monthlyBudget = monthly;
        System.out.println("Budget Set Successfully");
    }
    public boolean isOverWeeklyBudget(double spentAmount) {
        return spentAmount > weeklyBudget;
    }
    public void editBudget(double newWeekly, double newMonthly) {
        this.weeklyBudget = newWeekly;
        this.monthlyBudget = newMonthly;
        System.out.println("Budget Updated Successfully");
    }
    public void deleteBudget() {
        this.weeklyBudget = 0;
        this.monthlyBudget = 0;
        System.out.println("Budget Delete Successfully");
    }
    public void displayBudget() {
        System.out.println("Current Budget: ");
        System.out.println("Weekly Budget: " + weeklyBudget);
        System.out.println("Monthly Budget: " + monthlyBudget);
    }
}

