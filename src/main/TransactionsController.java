package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Bookkeeper;
import main.Model.User;

import java.io.IOException;

public class TransactionsController {
    private Bookkeeper myBk;
    private User currentUser;

    public void setupTransactions(User currentUser) {
        this.currentUser = currentUser;

        System.out.println("userId: " + this.currentUser.getUserId() + " setupTransaction() in TransactionsController");
    }

    @FXML private void showTransactionDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TransactionDetail.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        System.out.println ("userId: " + currentUser.getUserId() + " showTransactionDetail() in TransactionsController");

        TransactionDetailController controller = loader.getController();
        controller.setupTransactionDetail(currentUser);

        stage.show();
    }
}
