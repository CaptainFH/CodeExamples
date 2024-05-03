package uk.ac.aber.cs221.gp05.buccaneergame.game;


import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.ChanceCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.State;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateManager;
import uk.ac.aber.cs221.gp05.buccaneergame.util.JacksonUtil;

import java.io.*;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.*;

public class GameInitialiser  extends Application{

    public static GameManager game;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void TestSaveChanceCard() throws IOException {
        ChanceCard card1 = new ChanceCard();
        card1.setText("TEST DATA CARD1");
        ChanceCard card2 = new ChanceCard();
        card2.setText("TEST DATA CARD2");

        List<ChanceCard> testList = new ArrayList<>();
        testList.add(card1);
        testList.add(card2);


        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("TEST.json"), testList);
    }

    public static void loadChanceCard(){
        Queue<ChanceCard> deck = new LinkedList<ChanceCard>();
        try {

            ObjectMapper mapper = new ObjectMapper();
            List<ChanceCard> cards = mapper.readValue(Paths.get("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/chance_cards.json").toFile(), new TypeReference<List<ChanceCard>>(){});

            Collections.shuffle(cards);

            for (ChanceCard c: cards) {
                deck.add(c);
                System.out.println("1");
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }

        for(ChanceCard c : deck){
            System.out.println(c.getText());
        }
        game.setChanceCardQueue(deck);
    }
    public static void main(String[] args) throws IOException {
//        saveChanceCard();
        game = new GameManager();
        loadChanceCard();
        StateManager newState = new StateManager(State.PREGAME, 0 );
        game.setState(newState);
        launch();
    }
}
