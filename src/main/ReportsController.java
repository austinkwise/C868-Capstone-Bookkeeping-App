package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import main.Helpers.DBConnection;
import main.Model.User;

import java.sql.SQLException;
import java.util.Calendar;


public class ReportsController {
    private static User currentUser;
    private static DBConnection myDb;

    //Income Statement Labels
    @FXML private Label incomeLabel;
    @FXML private Label cogsLabel;
    @FXML private Label grossProfitLabel;
    @FXML private Label operatingExpensesLabel;
    @FXML private Label netProfitLabel;
    @FXML private DatePicker fromDp;
    @FXML private DatePicker toDp;

    //Balance Sheet Labels
    @FXML private DatePicker datePicker;
    @FXML private Label cashAndBankLabel;
    @FXML private Label otherCurrentAssetsLabel;
    @FXML private Label longTermAssetsLabel;
    @FXML private Label totalAssetsLabel;

    @FXML private Label currentLiabilitiesLabel;
    @FXML private Label longTermLiabilitiesLabel;
    @FXML private Label totalLiabilitiesLabel;

    @FXML private Label otherEquityLabel;
    @FXML private Label retainedEarningsLabel;
    @FXML private Label totalEquityLabel;

    public void setupReports(User currentUser) throws SQLException {
        this.currentUser = currentUser;

        populateIncomeStatement();
        populateBalanceSheet();
    }

    private void populateIncomeStatement() throws SQLException {
        Calendar now = Calendar.getInstance();

        String start = now.get(Calendar.YEAR) + "-01-01";
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);

        System.out.println(start);
        System.out.println(today);

        int initialIncome = myDb.getIncome(currentUser.getUserId(), start,  today);
        int initialCogs = 1;
        int grossProfit = initialIncome - initialCogs;
        int operatingExpenses = myDb.getOperatingExpenses(currentUser.getUserId(), start, today);;
        int netProfit = grossProfit - operatingExpenses;

