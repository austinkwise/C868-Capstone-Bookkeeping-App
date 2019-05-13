package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.Helpers.DBHelper;
import main.Model.User;

import java.io.IOException;
import java.sql.SQLException;

public class SignInController {
    private Bookkeeper myBk;
    private DBHelper myDb;
    @FXML
    private TextField usernameEmailTF, passwordTF;

    public void setupSignIn(Bookkeeper myBookkeeper) {
        this.myBk = myBookkeeper;
    }

    @FXML private void signInClicked(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        String usernameEmail = usernameEmailTF.getText();
        String password = passwordTF.getText();

        User currentUser;
        currentUser = myDb.signInUser(usernameEmail, password);

        myBk.showMainApp(currentUser);
    }


    @FXML private void signUpClicked(ActionEvent e) throws IOException {
        myBk.showSignUp();
    }

    @FXML private void exitClicked(ActionEvent e){
        Platform.exit();
    }
}
