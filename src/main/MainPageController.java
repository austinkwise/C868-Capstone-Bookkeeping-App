package main;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Bookkeeper;
import main.Model.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainPageController {
    private Bookkeeper myBk;
    private User currentUser;
    @FXML private AnchorPane sideMenu;
    @FXML private StackPane mainArea;
    @FXML private Label orgLabel;
    @FXML private Label pageLabel;
    @FXML private Label nameLabel;


    public void setupMainApp(Bookkeeper myBk, User currentUser) throws IOException {
        this.myBk = myBk;
        this.currentUser = currentUser;

        orgLabel.setText(currentUser.getOrgName());
        nameLabel.setText(currentUser.getName());

        showTransactions();
        movePane();
    }

    @FXML private void signOutClicked() throws IOException {
        myBk.showSignIn();
    }

    @FXML private void showTransactions() throws IOException {
        pageLabel.setText("Transactions");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Transactions.fxml"));
        loader.load();
        TransactionsController controller = loader.getController();
        controller.setupTransactions(currentUser);

        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/Transactions.fxml"));
        mainArea.getChildren().setAll(node);
    }

    @FXML private void showChartOfAccounts() throws IOException {
        pageLabel.setText("Chart Of Accounts");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        loader.load();
        ChartOfAccountsController controller = loader.getController();
        controller.setupChartOfAccounts(currentUser);

        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        mainArea.getChildren().setAll(node);
    }

    @FXML private void showReports() throws IOException {
        pageLabel.setText("Reports");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Reports.fxml"));
        loader.load();
        ReportsController controller = loader.getController();
        controller.setupReports(currentUser);

        Node node;
        node = FXMLLoader.load(getClass().getResource("/fxml/Reports.fxml"));
        mainArea.getChildren().setAll(node);
    }

    @FXML private void showProfile() throws IOException {
        pageLabel.setText("Profile");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ProfileController controller = loader.getController();
        controller.setProfile(currentUser);
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
