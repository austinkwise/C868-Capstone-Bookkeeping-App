package main.Helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Account;
import main.Model.Transaction;
import main.Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class DBConnection {
    /**
     * Server name:remotemysql.com
     * Database name: otiq5otJUM
     * Username: otiq5otJUM
     * Password: YPJAAuQ8vJ
     *
     * @author austinwise
     */
    private static final String databaseName = "otiq5otJUM";
    private static final String DB_URL = "jdbc:mysql://remotemysql.com/" + databaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String user = "otiq5otJUM";
    private static final String pass = "YPJAAuQ8vJ";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static Connection conn;

    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(DB_URL, user, pass);
    }

    public static Connection getConn(){
        return conn;
    }

    public static void closeConn () throws SQLException {
        conn.close();
    }

    /*
    ===============================
    SIGN UP METHODS
    ===============================
     */
    public static void signUpUser(String username, String password, String name, String phone, String email, String orgName) throws SQLException, ClassNotFoundException {
        String statement = "INSERT INTO users (username, password, name, phone, email, organizationName) VALUES (?, ?, ?, ?, ?, ?);";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, username);
        pst.setString(2, password);
        pst.setString(3, name);
        pst.setString(4, phone);
        pst.setString(5, email);
        pst.setString(6, orgName);
        pst.execute();
    }

    /*
    ================================
    SIGN IN METHODS
    ================================
     */
    public static User signInUser(String usernameEmail, String password) throws SQLException, ClassNotFoundException {
        ResultSet rs;
        User user = new User();

        if(usernameEmail.contains("@")){
            String statement = "SELECT * FROM users WHERE email = ? AND password = ?";

            init();
            PreparedStatement pst = conn.prepareStatement((statement));
            pst.setString(1, usernameEmail);
            pst.setString(2, password);
            rs = pst.executeQuery();
        }else{
            String statement = "SELECT * FROM users WHERE username = ? AND password = ?";

            init();
            PreparedStatement pst = conn.prepareStatement((statement));
            pst.setString(1, usernameEmail);
            pst.setString(2, password);
            rs = pst.executeQuery();
        }

        if(rs.next()){
            String passwordHolder = rs.getString("password");
            if(!passwordHolder.contentEquals(password)){
                return null;
            }
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setUserId(rs.getInt("userId"));
            user.setOrgName(rs.getString("organizationName"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
        }
        else{
            return null;
        }
        return user;
    }

    /*
    ================================
    MAIN PAGE METHODS
    ================================
     */
    public static List<Transaction> getTransactionData(int userId) throws SQLException {
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "SELECT * FROM transactions WHERE userId = ?;";

        PreparedStatement pst = getConn().prepareStatement(assetQuery);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            int transactionId = rs.getInt("transactionId");
            String transactionType = rs.getString("transactionType");
            String transactionAccount = rs.getString("transactionAccount");
            LocalDate transactionDate = rs.getDate("transactionDate").toLocalDate();
            String transactionCategory = rs.getString("transactionCategory");
            String transactionAmount = rs.getString("transactionAmount");
            String transactionDescription = rs.getString("transactionDescription");
            transactionList.add(new Transaction(transactionId, transactionType, transactionAccount, transactionDate, transactionCategory, transactionAmount, transactionDescription));
        }
        return transactionList;
    }

    /*
    ================================
    CHART OF ACCOUNTS METHODS
    ================================
     */
    public static List<Account> getAccountData(int userId, int accounts) throws SQLException {
        ObservableList<Account> accountList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "";

        switch (accounts){
            case 1:
                //Asset Table
                assetQuery = "SELECT * FROM accounts WHERE account = 'Asset Account' AND userId = ?;";
                break;
            case 2:
                //Liability Table
                assetQuery = "SELECT * FROM accounts WHERE account = 'Liability Account' AND userId = ?;";
                break;
            case 3:
                //Income Table
                assetQuery = "SELECT * FROM accounts WHERE account = 'Income Account' AND userId = ?;";
                break;
            case 4:
                //Expense Table
                assetQuery = "SELECT * FROM accounts WHERE account = 'Expense Account' AND userId = ?;";
                break;
            case 5:
                //Equity Table
                assetQuery = "SELECT * FROM accounts WHERE account = 'Equity Account' AND userId = ?;";
            default:
                break;
        }

        PreparedStatement pst = getConn().prepareStatement(assetQuery);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountId = rs.getString("accountId");
            String account = rs.getString("account");
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");
            String accountDescription = rs.getString("accountDescription");

            int archive = Integer.parseInt(rs.getString("archiveAccount"));
            String archiveAccount;
            if (archive == 1){
                archiveAccount = "Archived";
            }else{
                archiveAccount = "Active";
            }

            String userIdDb = rs.getString("userId");
            accountList.add(new Account(accountId, account, accountType, accountName, accountDescription, archiveAccount, userIdDb));
        }

        return accountList;
    }

    public static List<Account> getAllAccountData(int userId) throws SQLException {
        ObservableList<Account> accountList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "SELECT * FROM accounts WHERE userId = ?;";

        PreparedStatement pst = getConn().prepareStatement(assetQuery);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountId = rs.getString("accountId");
            String account = rs.getString("account");
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");
            String accountDescription = rs.getString("accountDescription");

            int archive = Integer.parseInt(rs.getString("archiveAccount"));
            String archiveAccount;
            if (archive == 1){
                archiveAccount = "Archived";
            }else{
                archiveAccount = "Active";
            }

            String userIdDb = rs.getString("userId");
            accountList.add(new Account(accountId, account, accountType, accountName, accountDescription, archiveAccount, userIdDb));
        }
        return accountList;
    }

    /*
    ================================
    ACCOUNT DETAIL METHODS
    ================================
     */
    public static boolean saveNewAccount(String accounts, String accountType, String accountName, int accountId, String description, int archiveAccount, int userId) throws SQLException, ClassNotFoundException{
        String statement = "INSERT INTO accounts (accountId, account, accountType, accountName, accountDescription, archiveAccount, userId) VALUES (?, ?, ?, ?, ?, ?, ?);";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setInt(1, accountId);
        pst.setString(2, accounts);
        pst.setString(3, accountType);
        pst.setString(4, accountName);
        pst.setString(5, description);
        pst.setInt(6, archiveAccount);
        pst.setInt(7, userId);
        pst.execute();

        return true;
    }

    public static boolean updateAccount(String accounts, String accountType, String accountName, int accountId, String description, int archiveAccount, int userId) throws SQLException, ClassNotFoundException {
        String statement = "UPDATE accounts SET accountId = ?, account = ?, accountType = ?, accountName = ?, accountDescription = ?, archiveAccount = ? WHERE accountId = ? AND userId = ?";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setInt(1, accountId);
        pst.setString(2, accounts);
        pst.setString(3, accountType);
        pst.setString(4, accountName);
        pst.setString(5, description);
        pst.setInt(6, archiveAccount);
        pst.setInt(7, accountId);
        pst.setInt(8, userId);
        pst.execute();

        //String transactionStmt = "UPDATE transactions SET ";

        return true;
    }

    /*
    ================================
    TRANSACTION DETAIL METHODS
    ================================
     */
    public static List<String> getAccountNames(int userId) throws SQLException {
        ObservableList<String> accountNames = FXCollections.observableArrayList();
        ResultSet rs;

        String query = "SELECT accountName, archiveAccount FROM accounts WHERE userId = ? AND (account = 'Asset Account' OR account = 'Liability Account' OR account = 'Equity Account');";

        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            int archive = rs.getInt("archiveAccount");
            if (archive != 1){
                String accountName = rs.getString("accountName");

                accountNames.add(accountName);
            }
        }
        return accountNames;
    }

    public static List<String> getCategories(int userId, int category) throws SQLException {
        ObservableList<String> categories = FXCollections.observableArrayList();
        ResultSet rs;

        String query = "";

        switch(category){
            case 1:
                //Income
                query = "SELECT accountName, accountType FROM accounts WHERE userId = ? AND (account = 'Income Account' OR account = 'Equity Account');";
                break;
            case 2:
                //Expense
                query = "SELECT accountName, accountType FROM accounts WHERE userId = ? AND (account = 'Expense Account' OR account = 'Equity Account');";
                break;
            default:
                break;
        }

        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountName = rs.getString("accountName");

            categories.add(accountName);
        }

        return categories;
    }

    public static void deleteTransaction(int transactionId) throws SQLException {
        String deleteQuery = "DELETE FROM transactions WHERE transactionId = ?;";
        PreparedStatement pst = getConn().prepareStatement(deleteQuery);
        pst.setInt(1, transactionId);
        pst.executeUpdate();
    }


    public static boolean saveNewTransaction(String transactionType, String transactionAccount, LocalDate transactionDate, String transactionCategory, String transactionAmount, String transactionDescription, int userId) throws SQLException, ClassNotFoundException {
        String statement = "INSERT INTO transactions (transactionType, transactionAccount, transactionDate, transactionCategory, transactionAmount, transactionDescription, userId) VALUES (?, ?, ?, ?, ?, ?, ?);";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, transactionType);
        pst.setString(2, transactionAccount);
        pst.setDate(3, java.sql.Date.valueOf(transactionDate));
        pst.setString(4, transactionCategory);
        pst.setString(5, transactionAmount);
        pst.setString(6, transactionDescription);
        pst.setInt(7, userId);
        pst.execute();

        return true;
    }

    public static boolean updateTransaction(String transactionType, String transactionAccount, LocalDate transactionDate, String transactionCategory, String transactionAmount, String transactionDescription, int transId) throws SQLException, ClassNotFoundException {
        String statement = "UPDATE transactions SET transactionType = ?, transactionAccount = ?, transactionDate = ?, transactionCategory = ?, transactionAmount = ?, transactionDescription = ? WHERE transactionId = ?;";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, transactionType);
        pst.setString(2, transactionAccount);
        pst.setDate(3, java.sql.Date.valueOf(transactionDate));
        pst.setString(4, transactionCategory);
        pst.setString(5, transactionAmount);
        pst.setString(6, transactionDescription);
        pst.setInt(7, transId);
        pst.execute();

        return true;
    }

    /*
    ================================
    PROFILE METHODS
    ================================
     */
    public static boolean updateUser(String username, String name, String phone, String email, String orgName, int userId) throws SQLException, ClassNotFoundException {
        String statement = "UPDATE users SET username = ?, name = ?, phone = ?, email = ?, organizationName = ? WHERE userId = ?";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, username);
        pst.setString(2, name);
        pst.setString(3, phone);
        pst.setString(4, email);
        pst.setString(5, orgName);
        pst.setInt(6, userId);
        pst.execute();

        return true;
    }

    /*
    ==========================
    REPORTS METHODS
    ==========================
     */
    public static Integer getBalanceSheetData(int userId, int dataSwitch, String date) throws SQLException {
        String incomeQuery = "";
        String expenseQuery = "";

        switch(dataSwitch){
            case 1:
                //Cash and Bank
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Cash and Bank' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Cash and Bank' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 2:
                //Other Current Assets
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Other Current Asset' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Other Current Asset' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 3:
                //Long Term Assets
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Other Long Term Asset' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Other Long Term Asset' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 4:
                //Current Liabilities
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Current Liability' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Current Liability' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 5:
                //Long term liabilities
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Other Long Term Liability' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Other Long Term Liability' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 6:
                //other equity
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Other Equity' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Other Equity' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            case 7:
                //retained earnings
                incomeQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Income' AND a.accountType = 'Retained Earnings: Profit' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                expenseQuery = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE transactions.transactionType = 'Expense' AND a.accountType = 'Retained Earnings: Profit' AND transactions.transactionDate < '" + date + "' AND transactions.userId = ?;";
                break;
            default:
                break;
        }

        ResultSet rs1;
        PreparedStatement pst1 = getConn().prepareStatement(incomeQuery);
        pst1.setInt(1, userId);
        rs1 = pst1.executeQuery();

        int income = 0;
        if(rs1.next()){
            income = rs1.getInt("amount");
        }

        //For testing
        //System.out.println(pst1);
        //System.out.println(income);

        ResultSet rs2;
        PreparedStatement pst2 = getConn().prepareStatement(expenseQuery);
        pst2.setInt(1, userId);
        rs2 = pst2.executeQuery();

        int expense = 0;
        if(rs2.next()){
            expense = rs2.getInt("amount");
        }

        //For testing
        //System.out.println(pst2);
        //System.out.println(expense);

        int amount;

        if(dataSwitch <= 3){
            amount = income - expense;
        }else{
            amount = expense - income;
        }
        return amount;
    }

    public static Integer getIncomeStatementData(int userId, String start, String end, int dataSwitch) throws SQLException {
        String query = "";

        switch(dataSwitch){
            case 1:
                //Income
                query = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionCategory = a.accountName WHERE a.accountType = 'Income' AND (transactions.transactionDate BETWEEN '" + start + "' AND '" + end + "') AND transactions.userId = ?";
                break;
            case 2:
                //Cost of goods sold
                query = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionCategory = a.accountName WHERE a.accountType = 'Cost of Goods Sold' AND (transactions.transactionDate BETWEEN '" + start + "' AND '" + end + "') AND transactions.userId = ?";
                break;
            case 3:
                //Operating expenses
                query = "SELECT SUM(transactionAmount) AS 'amount' FROM transactions INNER JOIN accounts a ON transactions.transactionCategory = a.accountName WHERE a.accountType = 'Operation Expense' AND (transactions.transactionDate BETWEEN '" + start + "' AND '" + end + "') AND transactions.userId = ?";
                break;
        }
        ResultSet rs;
        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);

        rs = pst.executeQuery();
        int amount = 0;

        if(rs.next()){
            amount = rs.getInt("amount");
        }

        //System.out.println(pst);
        //System.out.println(amount);

        return amount;
    }
}
