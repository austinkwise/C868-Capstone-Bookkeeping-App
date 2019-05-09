package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Helpers.DBHelper;

import java.io.IOException;

public class theapp extends Application {
    private Stage stage;
    private DBHelper mydb;

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

    public void showMainApp() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainApp.fxml"));
        AnchorPane pane = loader.load();
        MainAppController controller = loader.getController();
        controller.setupMainApp(this);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
