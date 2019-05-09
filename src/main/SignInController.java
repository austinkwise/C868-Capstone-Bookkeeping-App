package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {
    private theapp theappApp;
    @FXML private TextField usernameEmailTF, passwordTF;

    public void setupSignIn(theapp theappApp) {
        this.theappApp = theappApp;
    }

    @FXML private void signInClicked(ActionEvent e) throws IOException {
        String usernameEmail = usernameEmailTF.getText();

        if(usernameEmail.contains("%@%")){
            //user is using email to sign in
        }else{
            //user is using username to sign in
        }


        theappApp.showMainApp();
    }

    @FXML private void signUpClicked(ActionEvent e) throws IOException {
        theappApp.showSignUp();
    }

    @FXML private void exitClicked(ActionEvent e){
        Platform.exit();
    }

}
