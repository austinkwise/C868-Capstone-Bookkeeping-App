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
    //@FXML private Label cashAndBankLabel;
    //@FXML private Label otherCurrentAssetsLabel;
    //@FXML private Label longTermAssetsLabel;
    @FXML private Label totalAssetsLabel;

    //@FXML private Label currentLiabilitiesLabel;
    //@FXML private Label longTermLiabilitiesLabel;
    @FXML private Label totalLiabilitiesLabel;

    //@FXML private Label otherEquityLabel;
    //@FXML private Label retainedEarningsLabel;
    @FXML private Label totalEquityLabel;

    @FXML private Label dateHeadLbl;

    public void setupReports(User currentUser) throws SQLException {
        this.currentUser = currentUser;

        populateBalanceSheet();
        populateIncomeStatement();
    }



    private void populateBalanceSheet() throws SQLException {
        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);
        //System.out.println(today);
        dateHeadLbl.setText(today);

        //Getting Data
        int cashAndBank = myDb.getBalanceSheetData(currentUser.getUserId(), 1, today);
        int otherCurrentAssets = myDb.getBalanceSheetData(currentUser.getUserId(), 2, today);
        int longTermAssets = myDb.getBalanceSheetData(currentUser.getUserId(), 3, today);

        int totalAssets = cashAndBank + otherCurrentAssets + longTermAssets;

        int currentLiabilities = myDb.getBalanceSheetData(currentUser.getUserId(), 4, today);
        int otherLongTermLiabilities = myDb.getBalanceSheetData(currentUser.getUserId(), 5, today);

        int totalLiabilities = currentLiabilities + otherLongTermLiabilities;

        //int otherEquity = myDb.getBalanceSheetData(currentUser.getUserId(), 6, today);
        //int retainedEarnings = myDb.getBalanceSheetData(currentUser.getUserId(), 7, today);

        int totalEquity = totalAssets - totalLiabilities;

        //Setting Data
        //cashAndBankLabel.setText(String.valueOf(cashAndBank));
        //otherCurrentAssetsLabel.setText(String.valueOf(otherCurrentAssets));
        //longTermAssetsLabel.setText(String.valueOf(longTermAssets));
        totalAssetsLabel.setText(String.valueOf(totalAssets));

        //currentLiabilitiesLabel.setText(String.valueOf(currentLiabilities));
        //longTermLiabilitiesLabel.setText(String.valueOf(otherLongTermLiabilities));
        totalLiabilitiesLabel.setText(String.valueOf(totalLiabilities));

        //otherEquityLabel.setText(String.valueOf(otherEquity));
        //retainedEarningsLabel.setText(String.valueOf(retainedEarnings));
        totalEquityLabel.setText(String.valueOf(totalEquity));
    }


    @FXML private void updateBalanceSheet() throws SQLException {
        String date = datePicker.getValue().toString();
        System.out.println(date);
        dateHeadLbl.setText(date);

        //Getting Data
        int cashAndBank = myDb.getBalanceSheetData(currentUser.getUserId(), 1, date);
        int otherCurrentAssets = myDb.getBalanceSheetData(currentUser.getUserId(), 2, date);
        int longTermAssets = myDb.getBalanceSheetData(currentUser.getUserId(), 3, date);

        int totalAssets = cashAndBank + otherCurrentAssets + longTermAssets;

        int currentLiabilities = myDb.getBalanceSheetData(currentUser.getUserId(), 4, date);
        int otherLongTermLiabilities = myDb.getBalanceSheetData(currentUser.getUserId(), 5, date);

        int totalLiabilities = currentLiabilities + otherLongTermLiabilities;

        //int otherEquity = myDb.getBalanceSheetData(currentUser.getUserId(), 6, date);
        //int retainedEarnings = myDb.getBalanceSheetData(currentUser.getUserId(), 7, date);

        int totalEquity = totalAssets - totalLiabilities;

        //Setting Data
        //cashAndBankLabel.setText(String.valueOf(cashAndBank));
        //otherCurrentAssetsLabel.setText(String.valueOf(otherCurrentAssets));
        //longTermAssetsLabel.setText(String.valueOf(longTermAssets));
        totalAssetsLabel.setText(String.valueOf(totalAssets));

        //currentLiabilitiesLabel.setText(String.valueOf(currentLiabilities));
        //longTermLiabilitiesLabel.setText(String.valueOf(otherLongTermLiabilities));
        totalLiabilitiesLabel.setText(String.valueOf(totalLiabilities));

        //otherEquityLabel.setText(String.valueOf(otherEquity));
        //retainedEarningsLabel.setText(String.valueOf(retainedEarnings));
        totalEquityLabel.setText(String.valueOf(totalEquity));
    }

    private void populateIncomeStatement() throws SQLException {
        Calendar now = Calendar.getInstance();

        String start = now.get(Calendar.YEAR) + "-01-01";
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE);

        //System.out.println(start);
        //System.out.println(today);

        //Getting Data
        int income = myDb.getIncomeStatementData(currentUser.getUserId(), start, today, 1);
        int cogs = myDb.getIncomeStatementData(currentUser.getUserId(), start, today, 2);

        int grossProfit = income - cogs;

        int operatingExpenses = myDb.getIncomeStatementData(currentUser.getUserId(), start, today, 3);

        int netProfit = grossProfit - operatingExpenses;

        //Setting data
        incomeLabel.setText(String.valueOf(income));
        cogsLabel.setText(String.valueOf(cogs));
        grossProfitLabel.setText(String.valueOf(grossProfit));
        operatingExpensesLabel.setText(String.valueOf(operatingExpenses));
        netProfitLabel.setText(String.valueOf(netProfit));

    }

    @FXML private void updateIncomeStatement() throws SQLException {
        String from = fromDp.getValue().toString();
        String to = toDp.getValue().toString();

        //System.out.println(from);
        //System.out.println(to);

        //Getting Data
        int income = myDb.getIncomeStatementData(currentUser.getUserId(), from, to, 1);
        int cogs = myDb.getIncomeStatementData(currentUser.getUserId(), from, to, 2);

        int grossProfit = income - cogs;

        int operatingExpenses = myDb.getIncomeStatementData(currentUser.getUserId(), from, to, 3);

        int netProfit = grossProfit - operatingExpenses;

        //Setting data
        incomeLabel.setText(String.valueOf(income));
        cogsLabel.setText(String.valueOf(cogs));
        grossProfitLabel.setText(String.valueOf(grossProfit));
        operatingExpensesLabel.setText(String.valueOf(operatingExpenses));
        netProfitLabel.setText(String.valueOf(netProfit));

    }
}
