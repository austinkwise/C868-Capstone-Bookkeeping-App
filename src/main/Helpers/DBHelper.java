package main.Helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Account;
import main.Model.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;

public class DBHelper {
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
        System.out.println(user + "signInUser() in DBHelper");
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

    public static List<Account> getAssetData(int userId) throws SQLException, ClassNotFoundException {
        ObservableList<Account> assetAccountList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "SELECT * FROM accounts WHERE account = Asset Account AND userId = ?;";

        init();
        PreparedStatement pst = conn.prepareStatement(assetQuery);
        pst.setInt(1, userId);
        rs = pst.executeQuery();

        while(rs.next()){
            Integer accountId = rs.getInt("accountId");
            String account = rs.getString("account");
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");
            String accountDescription = rs.getString("accountDescription");
            int archiveAccount = rs.getInt("archiveAccount");
            int userIdDb = rs.getInt("userId");
            assetAccountList.add(new Account(accountId, account, accountType, accountName, accountDescription, archiveAccount, userIdDb));
        }
        conn.close();

        return assetAccountList;
    }
}
