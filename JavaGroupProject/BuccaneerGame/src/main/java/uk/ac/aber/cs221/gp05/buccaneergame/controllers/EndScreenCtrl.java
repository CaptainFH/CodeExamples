
package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;

import java.io.IOException;
import java.util.Objects;

/**
 * The end screen class, the end screen is shown when the game has finished
 * this class displays the winner and also allows the users to navigate to the main menu
 *
 * @author Igor Daniulak (igd4)
 * @version 0.1 Class is created
 */

public class EndScreenCtrl {

    @FXML
    private Label displayWinnerLabel;

    @FXML
    private Button mainMenuButton;

    //This method is used when a player clicks on the button
    //The method will transport the user to the main menu
    @FXML
    void goToMainMenu(ActionEvent event) throws IOException { // Method for returning to the main menu after pressing the corresponding button
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) mainMenuButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }

    //This method will find the winner of the game and displays their name in the label
    void setDisplayWinnerLabel() {
        displayWinnerLabel.setText(GameInitialiser.game.findCurrentPlayer().getName());
    }

}
