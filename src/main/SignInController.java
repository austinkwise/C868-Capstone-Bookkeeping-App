package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Helpers.DBConnection;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;



public class SignInController {
    private Bookkeeper myBk;
    private DBConnection myDb;

    @FXML
    private TextField usernameEmailTF, passwordTF;
    @FXML private Label errorLabel;

    public void setupSignIn(Bookkeeper myBookkeeper) {
        this.myBk = myBookkeeper;
    }

    @FXML private void signInClicked(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        String usernameEmail = usernameEmailTF.getText();
        String password = passwordTF.getText();

        User currentUser;
        currentUser = myDb.signInUser(usernameEmail, password);

        if (currentUser == null){
            errorLabel.setText("Invalid credentials given.");
        }else{
            myBk.showMainApp(currentUser);
        }

    }

    @FXML private void signUpClicked(ActionEvent e) throws IOException {
        myBk.showSignUp();
    }

    @FXML private void exitClicked(ActionEvent e){
        Platform.exit();
    }
}
