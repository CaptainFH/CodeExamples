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
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Port;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Colour;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.CrewCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

/**
 * Class responsible for controlling the Trading system
 * This class allows for the trading between ports to function, it also connects to the FXML
 * Which is responsible for the ui interface that shows up
 *
 * @author Jay Kirkahm (jak77)
 * @version 0.1 Class created
 * @version 0.2  trading functionality added
 * @see Player
 * @see Treasure
 * @see CrewCard
 * @see Port
 */

public class TradingCtrl implements Initializable {

    @FXML
    private Button confirmButton;

    @FXML
    private Label playerOne;
    @FXML
    private Label playerTwo;

    @FXML private ToggleButton playercard00;
    @FXML private ToggleButton playercard01;
    @FXML private ToggleButton playercard02;
    @FXML private ToggleButton playercard03;
    @FXML private ToggleButton playercard04;
    @FXML private ToggleButton playercard05;
    @FXML private ToggleButton playercard06;
    @FXML private ToggleButton playercard10;
    @FXML private ToggleButton playercard11;
    @FXML private ToggleButton playercard12;
    @FXML private ToggleButton playercard13;
    @FXML private ToggleButton playercard14;
    @FXML private ToggleButton playercard15;
    @FXML private ToggleButton playercard16;
    @FXML private ToggleButton playercard20;
    @FXML private ToggleButton playercard21;
    @FXML private ToggleButton playercard22;
    @FXML private ToggleButton playercard23;
    @FXML private ToggleButton playercard24;
    @FXML private ToggleButton playercard25;
    @FXML private ToggleButton playercard26;

    @FXML private ToggleButton playertreasure00;
    @FXML private ToggleButton playertreasure01;
    @FXML private ToggleButton playertreasure02;
    @FXML private ToggleButton playertreasure03;
    @FXML private ToggleButton playertreasure04;
    @FXML private ToggleButton playertreasure05;
    @FXML private ToggleButton playertreasure06;
    @FXML private ToggleButton playertreasure10;
    @FXML private ToggleButton playertreasure11;
    @FXML private ToggleButton playertreasure12;
    @FXML private ToggleButton playertreasure13;
    @FXML private ToggleButton playertreasure14;
    @FXML private ToggleButton playertreasure15;
    @FXML private ToggleButton playertreasure16;
    @FXML private ToggleButton playertreasure20;
    @FXML private ToggleButton playertreasure21;
    @FXML private ToggleButton playertreasure22;
    @FXML private ToggleButton playertreasure23;
    @FXML private ToggleButton playertreasure24;
    @FXML private ToggleButton playertreasure25;
    @FXML private ToggleButton playertreasure26;

    @FXML private ToggleButton portcard00;
    @FXML private ToggleButton portcard01;
    @FXML private ToggleButton portcard02;
    @FXML private ToggleButton portcard03;
    @FXML private ToggleButton portcard04;
    @FXML private ToggleButton portcard05;
    @FXML private ToggleButton portcard06;
    @FXML private ToggleButton portcard10;
    @FXML private ToggleButton portcard11;
    @FXML private ToggleButton portcard12;
    @FXML private ToggleButton portcard13;
    @FXML private ToggleButton portcard14;
    @FXML private ToggleButton portcard15;
    @FXML private ToggleButton portcard16;
    @FXML private ToggleButton portcard20;
    @FXML private ToggleButton portcard21;
    @FXML private ToggleButton portcard22;
    @FXML private ToggleButton portcard23;
    @FXML private ToggleButton portcard24;
    @FXML private ToggleButton portcard25;
    @FXML private ToggleButton portcard26;

