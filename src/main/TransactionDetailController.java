package main;

import javafx.fxml.FXML;
import main.Model.User;

public class TransactionDetailController {
    private static User currentUser;

    public void setupTransactionDetail(User user){
        currentUser = user;

        System.out.println("userId: " + currentUser.getUserId() + " setupTransactionDetail() in TransactionDetailController");
    }

    @FXML private void testClick(){
        System.out.println("userId: " + currentUser.getUserId() + " testClick() in TransactionDetailController");
    }
}
