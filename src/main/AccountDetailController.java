package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Helpers.DBConnection;
import main.Model.Account;
import main.Model.User;

import java.sql.SQLException;

public class AccountDetailController {
    private static User currentUser;
    private static Bookkeeper myBk;
    private DBConnection myDb;
    private ProfileController profile;
    private static Account accountModSelected;

    @FXML private Button cancelBtn, saveBtn;
    @FXML private ComboBox<String> accountsCb, accountTypeCb;
    @FXML private TextField accountNameTf, accountIdTf;
    @FXML private TextArea descriptionTa;
    @FXML private CheckBox archiveAccountCheck;

    public void setupAccountDetail(User user){
        currentUser = user;

        accountModSelected = ChartOfAccountsController.getSelectedAccount();

        if(accountModSelected == null){
            archiveAccountCheck.setVisible(false);
            accountsCb.setItems(accounts);
            setAccountTypes();
        }else{
            accountsCb.setItems(accounts);
            setAccountTypes();

            accountsCb.getSelectionModel().select(accountModSelected.getAccount());
            accountTypeCb.getSelectionModel().select(accountModSelected.getAccount());
            accountNameTf.setText(accountModSelected.getAccountName());
            accountIdTf.setText(accountModSelected.getAccountId());
            descriptionTa.setText(accountModSelected.getAccountDescription());
            if(accountModSelected.getArchiveAccount().contentEquals("1")){
                archiveAccountCheck.isSelected();
            }
        }
    }

    ObservableList<String> accounts = FXCollections.observableArrayList("Asset Account", "Liability Account", "Income Account", "Expense Account", "Equity Account");

    private void setAccountTypes(){
        accountsCb.getSelectionModel().selectedItemProperty().addListener( (options, oldAccount, newAccount) -> {
                    int idx = accountsCb.getSelectionModel().getSelectedIndex();
                    switchAccountType(idx);
                }
        );
    }

    private void switchAccountType(int idx){
        ObservableList<String> accountType = FXCollections.observableArrayList("");

        switch (idx){
            case 0:
                accountType = FXCollections.observableArrayList("Cash and Bank", "Other Current Asset", "Other Long Term Asset");
                break;
            case 1:
                accountType = FXCollections.observableArrayList("Current Liability", "Other Long Term Liability");
                break;
            case 2:
                accountType = FXCollections.observableArrayList("Income", "Uncategorized Income");
                break;
            case 3:
                accountType = FXCollections.observableArrayList("Operation Expense", "Cost of Goods Sold", "Uncategorized Expense");
                break;
            case 4:
                accountType = FXCollections.observableArrayList("Other Equity", "Retained Earnings: Profit");
                break;
            default:
                break;
        }
        accountTypeCb.setItems(accountType);
    }

    @FXML
    private void saveClick() throws SQLException, ClassNotFoundException {
        String accounts = accountsCb.getSelectionModel().getSelectedItem();
        String accountType = accountTypeCb.getSelectionModel().getSelectedItem();
        String accountName = accountNameTf.getText();
        int accountId = Integer.parseInt(accountIdTf.getText());
        String description = descriptionTa.getText();
        int archiveAccount = 0;

        int userId = currentUser.getUserId();

        boolean isArchived = archiveAccountCheck.isSelected();
        if (isArchived){
            archiveAccount = 1;
        }

        if(accountModSelected == null){
            boolean saveSuccess;
            saveSuccess = myDb.saveNewAccount(accounts, accountType, accountName, accountId, description, archiveAccount, userId);

            if(saveSuccess){
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }else {
            boolean saveSuccess;
            saveSuccess = myDb.updateAccount(accounts, accountType, accountName, accountId, description, archiveAccount, userId);

            if(saveSuccess){
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML private void cancel(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
