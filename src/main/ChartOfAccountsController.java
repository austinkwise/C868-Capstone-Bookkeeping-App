package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers.DBConnection;
import main.Model.Account;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class ChartOfAccountsController {
    private static User currentUser;
    private static Bookkeeper myBk;
    private static Account accountModSelected;

    @FXML private TableView<Account> accountsTable;
    @FXML private TableColumn<Account, String> idColumn;
    @FXML private TableColumn<Account, String> accountColumn;
    @FXML private TableColumn<Account, String> accountTypeColumn;
    @FXML private TableColumn<Account, String> nameColumn;
    @FXML private TableColumn<Account, String> descriptionColumn;
    @FXML private TableColumn<Account, String> statusColumn;

    @FXML private ComboBox<String> accountsCb;

    ObservableList<String> accounts = FXCollections.observableArrayList("All Accounts", "Asset Accounts", "Liability Accounts", "Income Accounts", "Expense Accounts", "Equity Accounts");

    public void setupChartOfAccounts(User user, Bookkeeper bookkeeper) throws SQLException {
        currentUser = user;
        myBk = bookkeeper;

        System.out.println("Problem 1: Setting ComboBox items");
        accountsCb.setItems(accounts);
        System.out.println("In accountsCb: " + accountsCb.getItems().get(0));
        System.out.println("In accountsCb: " + accountsCb.getItems().get(1));

        populateTables();
    }

    @FXML private void addAccountClick() throws IOException{
        myBk.showAccountDetail();
    }

    @FXML private void editAccountClick() throws IOException{
        setSelectedAccount(accountsTable.getSelectionModel().getSelectedItem());

        if(accountModSelected == null){
            System.out.println("pick an account");
        }else{
            myBk.showAccountDetail();
        }
    }

    @FXML private void updateTableClick() throws SQLException {
        int idx = accountsCb.getSelectionModel().getSelectedIndex();

        if(idx != 0){
            idColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
            accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
            accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

            accountsTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), idx));
        }else{
            populateTables();
        }
    }

    private void populateTables() throws SQLException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        accountsTable.getItems().setAll(DBConnection.getAllAccountData(currentUser.getUserId()));

        System.out.println("Problem 2: Setting Table View");
        System.out.println("In accountsTable: " + accountsTable.getItems().get(0).getAccountName());
        System.out.println("In accountsTable: " + accountsTable.getItems().get(1).getAccountName());
    }

    public static Account getSelectedAccount(){
        return accountModSelected;
    }

    public static void setSelectedAccount(Account account){
        accountModSelected = account;
    }

    public static void resetSelectedAccount(){
        accountModSelected = null;
    }
}
