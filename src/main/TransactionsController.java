package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import main.Bookkeeper;
import main.Model.User;

public class TransactionsController {
    private Bookkeeper myBk;
    private User currentUser;

    public void setupTransactions(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML private void saveTransaction(){

    }

    @FXML private void cancel(){
        Platform.exit();
    }
}
