package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static uk.ac.aber.cs221.gp05.buccaneergame.util.JavafxUtil.changeScene;

/**
 * to control main menu allowing player to start and
 * load the game, continue from last save, change settings or quit
 *
 * @author Igor Daniulak (igd4)
 * @version 0.1 Class created
 */
public class MainMenuCtrl {
    //fields
    @FXML
    private Button continueButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Button settingsButton;

    //methods

    /**
     * change to LoadGameScreen
     * @param event ...
     */
    @FXML
    void changeToLoadGameScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoadGame.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) loadGameButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }

    /**
     * change to newGameScreen which starts with selecting \
     * player's names and colors
     * @param event ...
     */
    @FXML
    void changeToNewGameScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SelectPlayerView.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) loadGameButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }

    /**
     * change to settings window
     * @param event ...
     */
    @FXML
    void changeToSettingsScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("settingsView.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) loadGameButton.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }

    /**
     * exit the application
     * @param event ...
     */
    @FXML
    void quit(ActionEvent event) {
        Stage mainWindow = (Stage) exitButton.getScene().getWindow();
        mainWindow.close();
    }

}
