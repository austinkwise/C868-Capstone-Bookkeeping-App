package main;

import javafx.fxml.FXML;
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

    private static Account liabilityAccountModSelected;
    private static Account incomeAccountModSelected;
    private static Account expenseAccountModSelected;
    private static Account equityAccountModSelected;

    @FXML private TableView<Account> assetTable;
    @FXML private TableColumn<Account, String> idAssetColumn;
    @FXML private TableColumn<Account, String> accountTypeAssetColumn;
    @FXML private TableColumn<Account, String> nameAssetColumn;
    @FXML private TableColumn<Account, String> descriptionAssetColumn;
    @FXML private TableColumn<Account, String> statusAssetColumn;

    @FXML private TableView<Account> liabilityTable;
    @FXML private TableColumn<Account, String> idLiabilityColumn;
    @FXML private TableColumn<Account, String> accountTypeLiabilityColumn;
    @FXML private TableColumn<Account, String> nameLiabilityColumn;
    @FXML private TableColumn<Account, String> descriptionLiabilityColumn;
    @FXML private TableColumn<Account, String> statusLiabilityColumn;

    @FXML private TableView<Account> incomeTable;
    @FXML private TableColumn<Account, String> idIncomeColumn;
    @FXML private TableColumn<Account, String> accountTypeIncomeColumn;
    @FXML private TableColumn<Account, String> nameIncomeColumn;
    @FXML private TableColumn<Account, String> descriptionIncomeColumn;
    @FXML private TableColumn<Account, String> statusIncomeColumn;

    @FXML private TableView<Account> expenseTable;
    @FXML private TableColumn<Account, String> idExpenseColumn;
    @FXML private TableColumn<Account, String> accountTypeExpenseColumn;
    @FXML private TableColumn<Account, String> nameExpenseColumn;
    @FXML private TableColumn<Account, String> descriptionExpenseColumn;
    @FXML private TableColumn<Account, String> statusExpenseColumn;

    @FXML private TableView<Account> equityTable;
    @FXML private TableColumn<Account, String> idEquityColumn;
    @FXML private TableColumn<Account, String> accountTypeEquityColumn;
    @FXML private TableColumn<Account, String> nameEquityColumn;
    @FXML private TableColumn<Account, String> descriptionEquityColumn;
    @FXML private TableColumn<Account, String> statusEquityColumn;

    public void setupChartOfAccounts(User user, Bookkeeper bookkeeper) throws SQLException, ClassNotFoundException {
        currentUser = user;
        myBk = bookkeeper;

        populateTables();
    }

    @FXML private void addAccountClick() throws IOException{
        myBk.showAccountDetail();
    }

    @FXML private void editAssetAccount() throws IOException{
        setSelectedAccount(assetTable.getSelectionModel().getSelectedItem());

        if(accountModSelected == null){
            System.out.println("pick an account");
        }else{
            myBk.showAccountDetail();
        }
    }

    @FXML private void editLiabilityAccount() throws IOException{
        liabilityAccountModSelected = liabilityTable.getSelectionModel().getSelectedItem();
    }

    @FXML private void editIncomeAccount() throws IOException{
        incomeAccountModSelected = incomeTable.getSelectionModel().getSelectedItem();
    }


    @FXML private void editExpenseAccount() throws IOException{
        expenseAccountModSelected = expenseTable.getSelectionModel().getSelectedItem();
    }

    @FXML private void editEquityAccount() throws IOException{
        equityAccountModSelected = equityTable.getSelectionModel().getSelectedItem();
    }

    private void populateTables() throws SQLException, ClassNotFoundException {
        //ASSET TABLE
        idAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusAssetColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        assetTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), 1));
        //LIABILITY TABLE
        idLiabilityColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeLiabilityColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameLiabilityColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionLiabilityColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusLiabilityColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        liabilityTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), 2));
        //INCOME TABLE
        idIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        incomeTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), 3));
        //EXPENSE TABLE
        idExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusExpenseColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        expenseTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), 4));
        //EQUITY TABLE
        idEquityColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeEquityColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameEquityColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionEquityColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusEquityColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        equityTable.getItems().setAll(DBConnection.getAccountData(currentUser.getUserId(), 5));
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
