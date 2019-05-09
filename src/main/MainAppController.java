package main;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class MainAppController {
    private theapp theappApp;
    @FXML private AnchorPane sideMenu;


    public void setupMainApp(theapp theappApp) {
        this.theappApp = theappApp;
        movePane();
    }

    @FXML private void signOutClicked(ActionEvent e) throws IOException {
        theappApp.showSignIn();
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