        incomeLabel.setText(String.valueOf(initialIncome));
        cogsLabel.setText(String.valueOf(initialCogs));
        grossProfitLabel.setText(String.valueOf(grossProfit));
        operatingExpensesLabel.setText(String.valueOf(operatingExpenses));
        netProfitLabel.setText(String.valueOf(netProfit));
    }

    @FXML private void updateIncomeStatement() throws SQLException {
        String from = fromDp.getValue().toString();
        String to = toDp.getValue().toString();

        System.out.println(from);
        System.out.println(to);

        int income = myDb.getIncome(currentUser.getUserId(), from,  to);
        int cogs = myDb.getCogs(currentUser.getUserId(), from, to);
        int grossProfit = income - cogs;
        int operatingExpenses = myDb.getOperatingExpenses(currentUser.getUserId(), from, to);
        int netProfit = grossProfit - operatingExpenses;

        incomeLabel.setText(String.valueOf(income));
        cogsLabel.setText(String.valueOf(cogs));
        grossProfitLabel.setText(String.valueOf(grossProfit));
        operatingExpensesLabel.setText(String.valueOf(operatingExpenses));
        netProfitLabel.setText(String.valueOf(netProfit));
    }

    private void populateBalanceSheet() throws SQLException {
        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);

        System.out.println(today);
        
        int cashAndBankIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Cash and Bank'", "'Income'", today);
        int cashAndBankExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Cash and Bank'", "'Expense'", today);
        int cashAndBank = cashAndBankIncome - cashAndBankExpense;
        int otherCurrentAssetsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Current Assets'", "'Income'", today);
        int otherCurrentAssetsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Current Assets'", "'Expense'", today);
        int otherCurrentAssets = otherCurrentAssetsIncome - otherCurrentAssetsExpense;
        int longTermAssetsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Long Term Assets'", "'Income'", today);
        int longTermAssetsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Long Term Assets'", "'Expense'", today);
        int longTermAssets = longTermAssetsIncome - longTermAssetsExpense;

        int totalAssets = cashAndBank + otherCurrentAssets + longTermAssets;

        int currentLiabilitiesIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Current Liabilities'", "'Income'", today);
        int currentLiabilitiesExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Current Liabilities'", "'Expense'", today);
        int currentLiabilities = currentLiabilitiesIncome - currentLiabilitiesExpense;
        int otherLongTermLiabilitiesIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Long Term Liability'", "'Income'", today);
        int otherLongTermLiabilitiesExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Long Term Liability'", "'Expense'", today);
        int otherLongTermLiabilities = otherLongTermLiabilitiesIncome - otherLongTermLiabilitiesExpense;

        int totalLiabilities = currentLiabilities + otherLongTermLiabilities;

        int otherEquityIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Equity'", "'Income'", today);
        int otherEquityExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Equity'", "'Expense'", today);
        int otherEquity = otherEquityIncome - otherEquityExpense;
        int retainedEarningsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Retained Earnings: Profit'", "'Income'", today);
        int retainedEarningsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Retained Earnings: Profit'", "'Expense'", today);
        int retainedEarnings = retainedEarningsIncome - retainedEarningsExpense;

        int totalEquity = otherEquity + retainedEarnings;

        cashAndBankLabel.setText(String.valueOf(cashAndBank));
        otherCurrentAssetsLabel.setText(String.valueOf(otherCurrentAssets));
        longTermAssetsLabel.setText(String.valueOf(longTermAssets));
        totalAssetsLabel.setText(String.valueOf(totalAssets));

        currentLiabilitiesLabel.setText(String.valueOf(currentLiabilities));
        longTermLiabilitiesLabel.setText(String.valueOf(otherLongTermLiabilities));
        totalLiabilitiesLabel.setText(String.valueOf(totalLiabilities));

        otherEquityLabel.setText(String.valueOf(otherEquity));
        retainedEarningsLabel.setText(String.valueOf(retainedEarnings));
        totalEquityLabel.setText(String.valueOf(totalEquity));
    }

    @FXML private void updateBalanceSheet() throws SQLException {
        String date = datePicker.getValue().toString();

        System.out.println(date);

        int cashAndBankIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Cash and Bank'", "'Income'", date);
        int cashAndBankExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Cash and Bank'", "'Expense'", date);
        int cashAndBank = cashAndBankIncome - cashAndBankExpense;
        int otherCurrentAssetsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Current Assets'", "'Income'", date);
        int otherCurrentAssetsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Current Assets'", "'Expense'", date);
        int otherCurrentAssets = otherCurrentAssetsIncome - otherCurrentAssetsExpense;
        int longTermAssetsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Long Term Assets'", "'Income'", date);
        int longTermAssetsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Long Term Assets'", "'Expense'", date);
        int longTermAssets = longTermAssetsIncome - longTermAssetsExpense;

        int totalAssets = cashAndBank + otherCurrentAssets + longTermAssets;

        int currentLiabilitiesIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Current Liabilities'", "'Income'", date);
        int currentLiabilitiesExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Current Liabilities'", "'Expense'", date);
        int currentLiabilities = currentLiabilitiesIncome - currentLiabilitiesExpense;
        int otherLongTermLiabilitiesIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Long Term Liability'", "'Income'", date);
        int otherLongTermLiabilitiesExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Long Term Liability'", "'Expense'", date);
        int otherLongTermLiabilities = otherLongTermLiabilitiesIncome - otherLongTermLiabilitiesExpense;

        int totalLiabilities = currentLiabilities + otherLongTermLiabilities;

        int otherEquityIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Equity'", "'Income'", date);
        int otherEquityExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Other Equity'", "'Expense'", date);
        int otherEquity = otherEquityIncome - otherEquityExpense;
        int retainedEarningsIncome = myDb.getBalanceSheetData(currentUser.getUserId(), "'Retained Earnings: Profit'", "'Income'", date);
        int retainedEarningsExpense = myDb.getBalanceSheetData(currentUser.getUserId(), "'Retained Earnings: Profit'", "'Expense'", date);
        int retainedEarnings = retainedEarningsIncome - retainedEarningsExpense;

        int totalEquity = otherEquity + retainedEarnings;

        cashAndBankLabel.setText(String.valueOf(cashAndBank));
        otherCurrentAssetsLabel.setText(String.valueOf(otherCurrentAssets));
        longTermAssetsLabel.setText(String.valueOf(longTermAssets));
        totalAssetsLabel.setText(String.valueOf(totalAssets));

        currentLiabilitiesLabel.setText(String.valueOf(currentLiabilities));
        longTermLiabilitiesLabel.setText(String.valueOf(otherLongTermLiabilities));
        totalLiabilitiesLabel.setText(String.valueOf(totalLiabilities));

        otherEquityLabel.setText(String.valueOf(otherEquity));
        retainedEarningsLabel.setText(String.valueOf(retainedEarnings));
        totalEquityLabel.setText(String.valueOf(totalEquity));
    }
}
