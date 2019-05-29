package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Helpers.DBHelper;
import main.Model.Account;
import main.Model.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChartOfAccountsController {
    private static User currentUser;

    @FXML private TableView<Account> assetTable;
    @FXML private TableColumn<Account, String> idAssetColumn;
    @FXML private TableColumn<Account, String> accountTypeAssetColumn;
    @FXML private TableColumn<Account, String> nameAssetColumn;
    @FXML private TableColumn<Account, String> descriptionAssetColumn;
    @FXML private TableColumn<Account, String> statusAssetColumn;


    public void setupChartOfAccounts(User user) throws SQLException, ClassNotFoundException {
        currentUser = user;
        //populateTables();

        idAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusAssetColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));
        assetTable.getItems().setAll(getAssetData(currentUser.getUserId()));
    }

    @FXML private void addNewAccount() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AccountDetail.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AccountDetailController controller = loader.getController();
        controller.setupAccountDetail(currentUser);

        stage.show();
    }

    @FXML private void editAssetAccount() throws IOException{

    }

    @FXML private void editLiabilityAccount() throws IOException{
    }

    @FXML private void editIncomeAccount() throws IOException{
    }


    @FXML private void editExpenseAccount() throws IOException{
    }


    @FXML private void editEquityAccount() throws IOException{
    }

    private void populateTables() throws SQLException, ClassNotFoundException {
        //ASSET TABLE
        ObservableList<Account> assetAccountList = FXCollections.observableArrayList(getAssetData(currentUser.getUserId()));
        for(Account a : assetAccountList){
            System.out.println(a.getAccountName() + ", " + a.getAccountId());
        }
        assetTable.setItems(assetAccountList);

        idAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusAssetColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));

        assetTable.getColumns().setAll(idAssetColumn, accountTypeAssetColumn, nameAssetColumn, descriptionAssetColumn, statusAssetColumn);
        /*
        //LIABILITY TABLE
        populateLiabilityTable();
        //INCOME TABLE
        populateIncomeTable();
        //EXPENSE TABLE
        populateExpenseTable();
        //EQUITY TABLE
        populateEquityTable();`
         */
    }

    public List<Account> getAssetData(int userId) throws SQLException, ClassNotFoundException {
        ObservableList<Account> assetAccountList = FXCollections.observableArrayList();
        ResultSet rs;

        String assetQuery = "SELECT * FROM accounts WHERE account = 'Asset Account' AND userId = ?;";


        PreparedStatement pst = DBHelper.getConn().prepareStatement(assetQuery);
        pst.setInt(1, userId);
        rs = pst.executeQuery();

        while(rs.next()){
            String accountId = rs.getString("accountId");
            String account = rs.getString("account");
            String accountType = rs.getString("accountType");
            String accountName = rs.getString("accountName");
            String accountDescription = rs.getString("accountDescription");
            String archiveAccount = rs.getString("archiveAccount");
            String userIdDb = rs.getString("userId");
            assetAccountList.add(new Account(accountId, account, accountType, accountName, accountDescription, archiveAccount, userIdDb));
        }


        return assetAccountList;
    }
}
