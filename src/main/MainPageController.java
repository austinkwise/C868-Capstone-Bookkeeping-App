package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers.DBConnection;
import main.Model.Transaction;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class MainPageController {
    private Bookkeeper myBk;
    private static User currentUser;

    @FXML private Label orgLabel;
    @FXML private Label nameLabel;


    //Main Page Methods
    public void setupMainApp(Bookkeeper myBk, User user) throws IOException, SQLException {
        this.myBk = myBk;
        currentUser = user;

        orgLabel.setText(currentUser.getOrgName());
        nameLabel.setText(currentUser.getName());

        populateTable();
    }

    @FXML private void signOutClick() throws IOException {
        myBk.showSignIn();
    }

    @FXML private void chartOfAccountsClick() throws IOException, SQLException, ClassNotFoundException {
        myBk.showChartofAccounts();
    }

    @FXML private void reportsClick() throws IOException, SQLException {
        myBk.showReports();
    }

    @FXML private void profileClick() throws IOException {
        myBk.showProfile();
    }


    //Transaction Methods
    private static Transaction transactionModSelected;

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> accountColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, String> amountColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;

    @FXML private void addTransactionClick() throws IOException, SQLException {
        myBk.showTransactionDetail();
    }

    @FXML private void editTransactionClick() throws IOException, SQLException {
        setSelectedTransaction(transactionTable.getSelectionModel().getSelectedItem());

        if(transactionModSelected == null){
            System.out.println("pick an account");
        }else{
            myBk.showTransactionDetail();
        }
    }

    @FXML private void updateTableClick() throws SQLException {
        populateTable();
    }

    private void populateTable() throws SQLException {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAccount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDescription"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("transactionCategory"));

        transactionTable.getItems().setAll(DBConnection.getTransactionData(currentUser.getUserId()));
    }

    public static Transaction getSelectedTransaction(){
        return transactionModSelected;
    }

    public static void setSelectedTransaction(Transaction transaction){
        transactionModSelected = transaction;
    }

    public static Transaction resetSelectedTransaction(){
        transactionModSelected = null;
        return transactionModSelected;
    }
}
