/**
 * @author pad31
 * @version 0.1
 * @date 05/05/2022
 * Copyright (c) 2021 Aberystwyth University.
 * All rights reserved.
 *
 * This is the controller class for the SplashScreen fxml file
 * The class is designed to allow the user to navigate from the starting screen(Splash Screen) to the main menu
 **/


package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * CLass responsible for the initial splash screen at the start of the game
 * The splash screen launches at the start of the game
 *
 * @author Jack Gannon (jag78)
 * @author Parsa Haghighi (pad31)
 * @version 0.1 Class created
 * @version 0.2 Functionality for the progression to teh main menu added
 */

public class SplashScreenCtrl {

    @FXML
    private Pane pane;

    //This method is used when a player clicks on the pane
    //The method will transport the user to the main menu
    @FXML
     void loadMainMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) pane.getScene().getWindow();
        mainWindow.setScene(sceneToBeChangedTo);
    }
}