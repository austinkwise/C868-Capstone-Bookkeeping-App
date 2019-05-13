package main;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.Model.User;

import java.io.IOException;

public class MainPageController {
    private Bookkeeper myBk;
    private User currentUser;
    @FXML private AnchorPane sideMenu;
    @FXML private StackPane mainArea;


    public void setupMainApp(Bookkeeper myBk, User currentUser) throws IOException {
        this.myBk = myBk;
        this.currentUser = currentUser;

        showTransactions();
        movePane();
    }

    @FXML private void signOutClicked(ActionEvent e) throws IOException {
        myBk.showSignIn();
    }

    @FXML private void showTransactions() throws IOException {
        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/Transactions.fxml"));
        mainArea.getChildren().setAll(node);
    }

    @FXML private void showChartOfAccounts() throws IOException {
        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        mainArea.getChildren().setAll(node);
    }

    @FXML private void showReports() throws IOException {
        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/Reports.fxml"));
        mainArea.getChildren().setAll(node);
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
