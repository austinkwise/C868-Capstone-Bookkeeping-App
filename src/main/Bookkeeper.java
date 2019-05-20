package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Model.User;

import java.io.IOException;

public class Bookkeeper extends Application {
    private Stage stage;
    MainPageController mainPage;

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

    public void showMainApp(User currentUser) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainPage.fxml"));
        AnchorPane pane = loader.load();

        System.out.println("userId: " + currentUser.getUserId() + " showMainApp() in Bookkeeper");

        MainPageController controller = loader.getController();
        controller.setupMainApp(this, currentUser);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
