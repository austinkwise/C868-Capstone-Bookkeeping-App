package main;

import javafx.animation.TranslateTransition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class MainPageController {
    private Bookkeeper myBk;
    private static User currentUser;
    @FXML private AnchorPane sideMenu;
    @FXML public StackPane mainArea;
    @FXML private Label orgLabel;
    @FXML private Label pageLabel;
    @FXML private Label nameLabel;


    public void setupMainApp(Bookkeeper myBk, User user) throws IOException, SQLException {
        this.myBk = myBk;
        currentUser = user;

        System.out.println("userId: " + currentUser.getUserId() + " setupMainApp() in MainPageController");

        orgLabel.setText(currentUser.getOrgName());
        nameLabel.setText(currentUser.getName());

        transactionsCLick();
        movePane();
    }

    @FXML private void signOutClick() throws IOException {
        myBk.showSignIn();
    }

    @FXML private void transactionsCLick() throws IOException, SQLException {
        pageLabel.setText("Transactions");
        myBk.showTransactions();
    }

    @FXML private void chartOfAccountsClick() throws IOException, SQLException, ClassNotFoundException {
        pageLabel.setText("Chart Of Accounts");
        myBk.showChartofAccounts();
    }

    @FXML private void reportsClick() throws IOException, SQLException {
        pageLabel.setText("Reports");
        myBk.showReports();
    }

    @FXML private void profileClick() throws IOException {
        myBk.showProfile();
    }


    public void movePane(){
        sideMenu.setOnMouseEntered( mouseEvent -> {
            TranslateTransition menuOpen = new TranslateTransition(new Duration(300), sideMenu);
            menuOpen.setToX(100.0);
            menuOpen.play();
        });
        sideMenu.setOnMouseExited( mouseEvent -> {
            TranslateTransition menuOpen = new TranslateTransition(new Duration(300), sideMenu);
            menuOpen.setToX(0.0);
            menuOpen.play();
        });
    }
}
