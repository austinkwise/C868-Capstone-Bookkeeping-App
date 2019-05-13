package main;

import main.Model.User;

public class TransactionsController {
    private Bookkeeper myBk;
    private User currentUser;

    public void setupTransactions(Bookkeeper myBk, User currentUser) {
        this.myBk = myBk;
        this.currentUser = currentUser;
    }
}
