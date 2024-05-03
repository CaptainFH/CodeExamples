package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.IslandTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ResourceBundle;

import static uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser.game;


/**
 * Class responsible for the control fo the trade events
 * This class allows for the trading of treasures and crew cards
 *
 * @author Jay Kirkham (jak77)
 * @version 0.1 Class is created
 * @version 0.2 Added functionality for the calling of the graphical elements for the treasures
 * @version 0.3 Added functionality for the closing of the screen post-trade
 * @version 0.4 Modified switch statement for better efficiency
 * @see Treasure
 * @see Player
 */

public class ChanceTradeEvent implements Initializable {

    @FXML private ToggleButton treasure00;
    @FXML private ToggleButton treasure01;
    @FXML private ToggleButton treasure02;
    @FXML private ToggleButton treasure03;
    @FXML private ToggleButton treasure04;
    @FXML private ToggleButton treasure05;
    @FXML private ToggleButton treasure06;
    @FXML private ToggleButton treasure10;
    @FXML private ToggleButton treasure11;
    @FXML private ToggleButton treasure12;
    @FXML private ToggleButton treasure13;
    @FXML private ToggleButton treasure14;
    @FXML private ToggleButton treasure15;
    @FXML private ToggleButton treasure16;
    @FXML private ToggleButton treasure20;
    @FXML private ToggleButton treasure21;
    @FXML private ToggleButton treasure22;
    @FXML private ToggleButton treasure23;
    @FXML private ToggleButton treasure24;
    @FXML private ToggleButton treasure25;
    @FXML private ToggleButton treasure26;

    @FXML private Label totalValue;
    @FXML private Button confirmButton;

    private ArrayList<ToggleButton> treasureFrames;
    private ArrayList<Treasure> treasurePile = ((IslandTile) (game.getBoard().getTileMatrix())[8][8]).getTreasures();
    private static int currentLimit;
    private static Player currentPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        takeTreasure();
    }

    public static void setCurrents(Player player, int limit) {
        currentPlayer = player;
        currentLimit = limit;
    }

    public void takeTreasure() {
        getFrames();
        int debouncer = 0;
        for (Treasure treasure:treasurePile) {
            try {
                Image picture = new Image(new FileInputStream(getURLForTreasure(treasure)));
                ImageView viewer = new ImageView();
                viewer.setImage(picture);
                treasureFrames.get(debouncer).setGraphic(viewer);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            debouncer++;
        }
    }

    /**
     * This method assigns the graphical elements of each treasure so that their icons may be displayed where necessary
     *
     * @param treasure references the treasures as to call upon their graphical elements
     * @return returns the treasure URL
     */
    private String getURLForTreasure(Treasure treasure) { //Method gets the treasure urls from within the program structure
        return switch (treasure) {
            case DIAMOND -> // Each of these refers to the enum which stores their value
                    "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\diamond_big.png";
            case RUBY ->
                    "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\ruby_big.png";
            case GOLD ->
                    "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\gold_big.png";
            case PEARL ->
                    "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\pearl_big.png";
            case RUM ->
                    "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\rum_big.png";
        };
    }

    public void updateClicked() { // Detects if the field was clicked
        int returnValue = 0;
        int indexer = 0;
        for (ToggleButton item:treasureFrames) {
            if (item.isSelected()) {
                int value = treasurePile.get(indexer).getValue();
                returnValue += value;
            }
            indexer++;
        }
        if (returnValue <= currentLimit) {
            totalValue.setTextFill(Color.GREEN);
            confirmButton.setDisable(false);
        } else {
            totalValue.setTextFill(Color.RED);
            confirmButton.setDisable(true);
        }
    }

    public void getFrames() { // Responsible for the placement of the treasure within the trade menu
        treasureFrames = new ArrayList<>();
        treasureFrames.add(treasure00);
        treasureFrames.add(treasure01);
        treasureFrames.add(treasure02);
        treasureFrames.add(treasure03);
        treasureFrames.add(treasure04);
        treasureFrames.add(treasure05);
        treasureFrames.add(treasure06);
        treasureFrames.add(treasure10);
        treasureFrames.add(treasure11);
        treasureFrames.add(treasure12);
        treasureFrames.add(treasure13);
        treasureFrames.add(treasure14);
        treasureFrames.add(treasure15);
        treasureFrames.add(treasure16);
        treasureFrames.add(treasure20);
        treasureFrames.add(treasure21);
        treasureFrames.add(treasure22);
        treasureFrames.add(treasure23);
        treasureFrames.add(treasure24);
        treasureFrames.add(treasure25);
        treasureFrames.add(treasure26);

    }

    @FXML
    private void closeWindowAction() { //Closes the action window following the trade
        int indexer = 0;
        for (ToggleButton item:treasureFrames) {
            if (item.isSelected()) {
                currentPlayer.addTreasure(treasurePile.get(indexer));
                treasurePile.remove(treasurePile.get(indexer));
            }
            else {
                indexer++;
            }
        }
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
