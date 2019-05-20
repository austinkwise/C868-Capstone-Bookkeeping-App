package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Helpers.DBHelper;
import main.Model.User;

import java.sql.SQLException;

public class ProfileController {
    @FXML
    private TextField usernameTf, nameTf, phoneTf, emailTf, orgNameTf;
    @FXML private Button saveBtn, cancelBtn;
    private User currentUser;
    private DBHelper myDb;

    public void setProfile(User currentUser){
        this.currentUser = currentUser;

        String username = currentUser.getUsername();
        String name = currentUser.getName();
        String phone = currentUser.getPhone();
        String email = currentUser.getEmail();
        String orgName = currentUser.getOrgName();

        usernameTf.setText(username);
        nameTf.setText(name);
        phoneTf.setText(phone);
        emailTf.setText(email);
        orgNameTf.setText(orgName);
    }

    @FXML private void saveProfile() throws SQLException, ClassNotFoundException {
        String username = usernameTf.getText();
        String name = nameTf.getText();
        String phone = phoneTf.getText();
        String email = emailTf.getText();
        String orgName = orgNameTf.getText();

        int userId = currentUser.getUserId();

        System.out.println("userId: " + userId + " saveProfile() in ProfileController");

        boolean saveSuccessful = false;
        saveSuccessful = myDb.updateUser(username, name, phone, email, orgName, userId);

        if(saveSuccessful){
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void cancelProfile() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
