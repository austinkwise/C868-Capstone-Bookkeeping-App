package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class Bookkeeper extends Application {
    private Stage stage;
    private MainPageController mainPageController;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        showSignIn();
    }

    public void showSignIn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SignIn.fxml"));
        GridPane pane = loader.load();

        SignInController controller = loader.getController();
        controller.setupSignIn(this);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void showSignUp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SignUp.fxml"));
        GridPane pane = loader.load();

        SignUpController controller = loader.getController();
        controller.setupSignUp(this);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void showMainApp(User currentUser) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainPage.fxml"));
        AnchorPane pane = loader.load();

        this.currentUser = currentUser;

        mainPageController = loader.getController();
        mainPageController.setupMainApp(this, this.currentUser);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void showTransactions() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Transactions.fxml"));
        loader.load();

        TransactionsController controller = loader.getController();
        controller.setupTransactions(this, currentUser);

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Transactions.fxml"));
        mainPageController.mainArea.getChildren().setAll(pane);
    }

    public void showTransactionDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TransactionDetail.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        TransactionDetailController controller = loader.getController();
        controller.setupTransactionDetail(currentUser);

        stage.show();
    }

    public void showChartofAccounts() throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        loader.load();

        ChartOfAccountsController controller = loader.getController();
        controller.setupChartOfAccounts(currentUser, this);

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        mainPageController.mainArea.getChildren().setAll(pane);
    }

    public void showReports() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Reports.fxml"));
        loader.load();
        ReportsController controller = loader.getController();
        controller.setupReports(currentUser);

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Reports.fxml"));
        mainPageController.mainArea.getChildren().setAll(pane);
    }

    public void showProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ProfileController controller = loader.getController();
        controller.setProfile(currentUser);
    }

    public void showAccountDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AccountDetail.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AccountDetailController controller = loader.getController();
        controller.setupAccountDetail(currentUser);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
