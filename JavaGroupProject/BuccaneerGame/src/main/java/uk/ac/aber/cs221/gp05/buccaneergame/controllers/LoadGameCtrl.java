package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class responsible for the managing of the load screen
 * This class allows for the functionality of the Load, Delete and return buttons
 * which in turn allows for the load screen to do what it was designed for
 * The buttons and screen UI was made in FXML with the controller adding the desired functions
 *
 * @author Parsa Haghighi (pad31)
 * @version 0.1 Class created
 * @version 0.2 Back button functionality added
 */

public class LoadGameCtrl {

    @FXML
    public Label loadGameMenu;

    @FXML
    public Box contentBox;

    @FXML
    public Label savename;

    @FXML
    public Label savetime;

    @FXML
    public Label save1;

    @FXML
    public Label save1time;

    @FXML
    public Button loadButton;

    @FXML
    public Button deleteButton;

    @FXML
    public Button backButton;
    @FXML
    public ListView list;

    public void loadgame(ActionEvent actionEvent) {
    }

    public void deletesave(ActionEvent actionEvent) {
    }


    @FXML
    public void backToMainMenu() throws IOException { // Method for returning to the main manu using a button
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) backButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }
}
