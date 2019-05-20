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
import java.sql.SQLException;
import java.util.List;

public class ChartOfAccountsController {
    private User currentUser;

    @FXML private TableView<Account> assetTable;
    @FXML private TableColumn<Account, Integer> idAssetColumn;
    @FXML private TableColumn<Account, String> accountTypeAssetColumn;
    @FXML private TableColumn<Account, String> nameAssetColumn;
    @FXML private TableColumn<Account, String> descriptionAssetColumn;
    @FXML private TableColumn<Account, String> statusAssetColumn;


    public void setupChartOfAccounts(User currentUser) throws SQLException, ClassNotFoundException {
        this.currentUser = currentUser;
        //populateTables();
        System.out.println("userId: " + this.currentUser.getUserId() + " setupChartOfAccount() in ChartOfAccountsController");
    }

    @FXML private void addNewAccount() throws IOException{
        System.out.println("The issue is happening here where the current User object is returning null rather than containing the current users information");
        System.out.println("userId: " + currentUser.getUserId() + " addNewAccount() in ChartOfAccountsController");

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
        System.out.println("userId: " + this.currentUser.getUserId() + " editAssetAccount() in ChartOfAccountsController");
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
        idAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        accountTypeAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        nameAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        descriptionAssetColumn.setCellValueFactory(new PropertyValueFactory<>("accountDescription"));
        statusAssetColumn.setCellValueFactory(new PropertyValueFactory<>("archiveAccount"));
        assetTable.getItems().setAll(DBHelper.getAssetData(currentUser.getUserId()));
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
}
