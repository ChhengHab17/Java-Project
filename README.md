# Track expenses application
The track expenses applicaton is an application that tracks our expenses monthly by manually input the expenese based on the categories and it will genereate a report of the expenses monthly for users.

## Library
To use our feature of generate report you need to install this library and add it to your Java project<br>
[Generate Reports library](commons-logging-1.2.jar)<br>
[Generate Reports library](fontbox-2.0.27.jar)<br>
[Generate Reports library](pdfbox-2.0.27.jar)<br>
To use our database and connect to use it install this file and add it to the Java project<br>
[Database connector](mysql-connector-j-9.2.0.jar)
## Core Features
1. User managaement
   - Register
   - Login
2. Expense
   - Category
   - Amount
   - Date
   - Display
3. ExpenseManager
   - Add
   - View
   - Total
   - Delete
   - Filter
   - Edit
4. Budget
   - SetBudget
   - GetBudget
   - CheckExceeded
5. Budget management
   - Budget
   - Check
   - Remaining
6. ReportGenerator
   - generateExpenseReport
   - generateGraphicalReport
   - exportToCSV
7. DatabaseConnector 
   - connect
   - insertExpense
   - getAllExpenses
   - deleteExpense
   - closeConnection
8. ExpenseTrackerApp 
   - main
   - showMainMenu
   - handleUserInput

## Responsibilities
- Sophal Chanrat (Project leader)
   - Expense managenment
   - Expense
- Te Chhenghab
   - ExpenseTrackerApp
   - ReportGenerator
- Sithav Seavthean
   - User management
   - DatabaseConnector
- Phon Sokleaphea
   - Budget management
   - Budget
