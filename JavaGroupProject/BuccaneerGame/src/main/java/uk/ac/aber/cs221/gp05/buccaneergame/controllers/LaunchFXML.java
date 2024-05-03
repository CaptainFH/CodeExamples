package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameManager;

import java.io.IOException;

import static javafx.application.Application.launch;

public class LaunchFXML extends Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000,800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