    @FXML private ToggleButton porttreasure00;
    @FXML private ToggleButton porttreasure01;
    @FXML private ToggleButton porttreasure02;
    @FXML private ToggleButton porttreasure03;
    @FXML private ToggleButton porttreasure04;
    @FXML private ToggleButton porttreasure05;
    @FXML private ToggleButton porttreasure06;
    @FXML private ToggleButton porttreasure10;
    @FXML private ToggleButton porttreasure11;
    @FXML private ToggleButton porttreasure12;
    @FXML private ToggleButton porttreasure13;
    @FXML private ToggleButton porttreasure14;
    @FXML private ToggleButton porttreasure15;
    @FXML private ToggleButton porttreasure16;
    @FXML private ToggleButton porttreasure20;
    @FXML private ToggleButton porttreasure21;
    @FXML private ToggleButton porttreasure22;
    @FXML private ToggleButton porttreasure23;
    @FXML private ToggleButton porttreasure24;
    @FXML private ToggleButton porttreasure25;
    @FXML private ToggleButton porttreasure26;

    @FXML
    private Label totalDigitPlayer;
    @FXML
    private Label totalDigitPort;

    @FXML
    private ImageView diamond;
    @FXML
    private ImageView ruby;
    @FXML
    private ImageView gold;
    @FXML
    private ImageView pearl;
    @FXML
    private ImageView rum;

    private Player currentPlayer1;
    private Player currentPlayer2;
    private PortTile currentPort;
    private boolean isPlayerTrade = false;
    private Player portsOwner = new Player();


    /**
     *
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            diamond.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\diamond_big.png")));
            ruby.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\ruby_big.png")));
            gold.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\gold_big.png")));
            pearl.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\pearl_big.png")));
            rum.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\rum_big.png")));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            makeTrade(GameBoardCtrl.tradingPlayer,GameBoardCtrl.tradingPort);
        } catch (FileNotFoundException e) {
            System.err.println("feked it something to do with trying to trade with the player and port");
        }

    }

    private void testGeneratorPlayerTrade() throws FileNotFoundException {
        isPlayerTrade = true;
        Player playerA = new Player("John", Colour.BLUE);
        Player playerB = new Player("Jim",Colour.GREEN);
        playerB.setHomePort(new PortTile(Port.AMSTERDAM));

        currentPlayer1 = playerA;
        currentPlayer2 = playerB;

        CrewCard red1 = new CrewCard(-1,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_1_red.png");
        CrewCard red2 = new CrewCard(-2,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_2_red.png");
        CrewCard red3 = new CrewCard(-3,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_3_red.png");
        CrewCard black1 = new CrewCard(1,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_1_black.png");
        CrewCard black2 = new CrewCard(2,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_2_black.png");
        CrewCard black3 = new CrewCard(3,"src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_3_black.png");

        playerA.addCrewCard(red1);
        playerA.addCrewCard(red2);
        playerA.addCrewCard(red2);
        playerA.addCrewCard(red3);
        playerA.addCrewCard(red3);
        playerA.addCrewCard(red3);
        playerA.addCrewCard(red3);
        playerA.addCrewCard(red3);
        playerA.addCrewCard(black2);
        playerA.addTreasure(Treasure.DIAMOND);
        playerA.addTreasure(Treasure.GOLD);

        currentPort = playerB.getPortTile();
        currentPort.setCrewCards(new ArrayList<>());
        currentPort.setTreasurePile(new ArrayList<>());
        currentPort.addTreasure(Treasure.RUM);
        currentPort.addTreasure(Treasure.RUM);
        currentPort.addTreasure(Treasure.RUM);
        currentPort.addTreasure(Treasure.RUM);
        currentPort.addTreasure(Treasure.GOLD);

        makeTrade(playerA,currentPort);
    }

    /**
     *
     * @param player refers to the player
     * @param port refers to teh port
     * @throws FileNotFoundException
     */

