package main;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import main.Helpers.DBConnection;
import main.Model.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

    private void populateBalanceSheet(){
        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);

        System.out.println(today);
        
        int cashAndBank = 1;
        int otherCurrentAssets = 1;
        int longTermAssets = 1;
        int totalAssets = cashAndBank + otherCurrentAssets + longTermAssets;

        int currentLiabilities = 1;
        int longTermLiabilities = 1;
        int totalLiabilities = currentLiabilities + longTermLiabilities;

        int otherEquity = 1;
        int retainedEarnings = 1;
        int totalEquity = otherEquity + retainedEarnings;

        cashAndBankLabel.setText(String.valueOf(cashAndBank));
        otherCurrentAssetsLabel.setText(String.valueOf(otherCurrentAssets));
        longTermAssetsLabel.setText(String.valueOf(longTermAssets));
        totalAssetsLabel.setText(String.valueOf(totalAssets));

        currentLiabilitiesLabel.setText(String.valueOf(currentLiabilities));
        longTermLiabilitiesLabel.setText(String.valueOf(longTermLiabilities));
        totalLiabilitiesLabel.setText(String.valueOf(totalLiabilities));

        otherEquityLabel.setText(String.valueOf(otherEquity));
        retainedEarningsLabel.setText(String.valueOf(retainedEarnings));
        totalEquityLabel.setText(String.valueOf(totalEquity));
    }

    @FXML private void updateBalanceSheet(){

    }
}
