package uk.ac.aber.cs221.gp05.buccaneergame.util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.HashMap;

public class JavafxUtil {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    public static void changeScene(String fxml) {

    }
        protected void addScreen(String name, Pane pane){
            screenMap.put(name, pane);
        }

        protected void removeScreen(String name){
            screenMap.remove(name);
        }

        protected void activate(String name){
            main.setRoot( screenMap.get(name) );
        }
        //Write some static class to simplify switching scenes in JavaFX instead

    }