    public void makeTrade (Player player, PortTile port) throws FileNotFoundException {

        currentPlayer1 = player;
        currentPort = port;
        Port portname = port.getPort();

        playerOne.setText(player.getName());
        playerTwo.setText(currentPort.getPort().name());

        ArrayList<CrewCard> playerCards = player.getCrewCards();
        ArrayList<ToggleButton> playerCardFrames = getPlayerCardFrames();
        int totalPlayerValue = 0;
        ArrayList<Treasure> playerTreasure = player.getTreasure();
        ArrayList<ToggleButton> playerTreasureFrames = getPlayerTreasureFrames();


        ArrayList<CrewCard> portCards = new ArrayList<>();

        if (port.getPort() == Port.VENICE ||port.getPort() == Port.AMSTERDAM) {
            portCards = port.getCrewCards();
        }else{
            for (Player p : GameInitialiser.game.getPlayerList()){
                if(p.getPortTile().getPort() == portname){
                    portCards = p.getCrewCards();
                    portsOwner = p;
                }
            }
        }

        //Ports crew cards are actually the player who owns the ports cards
        ArrayList<ToggleButton> portCardFrames = getPortCardFrames();
        int totalPortValue = 0;
        ArrayList<Treasure> portTreasure = port.getTreasure();
        ArrayList<ToggleButton> portTreasureFrames = getPortTreasureFrames();

        int debouncer = 0;
        for (CrewCard card:playerCards) {
            int number = Math.abs(card.getValue());
            Image picture = card.getPicture();
            ImageView viewer = new ImageView();
            viewer.setImage(picture);
            playerCardFrames.get(debouncer).setGraphic(viewer);
            totalPlayerValue = totalPlayerValue + number;
            debouncer++;
        }
        while (debouncer < playerCardFrames.size()) {
            playerCardFrames.get(debouncer).setDisable(true);
            debouncer++;
        }

        debouncer = 0;
        if (!isPlayerTrade) {
            for (CrewCard card:portCards) {
                int number = Math.abs(card.getValue());
                Image picture = card.getPicture();
                ImageView viewer = new ImageView();
                viewer.setImage(picture);
                portCardFrames.get(debouncer).setGraphic(viewer);
                totalPortValue = totalPortValue + number;
                debouncer++;
            }
        }
        while (debouncer < portCardFrames.size()) {
            portCardFrames.get(debouncer).setDisable(true);
            debouncer++;
        }

        debouncer = 0;
        for (Treasure treasure:playerTreasure) {
            int value = treasure.getValue();
            Image picture = new Image(new FileInputStream(getURLForTreasure(treasure)));
            ImageView viewer = new ImageView();
            viewer.setImage(picture);
            viewer.setRotate(90);
            viewer.maxHeight(60.0);
            viewer.maxWidth(30.0);
            playerTreasureFrames.get(debouncer).setGraphic(viewer);
            playerTreasureFrames.get(debouncer).maxHeight(60.0);
            playerTreasureFrames.get(debouncer).maxWidth(30.0);
            totalPlayerValue = totalPlayerValue + value;
            debouncer++;
        }
        while (debouncer < playerTreasureFrames.size()) {
            playerTreasureFrames.get(debouncer).setDisable(true);
            debouncer++;
        }

        debouncer = 0;
        for (Treasure treasure:portTreasure) {
            int value = treasure.getValue();
            Image picture = new Image(new FileInputStream(getURLForTreasure(treasure)));
            ImageView viewer = new ImageView();
            viewer.setImage(picture);
            portTreasureFrames.get(debouncer).setGraphic(viewer);
            totalPortValue = totalPortValue + value;
            debouncer++;
        }
        while (debouncer < portTreasureFrames.size()) {
            portTreasureFrames.get(debouncer).setDisable(true);
            debouncer++;
        }
    }

    private ArrayList<ToggleButton> getPlayerCardFrames() {
        ArrayList<ToggleButton> frames = new ArrayList<>();
        frames.add(playercard00);
        frames.add(playercard01);
        frames.add(playercard02);
        frames.add(playercard03);
        frames.add(playercard04);
        frames.add(playercard05);
        frames.add(playercard06);
        frames.add(playercard10);
        frames.add(playercard11);
        frames.add(playercard12);
        frames.add(playercard13);
        frames.add(playercard14);
        frames.add(playercard15);
        frames.add(playercard16);
        frames.add(playercard20);
        frames.add(playercard21);
        frames.add(playercard22);
        frames.add(playercard23);
        frames.add(playercard24);
        frames.add(playercard25);
        frames.add(playercard26);
        return frames;
    }

