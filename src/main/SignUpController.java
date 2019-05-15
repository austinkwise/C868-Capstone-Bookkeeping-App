package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.Bookkeeper;
import main.Helpers.DBHelper;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    private Bookkeeper myBk;
    private DBHelper myDb;
    @FXML
    private TextField firstnametf, lastnametf, usernametf, passwordtf, phonetf, emailtf, orgNametf;

    public void setupSignUp(Bookkeeper myBk) {
        this.myBk = myBk;
    }

    @FXML private void backClicked(ActionEvent e) throws IOException {
        myBk.showSignIn();
    }

    @FXML private void signUpClicked(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        String username = usernametf.getText();
        String password = passwordtf.getText();
        String firstName = firstnametf.getText();
        String lastName = lastnametf.getText();
        String name = firstName + " " + lastName;
        String phone = phonetf.getText();
        String email = emailtf.getText();
        String orgName = orgNametf.getText();


        myDb.signUpUser(username, password, name, phone, email, orgName);
        myBk.showSignIn();
    }
}
