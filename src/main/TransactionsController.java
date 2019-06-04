package main;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Helpers.DBConnection;
import main.Model.Transaction;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class TransactionsController {
    private static Bookkeeper myBk;
    private static User currentUser;
    private static Transaction transactionModSelected;

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> accountColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, String> amountColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;

    public void setupTransactions(Bookkeeper bookkeeper, User user) throws SQLException {
        myBk = bookkeeper;
        currentUser = user;

        //populateTable();
    }

    @FXML private void addTransactionClick() throws IOException {
        myBk.showTransactionDetail();
    }

    @FXML private void editTransactionClick() throws IOException {
        setSelectedTransaction(transactionTable.getSelectionModel().getSelectedItem());

        if(transactionModSelected == null){
            System.out.println("pick an account");
        }else{
            myBk.showTransactionDetail();
        }
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

    public static void resetSelectedTransaction(){
        transactionModSelected = null;
    }

}