    private ArrayList<ToggleButton> getPlayerTreasureFrames() {
        ArrayList<ToggleButton> frames = new ArrayList<>();
        frames.add(playertreasure00);
        frames.add(playertreasure01);
        frames.add(playertreasure02);
        frames.add(playertreasure03);
        frames.add(playertreasure04);
        frames.add(playertreasure05);
        frames.add(playertreasure06);
        frames.add(playertreasure10);
        frames.add(playertreasure11);
        frames.add(playertreasure12);
        frames.add(playertreasure13);
        frames.add(playertreasure14);
        frames.add(playertreasure15);
        frames.add(playertreasure16);
        frames.add(playertreasure20);
        frames.add(playertreasure21);
        frames.add(playertreasure22);
        frames.add(playertreasure23);
        frames.add(playertreasure24);
        frames.add(playertreasure25);
        frames.add(playertreasure26);
        return frames;
    }

    private ArrayList<ToggleButton> getPortCardFrames() {
        ArrayList<ToggleButton> frames = new ArrayList<>();
        frames.add(portcard00);
        frames.add(portcard01);
        frames.add(portcard02);
        frames.add(portcard03);
        frames.add(portcard04);
        frames.add(portcard05);
        frames.add(portcard06);
        frames.add(portcard10);
        frames.add(portcard11);
        frames.add(portcard12);
        frames.add(portcard13);
        frames.add(portcard14);
        frames.add(portcard15);
        frames.add(portcard16);
        frames.add(portcard20);
        frames.add(portcard21);
        frames.add(portcard22);
        frames.add(portcard23);
        frames.add(portcard24);
        frames.add(portcard25);
        frames.add(portcard26);
        return frames;
    }

    private ArrayList<ToggleButton> getPortTreasureFrames() {
        ArrayList<ToggleButton> frames = new ArrayList<>();
        frames.add(porttreasure00);
        frames.add(porttreasure01);
        frames.add(porttreasure02);
        frames.add(porttreasure03);
        frames.add(porttreasure04);
        frames.add(porttreasure05);
        frames.add(porttreasure06);
        frames.add(porttreasure10);
        frames.add(porttreasure11);
        frames.add(porttreasure12);
        frames.add(porttreasure13);
        frames.add(porttreasure14);
        frames.add(porttreasure15);
        frames.add(porttreasure16);
        frames.add(porttreasure20);
        frames.add(porttreasure21);
        frames.add(porttreasure22);
        frames.add(porttreasure23);
        frames.add(porttreasure24);
        frames.add(porttreasure25);
        frames.add(porttreasure26);
        return frames;
    }

