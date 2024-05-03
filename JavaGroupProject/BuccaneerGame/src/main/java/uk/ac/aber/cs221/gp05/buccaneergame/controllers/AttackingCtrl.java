package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.IslandTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Tile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.CrewCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

/**
 * The class responsible for the control of the attacking system
 * It includes the method for resolving attack sequences which allow for the combat system to be implemented
 * It links to the Attacking specific FXML files which should change depending on the stage of the encounter
 * If a player loses the battle they should lose an appropriate amount of loot or card
 *
 * @author Igor Daniulak (igd4)
 * @author Jack Gannon (jag78)
 * @version 0.1 Initial version created
 * @version 0.2 Implemented methods for the attacking sequence and their resolution
 * @version 0.3 Fixed multiple errors within the code
 * @see Player
 * @see Treasure
 */
public class AttackingCtrl implements Initializable {
    //fields
    private Player currentPlayer;


    @FXML
    private Label leftPlayerCrew;

    @FXML
    private Text leftPlayerName;

    @FXML
    private ImageView leftShip;

    @FXML
    private Label rightPlayerCrew;

    @FXML
    private Text rightPlayerName;

    @FXML
    private ImageView rightPlayerShip;

    @FXML
    private Button takeLootButt;

    @FXML
    private Text victorResult;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage mainWinow = (Stage) takeLootButt.getScene().getWindow();
        mainWinow.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setstuff();
    }

    private void setstuff() {
        victorResult.setText(GameBoardCtrl.winner.getName() + " WINS!");
        try {
            switch (GameBoardCtrl.winner.getColour()) {
                case GREEN -> leftShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_green_big.png")));
                case YELLOW -> leftShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_yellow_big.png")));
                case BLUE -> leftShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_blue_big.png")));
                case ORANGE -> leftShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_orange_big.png")));
            }
            switch (GameBoardCtrl.loser.getColour()) {
                case GREEN -> rightPlayerShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_green_big.png")));
                case YELLOW -> rightPlayerShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_yellow_big.png")));
                case BLUE -> rightPlayerShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_blue_big.png")));
                case ORANGE -> rightPlayerShip.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_orange_big.png")));
            }

        }catch (Exception e){
            System.err.println("NOOB");
        }
        leftPlayerCrew.setText(String.valueOf(GameBoardCtrl.winnerFPB));
        leftPlayerName.setText(GameBoardCtrl.winner.getName());
        rightPlayerCrew.setText(String.valueOf(GameBoardCtrl.loserFPB));
        rightPlayerName.setText(GameBoardCtrl.loser.getName());
    }

    //methods



    /**
     * to initiate the attack sequence between 2 players
     * the seqeuence can only be initiated if the attacker desires to do so
     * the trigger for the attack sequeance should be the attacker being on the same square as a potential defender
     *
     * @param attacker attacking player (currentPlayer)
     * @param defender defending player (that was on the tile that currentPlayer decided to go to
     * @see Treasure
     * @see CrewCard
     * @see Player
     */
    public void attackSequence(Player attacker, Player defender) throws IOException {
        int attackingValue = attacker.getFightingPower();
        int defendingValue = defender.getFightingPower();
        //attacker wins if the combat value is greater or equal to the defender's
        if (attackingValue >= defendingValue) {
            GameBoardCtrl.winner = attacker;
            GameBoardCtrl.winnerFPB = attacker.getFightingPower();
            GameBoardCtrl.loser = defender;
            GameBoardCtrl.loserFPB = defender.getFightingPower();

            resolveAttackSequence(attacker, defender);
        } else {
            resolveAttackSequence(defender, attacker);
            GameBoardCtrl.winner = defender;
            GameBoardCtrl.winnerFPB = defender.getFightingPower();
            GameBoardCtrl.loser = attacker;
            GameBoardCtrl.loserFPB = attacker.getFightingPower();
        }
    }



    /**
     * Method for resolving the combat encounter
     * The outcome depends on who was the victor and who was the loser
     *
     * @param winner the player with higher fight value
     * @param loser player with lower fight value
     * @see Player
     * @see CrewCard
     * @see Treasure
     */
    public void resolveAttackSequence(Player winner, Player loser) {


        /*
        If the loser has treasure in their ship, then the winner is awarded the treasure. If the winner cannot accommodate
        the treasure in their ship (a ship can take a maximum of two pieces of treasure, and they may already have some),
        then the extra treasure is returned to Treasure Island).
        If the loser does not have treasure, but does have cards, then the two lowest value cards in the loser's hand are
        given to the winner, or one card if the loser only has one card.
        The loser is then allowed to make a legal move up to the maximum squares that they can move, in any direction,
        followed by a change of direction. They must move at least one square.
        The winner remains in the direction they were already moving/facing
         */
        ArrayList<Treasure> treasureToBeAwarded = loser.getTreasures();

        if (treasureToBeAwarded.size() != 0) { //Method calculates what treasures should be added and to who
            for (Treasure t : treasureToBeAwarded
            ) {
                loser.removeTreasure(t);
                if (winner.getTreasures().size() < 2) {
                    winner.addTreasure(t); // Adds treasure to the winner
                } else {
                    Tile[][] gameBoard = GameInitialiser.game.getBoard().getTileMatrix();
                    IslandTile it = (IslandTile) gameBoard[8][8];
                    it.addTreasure(t);
                }
            }
        } else {
            ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = loser.getCrewCards();
            // Decides what crew cards to add to the winner in the event where there is no treasure available.
            ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
            for (CrewCard c: cardsToBeAddedIfTreasureNotPresent
                 ) {
                if (c.getValue() == 1 || c.getValue() == -1){
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c: cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 2 || c.getValue() == -2){
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c: cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 3 || c.getValue() == -3){
                    cardsExchanged.add(c);
                }
            }if (loser.getCrewCards().size() != 0){
                loser.removeCrewCard(cardsExchanged.get(0));
                winner.addCrewCard(cardsExchanged.get(0));
                if (cardsToBeAddedIfTreasureNotPresent.size() > 0){
                    loser.removeCrewCard(cardsExchanged.get(1));
                    winner.addCrewCard(cardsExchanged.get(1));
                }
            }


        }
        //state change to combat?
        //loser moveSequence
    }
}
