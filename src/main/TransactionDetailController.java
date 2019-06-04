package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Helpers.DBConnection;
import main.Model.Transaction;
import main.Model.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class TransactionDetailController {
    private static User currentUser;
    private static DBConnection myDb;
    private static Transaction transactionModSelected;

    @FXML private ComboBox<String> transactionTypeCb, accountCb, categoryCb;
    @FXML private TextField transactionAmountTf;
    @FXML private TextArea transactionDescriptionTa;
    @FXML private DatePicker dateDp;
    @FXML private Button saveBtn, deleteBtn, cancelBtn;

    public TransactionDetailController() throws SQLException {
    }

    public void setupTransactionDetail(User user){
        currentUser = user;

        transactionModSelected = TransactionsController.getSelectedTransaction();

        if(transactionModSelected == null){
            deleteBtn.setVisible(false);

            transactionTypeCb.setItems(transactionTypes);
            accountCb.setItems(accountNames);
            setAccountTypes();
        }else{
            transactionTypeCb.setItems(transactionTypes);
            accountCb.setItems(accountNames);
            setAccountTypes();

            accountCb.getSelectionModel().select(transactionModSelected.getTransactionAccount());
            categoryCb.getSelectionModel().select(transactionModSelected.getTransactionCategory());
            transactionAmountTf.setText(transactionModSelected.getTransactionAmount());
            transactionDescriptionTa.setText(transactionModSelected.getTransactionDescription());
            dateDp.setValue(transactionModSelected.getTransactionDate().toLocalDate());
        }
    }

    ObservableList<String> transactionTypes = FXCollections.observableArrayList("Income", "Expense");
    ObservableList<String> accountNames = FXCollections.observableArrayList(DBConnection.getAccountNames(currentUser.getUserId()));

    private void setAccountTypes(){
        transactionTypeCb.getSelectionModel().selectedItemProperty().addListener( (options, oldAccount, newAccount) -> {
                    int idx = transactionTypeCb.getSelectionModel().getSelectedIndex();
                    try {
                        switchAccountType(idx);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void switchAccountType(int idx) throws SQLException {
        ObservableList<String> categories = FXCollections.observableArrayList("");

        switch (idx){
            case 0:
                categories = FXCollections.observableArrayList(DBConnection.getCategories(currentUser.getUserId(), "'Income Account'"));
                break;
            case 1:
                categories = FXCollections.observableArrayList(DBConnection.getCategories(currentUser.getUserId(), "'Expense Account'"));
                break;
            default:
                break;
        }
        categoryCb.setItems(categories);
    }


    @FXML private void saveClick() throws SQLException, ClassNotFoundException {
        String transactionType = transactionTypeCb.getSelectionModel().getSelectedItem();
        String account = accountCb.getSelectionModel().getSelectedItem();
        String category = categoryCb.getSelectionModel().getSelectedItem();
        String amount = transactionAmountTf.getText();
        String description = transactionDescriptionTa.getText();

        int hour = 0;
        int minute = 0;
        LocalDate date = null;
        if(dateDp != null){
            date = dateDp.getValue();
        }
        LocalDateTime transDate = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), hour, minute);

        int userId = currentUser.getUserId();

        boolean saveSuccess;
        saveSuccess = myDb.saveNewTransaction(transactionType, account, transDate, category, amount, description, userId);

        if(saveSuccess){
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void deleteClick() throws SQLException {
        myDb.deleteTransaction(currentUser.getUserId(), transactionModSelected.getTransactionId());
        Stage stage = (Stage) deleteBtn.getScene().getWindow();
        stage.close();
    }

    @FXML private void cancelClick() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