    /**
     * This method assigns reach treasure an appropriate png
     *
     * @param treasure
     * @return
     */
    private String getURLForTreasure(Treasure treasure) {
        return switch (treasure) {
            case DIAMOND ->
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

    @FXML
    private void updateClicked() {
        int totalPlayerValue = 0;
        int totalPortValue = 0;

        totalPlayerValue += updateClickedAssistantCardPl(getPlayerCardFrames(),currentPlayer1);
        totalPlayerValue += updateClickedAssistantTreasurePl(getPlayerTreasureFrames(),currentPlayer1);
        totalPortValue += updateClickedAssistantCardPo(getPortCardFrames(),currentPort);
        totalPortValue += updateClickedAssistantTreasurePo(getPortTreasureFrames(),currentPort);

        totalDigitPlayer.setText(String.valueOf(totalPlayerValue));
        totalDigitPort.setText(String.valueOf(totalPortValue));

        if (totalPlayerValue == totalPortValue) {
            totalDigitPort.setTextFill(Color.GREEN);
            totalDigitPlayer.setTextFill(Color.GREEN);
            confirmButton.setDisable(false);
        } else {
            totalDigitPort.setTextFill(Color.RED);
            totalDigitPlayer.setTextFill(Color.RED);
            confirmButton.setDisable(true);
        }

        System.out.println(totalPlayerValue);
        System.out.println(totalPortValue);
    }
    private int updateClickedAssistantCardPl(ArrayList<ToggleButton> frames,Player user) {
        int returnValue = 0;
        int indexer = 0;
        ArrayList<CrewCard> userCards = user.getCrewCards();
        for (ToggleButton item : frames) {
            if (item.isSelected()) {
               int value = Math.abs(userCards.get(indexer).getValue());
                returnValue += value;
            }
            indexer++;
        }
        return returnValue;
    }

    private int updateClickedAssistantCardPo(ArrayList<ToggleButton> frames, PortTile port) {
        int returnValue = 0;
        int indexer = 0;
        ArrayList<CrewCard> userCards = port.getCrewCards();
        for (ToggleButton item:frames) {
            if (item.isSelected()) {
                int value = Math.abs(userCards.get(indexer).getValue());
                returnValue += value;
            }
            indexer++;
        }
        return returnValue;
    }

    private int updateClickedAssistantTreasurePl(ArrayList<ToggleButton> frames, Player player) {
        int returnValue = 0;
        int indexer = 0;
        ArrayList<Treasure> userCards = player.getTreasure();
        for (ToggleButton item:frames) {
            if (item.isSelected()) {
                int value = Math.abs(userCards.get(indexer).getValue());
                returnValue += value;
            }
            indexer++;
        }
        return returnValue;
    }

    private int updateClickedAssistantTreasurePo(ArrayList<ToggleButton> frames, PortTile port) {
        int returnValue = 0;
        int indexer = 0;
        ArrayList<Treasure> userCards = port.getTreasure();
        for (ToggleButton item:frames) {
            if (item.isSelected()) {
                int value = Math.abs(userCards.get(indexer).getValue());
                returnValue += value;
            }
            indexer++;
        }
        return returnValue;
    }

    @FXML
    private void closeWindowAction() {
        int indexer = 0;
//        if (isPlayerTrade) {
//            for (ToggleButton item : getPlayerCardFrames()) {
//                if (item.isSelected()) {
//                    CrewCard card = currentPlayer1.getCrewCards().get(indexer);
//                    currentPlayer1.removeCrewCard(card);
//                    currentPlayer2.addCrewCard(card);
//                } else indexer++;
//            }
//        } else {
//            for (ToggleButton item : getPlayerCardFrames()) {
//                if (item.isSelected()) {
//                    CrewCard card = currentPlayer1.getCrewCards().get(indexer);
//                    currentPlayer1.removeCrewCard(card);
//                    currentPort.addCrewCard(card);
//                } else indexer++;
//            }
//        }
//        indexer = 0;
        for (ToggleButton item:getPlayerTreasureFrames()) {
            if (item.isSelected()) {
                Treasure treasure = currentPlayer1.getTreasure().get(indexer);
                currentPlayer1.removeTreasure(treasure);
                currentPort.addTreasure(treasure);
            } else indexer++;
        }
        indexer = 0;
        for (ToggleButton item:getPortCardFrames()) {
            if (item.isSelected()) {
                CrewCard card = currentPort.getCrewCards().get(indexer);
                if (currentPort.getPort() == Port.VENICE ||currentPort.getPort() == Port.AMSTERDAM) {
                    currentPort.removeCrewCard(card);
                }else{
                    portsOwner.removeCrewCard(card);
                }
                currentPlayer1.addCrewCard(card);
            } else indexer++;
        }
        indexer = 0;
        for (ToggleButton item:getPortTreasureFrames()) {
            if (item.isSelected()) {
                Treasure treasure = currentPort.getTreasure().get(indexer);
                currentPort.removeTreasure(treasure);
                currentPlayer1.addTreasure(treasure);
            } else indexer++;
        }
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
        System.out.println(currentPlayer1.getCrewCards());
//        System.out.println(currentPlayer2.getCrewCards());
        System.out.println(currentPlayer1.getTreasure());
        System.out.println(currentPlayer2.getTreasure());
    }
}
