package main;

import javafx.fxml.FXML;
import main.Model.User;

public class TransactionDetailController {
    private User currentUser;

    public void setupTransactionDetail(User currentUser){
        this.currentUser = currentUser;

        System.out.println("userId: " + this.currentUser.getUserId() + " setupTransactionDetail() in TransactionDetailController");
    }

    @FXML private void testClick(){
        System.out.println("userId: " + currentUser.getUserId() + " testClick() in TransactionDetailController");
    }
}
