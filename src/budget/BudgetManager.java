package budget;

public class BudgetManager {
    private Budget budget;

    public BudgetManager() {
        this.budget = new Budget(0, 0);
    }
    public void createBudget(double weekly, double monthly) {
        budget.setBudget(weekly, monthly);
    }
    public void modifyBudget(double newWeekly, double newMonthly) {
        budget.editBudget(newWeekly, newMonthly);
    }
    public void removeBudget() {
        budget.deleteBudget();
    }
    public void showBudget() {
        budget.displayBudget();
    }
}
