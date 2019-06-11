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

    public void showTransactionDetail() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TransDetailController.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        TransDetailController controller = loader.getController();
        controller.setupTransactionDetail(currentUser);

        stage.show();
    }

    public void showChartofAccounts() throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChartOfAccounts.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ChartOfAccountsController controller = loader.getController();
        controller.setupChartOfAccounts(currentUser, this);
    }

    public void showReports() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reports.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        ReportsController controller = loader.getController();
        controller.setupReports(currentUser);
    }

    public void showProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        ProfileController controller = loader.getController();
        controller.setProfile(currentUser);

        stage.show();
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
