package main;

import javafx.fxml.FXML;
import main.Model.User;

import java.io.IOException;

public class TransactionsController {
    private static Bookkeeper myBk;
    private static User currentUser;

    public void setupTransactions(Bookkeeper bookkeeper, User user) {
        myBk = bookkeeper;
        currentUser = user;

        System.out.println("userId: " + currentUser.getUserId() + " setupTransaction() in TransactionsController");
    }

    @FXML private void showTransactionDetail() throws IOException {
        System.out.println("The issue is happening here where the current User object is returning null rather than containing the current users information");
        System.out.println ("userId: " + currentUser.getUserId() + " showTransactionDetail() in TransactionsController");

        myBk.showTransactionDetail();
    }
}
