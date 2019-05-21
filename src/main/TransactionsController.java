package main;

import javafx.fxml.FXML;
import main.Model.User;

import java.io.IOException;

public class TransactionsController {
    private Bookkeeper myBk;
    private User currentUser;

    public void setupTransactions(Bookkeeper myBk, User currentUser) {
        this.myBk = myBk;
        this.currentUser = currentUser;

        System.out.println("userId: " + this.currentUser.getUserId() + " setupTransaction() in TransactionsController");
    }

    @FXML private void showTransactionDetail() throws IOException {
        System.out.println("The issue is happening here where the current User object is returning null rather than containing the current users information");
        System.out.println ("userId: " + currentUser.getUserId() + " showTransactionDetail() in TransactionsController");

        myBk.showTransactionDetail();
    }
}
