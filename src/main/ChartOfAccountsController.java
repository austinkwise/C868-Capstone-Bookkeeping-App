package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Model.User;

import java.io.IOException;

public class ChartOfAccountsController {
    private User currentUser;

    public void setupChartOfAccounts(User currentUser){
        this.currentUser = currentUser;
    }

    @FXML private void addNewAccount() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccountDetail.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        AccountDetailController controller = loader.getController();
        controller.setupAccountDetail(currentUser);
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
}
