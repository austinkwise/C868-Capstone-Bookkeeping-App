package main.Helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Account;
import main.Model.Transaction;
import main.Model.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
        conn.close();
    }

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
        conn.close();

        return true;
    }

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

        while(rs.next()){
            String passwordHolder = rs.getString("password");
            if(passwordHolder.contentEquals(password)){
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserId(rs.getInt("userId"));
                user.setOrgName(rs.getString("organizationName"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
            }else{
                return null;
            }
        }
        System.out.println(user + "signInUser() in DBConnection");
        return user;
    }

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
        conn.close();

        return true;
    }

    public static boolean updateAccount(String accounts, String accountType, String accountName, int accountId, String description, int archiveAccount, int userId) throws SQLException, ClassNotFoundException {
        String statement = "UPDATE account SET accountId = ?, account = ?, accountType = ?, accountName = ?, accountDescription = ?, archiveAccount = ? WHERE accountId = ? AND userId = ?";

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
        conn.close();

        return true;
    }

    public static List<Account> getAccountData(int userId, int accounts) throws SQLException, ClassNotFoundException {
        ObservableList<Account> accountList = FXCollections.observableArrayList();
        ResultSet rs;

        String accountSwitch = "";

        switch (accounts){
            case 1:
                //Asset Table
                accountSwitch = "'Asset Account'";
                break;
            case 2:
                //Liability Table
                accountSwitch = "'Liability Account'";
                break;
            case 3:
                //Income Table
                accountSwitch = "'Income Account'";
                break;
            case 4:
                //Expense Table
                accountSwitch = "'Expense Account'";
                break;
            case 5:
                //Equity Table
                accountSwitch = "'Equity Account'";
            default:
                break;
        }

        String assetQuery = "SELECT * FROM accounts WHERE account = ? AND userId = ?;";

        PreparedStatement pst = getConn().prepareStatement(assetQuery);
        pst.setString(1, accountSwitch);
        pst.setInt(2, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountId = rs.getString("accountId");
            String account = rs.getString("account");
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");
            String accountDescription = rs.getString("accountDescription");
            String archiveAccount = rs.getString("archiveAccount");
            String userIdDb = rs.getString("userId");
            accountList.add(new Account(accountId, account, accountType, accountName, accountDescription, archiveAccount, userIdDb));
        }
        return accountList;
    }

    public static boolean saveNewTransaction(String transactionType, String transactionAccount, LocalDateTime transactionDate, String transactionCategory, String transactionAmount, String transactionDescription, int userId) throws SQLException, ClassNotFoundException {
        String statement = "INSERT INTO transactions (transactionType, transactionAccount, transactionDate, transactionCategory, transactionAmount, userId) VALUES (?, ?, ?, ?, ?, ?, ?);";

        init();
        PreparedStatement pst = conn.prepareStatement(statement);
        pst.setString(1, transactionType);
        pst.setString(2, transactionAccount);
        pst.setTimestamp(3, Timestamp.valueOf(transactionDate));
        pst.setString(4, transactionCategory);
        pst.setString(5, transactionAmount);
        pst.setString(6, transactionDescription);
        pst.setInt(7, userId);
        pst.execute();
        conn.close();

        return true;
    }

    public static List<String> getAccountNames(int userId) throws SQLException {
        ObservableList<String> accountNames = FXCollections.observableArrayList();
        ResultSet rs;

        String query = "SELECT accountName, accountType FROM accounts (WHERE userId = ?) AND (account = 'Asset Account' OR account = 'Liability Account' OR account = 'Equity Account');";

        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");

            String accountAdd = accountName + " - (" + accountType + ")";

            accountNames.add(accountAdd);
        }
        return accountNames;

    }

    public static List<String> getCategories(int userId, String account) throws SQLException {
        ObservableList<String> categories = FXCollections.observableArrayList();
        ResultSet rs;

        String query = "SELECT accountName, accountType FROM accounts WHERE (userId = ?) AND (account = ?);";

        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);
        pst.setString(2, account);

        rs = pst.executeQuery();

        while(rs.next()){
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");

            String accountAdd = accountName + " - (" + accountType + ")";

            categories.add(accountAdd);
        }

        return categories;
    }

    public static List<Transaction> getTransactionData(int userId) throws SQLException {
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "SELECT * FROM transactions WHERE userId = ?;";

        PreparedStatement pst = getConn().prepareStatement(assetQuery);
        pst.setInt(2, userId);

        rs = pst.executeQuery();

        while(rs.next()){
            int transactionId = rs.getInt("transactionId");
            String transactionType = rs.getString("transactionType");
            String transactionAccount = rs.getString("transactionAccount");
            LocalDateTime transactionDate = rs.getTimestamp("transactionDate").toLocalDateTime();
            String transactionCategory = rs.getString("transactionCategory");
            String transactionAmount = rs.getString("transactionAmount");
            String transactionDescription = rs.getString("transactionDescription");
            transactionList.add(new Transaction(transactionId, transactionType, transactionAccount, transactionDate, transactionCategory, transactionAmount, transactionDescription));
        }
        return transactionList;

    }

    public void deleteTransaction(int userId, int transactionId) throws SQLException {
        String deleteQuery = "DELETE transaction.* FROM transactions WHERE transactionId = ? AND userId = ?";
        PreparedStatement pst = getConn().prepareStatement(deleteQuery);
        pst.setInt(1, transactionId);
        pst.setInt(2, userId);
        pst.executeUpdate();
    }

    public Integer getIncome(int userId, String start, String end) throws SQLException {
        Integer totalIncome = 0;
        ResultSet rs;

        String incomeQuery = "USE otiq5otJUM;\n" +
                "SELECT SUM(transactionAmount) AS 'Income'\n" +
                "FROM accounts, transactions\n" +
                "INNER JOIN accounts a ON transactions.transactionAccount = a.accountName\n" +
                "WHERE (transactions.userId = ?)\n" +
                "AND (accounts.account = 'Income Account')\n" +
                "AND (transactions.transactionDate BETWEEN (?) AND (?));";

        PreparedStatement pst = getConn().prepareStatement(incomeQuery);
        pst.setInt(1, userId);
        pst.setString(2, start);
        pst.setString(3, end);

        rs = pst.executeQuery();

        totalIncome = rs.getInt("Income");
        return totalIncome;
    }

    public Integer getOperatingExpenses(int userId, String start, String end) throws SQLException {
        Integer totalOperatingExpenses = 0;
        ResultSet rs;

        String expenseQuery = "SELECT SUM(transactionAmount) AS 'Operating Expense' FROM accounts, transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE (transactions.userId = ?) AND (accounts.accountType = ('Operating Expense' OR 'Uncategorized Income')) AND (transactions.transactionDate BETWEEN (?) AND (?))";

        PreparedStatement pst = getConn().prepareStatement(expenseQuery);
        pst.setInt(1, userId);
        pst.setString(2, start);
        pst.setString(3, end);

        rs = pst.executeQuery();

        totalOperatingExpenses = rs.getInt("Operating Expense");
        return totalOperatingExpenses;
    }

    public Integer getCogs(int userId, String start, String end) throws SQLException {
        Integer totalCogs = 0;
        ResultSet rs;

        String cogsQuery = "SELECT SUM(transactionAmount) AS 'COGS' FROM accounts, transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE (transactions.userId = ?) AND (accounts.accountType = 'Cost of Goods Sold') AND (transactions.transactionDate BETWEEN (?) AND (?))";

        PreparedStatement pst = getConn().prepareStatement(cogsQuery);
        pst.setInt(1, userId);
        pst.setString(2, start);
        pst.setString(3, end);

        rs = pst.executeQuery();

        totalCogs = rs.getInt("COGS");
        return totalCogs;
    }

    public Integer getAssetData(int userId, String accountType, String transactionType, String date) throws SQLException {
        int assets = 0;
        ResultSet rs;

        String query = "SELECT SUM(transactionAmount) AS 'Asset Income' FROM accounts, transactions INNER JOIN accounts a ON transactions.transactionAccount = a.accountName WHERE (transactions.userId = ?) AND (accounts.accountType = ?) AND (transactions.transactionType = ?) AND (transactions.transactionDate < (?))";


        PreparedStatement pst = getConn().prepareStatement(query);
        pst.setInt(1, userId);
        pst.setString(2, accountType);
        pst.setString(3, transactionType);
        pst.setString(4, date);

        rs = pst.executeQuery();

        assets = rs.getInt("Asset Income");
        return assets;
    }



    public static Connection getConn(){
        return conn;
    }

    public static void closeConn () throws SQLException {
        conn.close();
    }
}
