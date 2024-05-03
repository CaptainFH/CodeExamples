package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Class responsible for the control of the settings screen
 * Allows for simple functionality to toggle the accessibility mode
 *
 * @author Igor Daniulak (igd4)
 * @version 0.1 Class created
 * @version 0.2 back button functionality added
 */

public class SettingsCtrl {

    @FXML
    private Button backButton;

    @FXML
    void backToMainMenu(ActionEvent event) throws Exception { //Method for returning to main menu using the relevant button
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) backButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }

}
