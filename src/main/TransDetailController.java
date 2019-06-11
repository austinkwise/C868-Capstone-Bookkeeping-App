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

public class TransDetailController {
    private static User currentUser;
    private static DBConnection myDb;
    private static Transaction transactionModSelected;

    @FXML
    private ComboBox<String> transactionTypeCb, accountCb, categoryCb;
    @FXML private TextField transactionAmountTf;
    @FXML private TextArea transactionDescriptionTa;
    @FXML private DatePicker dateDp;
    @FXML private Button saveBtn, deleteBtn, cancelBtn;

    public void setupTransactionDetail(User user) throws SQLException {
        currentUser = user;


        transactionModSelected = MainPageController.getSelectedTransaction();

        ObservableList<String> transactionTypes = FXCollections.observableArrayList("Income", "Expense");
        ObservableList<String> accountNames = FXCollections.observableArrayList(myDb.getAccountNames(currentUser.getUserId()));

        if(transactionModSelected == null){
            deleteBtn.setVisible(false);

            transactionTypeCb.setItems(transactionTypes);
            accountCb.setItems(accountNames);
            setCategories();
        }else{
            deleteBtn.setVisible(true);
            transactionTypeCb.setItems(transactionTypes);
            accountCb.setItems(accountNames);
            setCategories();

            transactionTypeCb.getSelectionModel().select(transactionModSelected.getTransactionType());
            accountCb.getSelectionModel().select(transactionModSelected.getTransactionAccount());
            categoryCb.getSelectionModel().select(transactionModSelected.getTransactionCategory());
            transactionAmountTf.setText(transactionModSelected.getTransactionAmount());
            transactionDescriptionTa.setText(transactionModSelected.getTransactionDescription());
            dateDp.setValue(transactionModSelected.getTransactionDate());
        }
    }

    private void setCategories(){
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
                categories = FXCollections.observableArrayList(myDb.getCategories(currentUser.getUserId(), 1));
                break;
            case 1:
                categories = FXCollections.observableArrayList(myDb.getCategories(currentUser.getUserId(), 2));
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

        LocalDate date = null;
        if(dateDp != null){
            date = dateDp.getValue();
        }

        int userId = currentUser.getUserId();

        if(transactionModSelected == null){
            boolean saveSuccess;
            saveSuccess = myDb.saveNewTransaction(transactionType, account, date, category, amount, description, userId);

            if(saveSuccess){
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }else{
            int transId = transactionModSelected.getTransactionId();
            boolean saveSuccess;
            saveSuccess = myDb.updateTransaction(transactionType, account, date, category, amount, description, transId);

            if(saveSuccess){
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML private void deleteClick() throws SQLException {
        myDb.deleteTransaction(transactionModSelected.getTransactionId());

        Stage stage = (Stage) deleteBtn.getScene().getWindow();
        stage.close();

        transactionModSelected = MainPageController.resetSelectedTransaction();
    }

    @FXML private void cancelClick() {
        transactionModSelected = MainPageController.resetSelectedTransaction();

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
