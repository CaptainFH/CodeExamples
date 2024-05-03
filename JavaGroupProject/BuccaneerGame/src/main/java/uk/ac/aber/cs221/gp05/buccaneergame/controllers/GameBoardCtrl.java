package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.Position;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Island;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Tile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.*;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.State;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateEvent;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser.game;

/**
 * Class responsible for the management of the game board
 * This class checks what state the game is in and changes it as the game requires
 * It also controls events such as player movement and rotation
 *
 * @author Igor Daniualk (igd4)
 * @author Jack Gannon (jag78)
 * @author Fernando Rodriguez (fet8)
 * @version 0.1 Class Created
 * @version 0.2 Method for the checking of the state implemented
 * @see Player
 * @see Treasure
 * @see ChanceCard
 * @see CrewCard
 * */
public class GameBoardCtrl implements Initializable { // Method gets the current game state and switches it to teh turn state
    StateManager turn = game.getGamestate();
    State currentState = turn.getCurrentState();
    ArrayList<Position> toGenerateRedBoxes = new ArrayList<>();
    ArrayList<Position> newRotations = new ArrayList<>();
    Tile[][] currentBoard = game.getBoard().getTileMatrix();
    AttackingCtrl attackingCtrl = new AttackingCtrl();
    public static Player winner;
    public static Player loser;
    public static int winnerFPB;
    public static int loserFPB;
    public static Player tradingPlayer;
    public static PortTile tradingPort;

    private int previousTurn;
    private StateEvent sE = new StateEvent();



    @FXML
    private GridPane board;

    @FXML
    private Button moveButton;

    @FXML
    private Button rotateButton;

    @FXML
    private Button settingsButton;


    /**
     * Method for movement, detecting if a tile has been clicked, this also
     * checks which state the game is and triggers appropriate events
     *
     * @param event check for mouse events, in this case if a tile was clicked
     * @throws Exception
     */
    @FXML
    void clickedTile(MouseEvent event) throws Exception {
        int x = (int) event.getX() / 54;
        int y = (int) event.getY() / 54;
        Position newPlayerpos = new Position(x, y);
        System.out.println(game.getGamestate().getCurrentState().name());
        switch (game.getGamestate().getCurrentState()) {
            case COMBAT -> {game.getGamestate().setCurrentState(State.MOVE);
            game.getGamestate().setTurnNumber(previousTurn-1);}
            case PORT -> {//opens trading menu
                System.out.println("IN PORT CASE");
                tradingPlayer = game.findCurrentPlayer();
                int x1 = game.findCurrentPlayer().getLocation().getX();
                int y1 = game.findCurrentPlayer().getLocation().getY();
                Tile[][] gameboard = game.getBoard().getTileMatrix();
                tradingPort = ((PortTile) (gameboard[x1][y1]));

                launchTrade();
            }
            case TREASUREISLAND -> {//opens chance manu
            }
            case FLATISLAND -> { // takes all the stuff from flat island and puts it in the player
            }
            case MOVE -> {
                System.out.println("in MOVECASE");
                for (Position p : toGenerateRedBoxes) {
                    if (newPlayerpos.getX() == p.getX() && newPlayerpos.getY() == p.getY()) {
                        Position clickedPos = new Position(x, y);

                        for (Player player : game.getPlayerList()
                        ) {
                            if (!player.equals(game.findCurrentPlayer())) {
                                if (player.getLocation().getX() == clickedPos.getX() && player.getLocation().getY() == clickedPos.getY()) {
                                    attackingCtrl.attackSequence(game.findCurrentPlayer(), player);
                                    launchFXML();
                                    if(loser.getName().equals(game.findCurrentPlayer().getName())){
                                        board.setDisable(true);
                                        rotateButton.setDisable(false);
                                        moveButton.setDisable(false);
                                        game.getGamestate().setCurrentState(State.COMBAT);
                                        game.findCurrentPlayer().setLocation(clickedPos);
                                    } else {
                                        board.setDisable(true);
                                        rotateButton.setDisable(false);
                                        moveButton.setDisable(false);
                                        game.getGamestate().setCurrentState(State.COMBAT);
                                        game.findCurrentPlayer().setLocation(clickedPos);
                                        previousTurn = game.getGamestate().getTurnNumber();

                                        int toChangeTurns = game.getPlayerList().indexOf(loser);
                                        game.getGamestate().setTurnNumber(toChangeTurns);

                                    }
                                }


                            }
                            if (!game.getGamestate().getCurrentState().equals(State.COMBAT)){
                                game.findCurrentPlayer().setLocation(clickedPos);
                            }
                            board.getChildren().clear();
                            updatePlayersInfo();
                            createPlayerShip();
                            if (game.getGamestate().getCurrentState().equals(State.COMBAT)){
                                break;
                            }
                        }
                        if (game.getGamestate().getCurrentState().equals(State.COMBAT)){
                            break;
                        } else{
                        board.getChildren().clear();
                        board.setDisable(true);
//
                        createPlayerShip();

                        System.out.println("CHECKING SURROUNDINGS");
                        checkSurroundings(game.findCurrentPlayer());
                        switch (game.getGamestate().getCurrentState()) {
                            case TREASUREISLAND -> {launchCC();
                            }
                            case FLATISLAND -> {
                            } //DO FLAT ISLAND stuff
                        }
                        System.out.println("Finished check");
                        System.out.println("Checking for port");
                        if (isPort() && doesPlayerOwn()) {
                            //deposit all treasure
                            int x2 = game.findCurrentPlayer().getLocation().getX();
                            int y2 = game.findCurrentPlayer().getLocation().getY();
                            Tile[][] gameboard = game.getBoard().getTileMatrix();
                            PortTile port = ((PortTile) (gameboard[x2][y2]));
                            for (Treasure t : game.findCurrentPlayer().getTreasure()) {
                                port.addTreasure(t);
                            }
                            game.findCurrentPlayer().setTreasures(new ArrayList<>());


                            if (game.findCurrentPlayer().getPortTile().getTotalTreasureValue() > 20) { // WINNING CONDITION CURRENTLY 20
                                launchEND();
                                game.getGamestate().setCurrentState(State.ENDGAME);
                                break;
                            }
                        }
                        if (isPort() && !doesPlayerOwn()) {
                            game.getGamestate().setCurrentState(State.PORT);
                            tradingPlayer = game.findCurrentPlayer();
                            int x1 = game.findCurrentPlayer().getLocation().getX();
                            int y1 = game.findCurrentPlayer().getLocation().getY();
                            Tile[][] gameboard = game.getBoard().getTileMatrix();
                            tradingPort = ((PortTile) (gameboard[x1][y1]));
                            launchTrade();
                        }
                        System.out.println("Finished checking for port");

                        game.getGamestate().setCurrentState(State.ROTATE);
                        moveButton.setDisable(true);
                        newRotations = game.getBoard().checkPossibleRotations(game.findCurrentPlayer());
                        for (Position r : newRotations
                        ) {
                            board.add(createRedPane(), r.getX(), r.getY());
                        }
                        board.setDisable(false);
                        rotateButton.setDisable(true);
                        break;
                    }}
                }
            }
            case ROTATE -> {
                System.out.println("In rotateCase");
                for (Position p : newRotations
                ) {
                    if (newPlayerpos.getX() == p.getX() && newPlayerpos.getY() == p.getY()) {
                        Position clickedPos = new Position(x, y);
                        clickedPos.setX(clickedPos.getX() - game.findCurrentPlayer().getLocation().getX());
                        clickedPos.setY(clickedPos.getY() - game.findCurrentPlayer().getLocation().getY());
                        Direction newDirection = Direction.N;
                        switch (clickedPos.getX()) {
                            case -1 -> {
                                if (clickedPos.getY() == -1) {
                                    newDirection = Direction.NW;
                                    break;
                                }
                                if (clickedPos.getY() == 0) {
                                    newDirection = Direction.W;
                                    break;
                                }
                                if (clickedPos.getY() == 1) {
                                    newDirection = Direction.SW;
                                    break;
                                }
                            }
                            case 0 -> {
                                if (clickedPos.getY() == -1) {
                                    newDirection = Direction.N;
                                    break;
                                }
                                if (clickedPos.getY() == 1) {
                                    newDirection = Direction.S;
                                    break;
                                }

                            }
                            case 1 -> {
                                if (clickedPos.getY() == -1) {
                                    newDirection = Direction.NE;
                                    break;
                                }
                                if (clickedPos.getY() == 0) {
                                    newDirection = Direction.E;
                                    break;
                                }
                                if (clickedPos.getY() == 1) {
                                    newDirection = Direction.SE;
                                    break;
                                }
                            }

                        }
                        game.findCurrentPlayer().setDirection(newDirection);
                        board.getChildren().clear();
                        board.setDisable(true);
//                        for (int i = 0; i < 4; i++) {
//                            board.add(createBlackPane(),game.getPlayerList().get(i).getLocation().getX(),game.getPlayerList().get(i).getLocation().getY());
//                        }
//                        board.add(createGreenPane(),game.findPlayerBefore().getLocation().getX(),game.findPlayerBefore().getLocation().getY());
                        createPlayerShip();
                        //board.add(createBlackPane(), game.findCurrentPlayer().getLocation().getX(), game.findCurrentPlayer().getLocation().getY());
                        moveButton.setDisable(false);
                        rotateButton.setDisable(false);
                        turn.addTurn();
                        checkSurroundings(game.findCurrentPlayer());
                        updatePlayersInfo();
                        break;

                    }
                }
            }
        }
        System.out.println(game.getGamestate().getCurrentState());
        if (game.getGamestate().getCurrentState() == State.ENDGAME) {
            Stage mainWindow = (Stage) board.getScene().getWindow();
            mainWindow.close();
        }
    }

    private void launchCC() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChanceCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 800);
        stage.setTitle("Chance!");
        stage.setScene(scene);
        stage.show();
    }

    private void launchEND() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Winner!");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isPort() {
        int x = game.findCurrentPlayer().getLocation().getX();
        int y = game.findCurrentPlayer().getLocation().getY();
        Tile[][] gameboard = game.getBoard().getTileMatrix();
        return gameboard[x][y] instanceof PortTile;
    }

    private boolean doesPlayerOwn() {
        int x = game.findCurrentPlayer().getLocation().getX();
        int y = game.findCurrentPlayer().getLocation().getY();
        Tile[][] gameboard = game.getBoard().getTileMatrix();
        PortTile portTile = (PortTile) gameboard[x][y];
        return portTile.getPort() == game.findCurrentPlayer().getPortTile().getPort();
    }

    private void launchTrade() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TradeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Trade!");
        stage.setScene(scene);
        stage.show();
    }

    private void loserTurnfunc() {
        //rotate
        //move
        //rotate
    }

    private void checkSurroundings(Player currentPlayer) {
        Island rtdIsle = game.getBoard().checkSurroundings(currentPlayer.getLocation());
        switch (rtdIsle) {
            case FLAT -> game.getGamestate().setCurrentState(State.FLATISLAND);
            case PIRATE -> {
            }//DO NOTHING;
            case TREASURE -> game.getGamestate().setCurrentState(State.TREASUREISLAND);
            default -> {
            } // DO NOTHING
        }
    }

    private void launchFXML() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 606, 380);
        stage.setTitle("Battle!");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void goToSettings(ActionEvent event) throws Exception {
    }

    @FXML
    void testAnything(ActionEvent event) {

    }

    Pane createRedPane() {
        Pane tileTest = new Pane();
        tileTest.setStyle(" -fx-background-color: red;\n" +
                "    -fx-width: 54px;\n" +
                "    -fx-height: 54px; -fx-opacity: 0.5;");
        return tileTest;
    }

    Pane createBlackPane() {
        Pane tileTest = new Pane();
        tileTest.setStyle(" -fx-background-color: black;\n" +
                "    -fx-width: 54px;\n" +
                "    -fx-height: 54px;");
        return tileTest;
    }

    Pane createGreenPane() {
        Pane tileTest = new Pane();
        tileTest.setStyle(" -fx-background-color: green;\n" +
                "    -fx-width: 54px;\n" +
                "    -fx-height: 54px;");
        return tileTest;
    }

    @FXML
    void move(ActionEvent event) {
        board.setDisable(false);
        rotateButton.setDisable(true);
        moveButton.setDisable(true);


        Position tileToBeChecked = new Position();
        tileToBeChecked = game.findCurrentPlayer().getLocation();
        if (currentBoard[tileToBeChecked.getX()][tileToBeChecked.getY()].isPort()) {
            toGenerateRedBoxes = game.getBoard().checkPossibleMovesInPort(game.findCurrentPlayer());
            game.getGamestate().setCurrentState(State.PORT);
        } else {
            toGenerateRedBoxes = game.getBoard().checkPossibleMoves(game.findCurrentPlayer());
        }
        for (Position p : toGenerateRedBoxes
        ) {
            board.add(createRedPane(), p.getX(), p.getY());
        }
        game.getGamestate().setCurrentState(State.MOVE);


        //grey out rotate
        //check to see if its in port
        //highlight the options where they can move they can only move the direction they are facing
        //wait for a click on rotate or move
        //check
        //move
        //change state to rotate
        //check for positioning relative to islands for island state or pirate island

    }

    @FXML
    void rotatePlayer(ActionEvent event) {
        moveButton.setDisable(true);
        rotateButton.setDisable(true);
        newRotations = game.getBoard().checkPossibleRotations(game.findCurrentPlayer());
        for (Position r : newRotations
        ) {
            board.add(createRedPane(), r.getX(), r.getY());
        }
        board.setDisable(false);
        game.getGamestate().setCurrentState(State.ROTATE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updatePlayersInfo();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        board.setDisable(true);
        board.getChildren().clear();
        try {
            createPlayerShip();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //System.out.printf(game.getPlayerList().get(0).toString());
    //Function to actually put the players in the correct places and orientations
    //gameStart();


    @FXML
    public Label currentPlayerName;
    @FXML
    public ImageView currentPlayerImage;

    @FXML
    public Label currentPlayerAttackingPower;

    @FXML
    public Label currentPlayerMovementPower;

    @FXML
    public ImageView currentPlayerTreasure1;

    @FXML
    public ImageView currentPlayerTreasure2;

    @FXML
    public Label currentPlayerPoints;

    @FXML
    public ImageView player2Image;

    @FXML
    public Label player2Name;

    @FXML
    public Label player2Points;

    @FXML
    public ImageView player2Treasure1;

    @FXML
    public ImageView player2Treasure2;

    @FXML
    public ImageView player3Image;

    @FXML
    public Label player3Name;

    @FXML
    public Label player3Points;

    @FXML
    public ImageView player3Treasure1;

    @FXML
    public ImageView player3Treasure2;

    @FXML
    public ImageView player4Image;

    @FXML
    public Label player4Name;

    @FXML
    public Label player4Points;

    @FXML
    public ImageView player4Treasure1;

    @FXML
    public ImageView player4Treasure2;

    @FXML
    public Label player2Card1;
    @FXML
    public Label player2Card2;
    @FXML
    public Label player2Card3;

    @FXML
    public Label player3Card1;
    @FXML
    public Label player3Card2;
    @FXML
    public Label player3Card3;

    @FXML
    public Label player4Card1;
    @FXML
    public Label player4Card2;
    @FXML
    public Label player4Card3;


    public void updatePlayersInfo() throws FileNotFoundException {
        Player currentInput = game.findCurrentPlayer();
        ArrayList<Player> playerList = game.getPlayerList();
        int turn = playerList.indexOf(currentInput);
        currentPlayerName.setText(currentInput.getName() + "' turn");
        setSideMenuBoat(currentPlayerImage, currentInput.getColour());
        currentPlayerAttackingPower.setText(String.valueOf(currentInput.getFightingPower()));
        currentPlayerMovementPower.setText(String.valueOf(currentInput.getCrewPower()));
        currentPlayerPoints.setText(String.valueOf(currentInput.getPortTile().getTotalTreasureValue()));
        setCurrentPlayerCards();
        setPlayerTreasures(currentPlayerTreasure1, currentPlayerTreasure2, currentInput.getTreasures());
        ImageView[] otherPlayerImages = new ImageView[]{player2Image, player2Treasure1, player2Treasure2, player3Image, player3Treasure1, player3Treasure2, player4Image, player4Treasure1, player4Treasure2};
        Label[] otherPlayerLabels = new Label[]{player2Name, player2Points, null, player3Name, player3Points, null, player4Name, player4Points};
        Label[] settingCardsToPlayer = new Label[]{player2Card1,player2Card2,player2Card3,player3Card1,player3Card2,player3Card3,player4Card1,player4Card2,player4Card3};
        for (int i = 0; i < 9; i += 3) {
            turn++;
            if (turn == 4) {
                turn = 0;
            }
            setSideMenuBoat(otherPlayerImages[i], playerList.get(turn).getColour());
            setPlayerTreasures(otherPlayerImages[i + 1], otherPlayerImages[i + 2], playerList.get(turn).getTreasure());
            otherPlayerImages[i + 1].resize(98, 150);
            otherPlayerImages[i + 2].resize(98, 150);
            otherPlayerLabels[i].setText(playerList.get(turn).getName());
            otherPlayerLabels[i + 1].setText(String.valueOf(playerList.get(turn).getPortTile().getTotalTreasureValue()));
            setPlayerCards(settingCardsToPlayer[i], playerList.get(turn),1);
            setPlayerCards(settingCardsToPlayer[i+1], playerList.get(turn),2);
            setPlayerCards(settingCardsToPlayer[i+2], playerList.get(turn),3);
        }
    }

    private void setSideMenuBoat(ImageView toBeSet, Colour shipColour) throws FileNotFoundException {
        if (!(shipColour == null)) {
            switch (shipColour) {
                case GREEN -> toBeSet.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_green_big.png")));
                case YELLOW -> toBeSet.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_yellow_big.png")));
                case BLUE -> toBeSet.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_blue_big.png")));
                case ORANGE -> toBeSet.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_orange_big.png")));
            }
        } else {
            toBeSet.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/selectPlayerView/boat_default_big.png")));
        }
    }

    private void setPlayerTreasures(ImageView firstTreasureImage, ImageView secondTreasureImage, ArrayList<Treasure> playerTreasure) throws FileNotFoundException {
        if (!(playerTreasure.size() == 0)) {
            if (!(playerTreasure.get(0) == null)) {
                firstTreasureImage.setImage(new Image(new FileInputStream(findTreasure(playerTreasure.get(0)))));
            }
            if (playerTreasure.size() == 2) {
                if (!(playerTreasure.get(1) == null)) {
                    secondTreasureImage.setImage(new Image((new FileInputStream(findTreasure(playerTreasure.get(1))))));
                }
            } else {
                secondTreasureImage.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/sadge.png")));
            }
        } else {
            firstTreasureImage.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/sadge.png")));
            secondTreasureImage.setImage(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/sadge.png")));
        }
    }

    private String findTreasure(Treasure toBeFound) {
        switch (toBeFound) {
            case RUM -> {
                return "src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/rum_big.png";
            }
            case GOLD -> {
                return "src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/gold_big.png";
            }
            case RUBY -> {
                return "src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/ruby_big.png";
            }
            case PEARL -> {
                return "src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/pearl_big.png";
            }
            case DIAMOND -> {
                return "src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/diamond_big.png";
            }
        }
        return "";
    }

    //PlayerDirection needs to be renamed
    private void rotateShip(ImageView ship, Player toBeRotated) {
        switch (toBeRotated.getDirection()) {
            case N:
                ship.setRotate(0);
                break;
            case NE:
                ship.setRotate(45);
                break;
            case E:
                ship.setRotate(90);
                break;
            case SE:
                ship.setRotate(135);
                break;
            case S:
                ship.setRotate(180);
                break;
            case SW:
                ship.setRotate(225);
                break;
            case W:
                ship.setRotate(270);
                break;
            case NW:
                ship.setRotate(315);
                break;
        }
    }

    private void createPlayerShip() throws FileNotFoundException {
        Player[] listOfPlayers = game.getPlayerList().toArray(new Player[0]);
        for (int i = 0; i < 4; i++) {
            ImageView newShip = new ImageView(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/boat_default_small.png")));
            switch (listOfPlayers[i].getColour()) {
                case GREEN -> {
                    newShip = new ImageView(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/boat_green_small.png")));
                }
                case YELLOW -> {
                    newShip = new ImageView(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/boat_yellow_small.png")));
                }
                case BLUE -> {
                    newShip = new ImageView(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/boat_blue_small.png")));
                }
                case ORANGE -> {
                    newShip = new ImageView(new Image(new FileInputStream("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/gameBoard/boat_orange_small.png")));
                }
            }
            board.add(newShip, listOfPlayers[i].getLocation().getX(), listOfPlayers[i].getLocation().getY());
            rotateShip(newShip, listOfPlayers[i]);
        }
    }

    private void updateAssets() {

    }
    @FXML
    public Label currentPlayerRedCard1;
    @FXML
    public Label currentPlayerRedCard2;
    @FXML
    public Label currentPlayerRedCard3;
    @FXML
    public Label currentPlayerBlackCard1;
    @FXML
    public Label currentPlayerBlackCard2;
    @FXML
    public Label currentPlayerBlackCard3;


    private void setCurrentPlayerCards(){
        Label[] redCardLabels = new Label[]{currentPlayerRedCard1,currentPlayerRedCard2,currentPlayerRedCard3};
        Label[] blackCardLabels = new Label[]{currentPlayerBlackCard1,currentPlayerBlackCard2,currentPlayerBlackCard3};
        for (int i = 1; i < 4; i++){
            redCardLabels[i-1].setText(String.valueOf(cardAmmount(game.findCurrentPlayer(),i)));
            blackCardLabels[i-1].setText(String.valueOf(cardAmmount(game.findCurrentPlayer(),(i*-1))));
        }
    }
    private void setPlayerCards(Label toBeSet, Player beenRead, int cardSetting){
        toBeSet.setText(String.valueOf(cardAmmountNonSpecific(beenRead, cardSetting)));
    }
    //Positive cardsWanted indicate red cards while black are indicated by negative values
    private int cardAmmount(Player beenRead,int cardWanted){
        int total = 0;
        ArrayList<CrewCard> playerCards= beenRead.getCrewCards();
        for(int i = 0; i < playerCards.size(); i++){
            if (playerCards.get(i).getValue() == cardWanted){
                total++;
            }
        }
        return total;
    }
    private int cardAmmountNonSpecific(Player beenRead,int cardWanted){
        int total = 0;
        ArrayList<CrewCard> playerCards= beenRead.getCrewCards();
        for(int i = 0; i < playerCards.size(); i++){
            if (Math.abs(playerCards.get(i).getValue()) == cardWanted){
                total++;
            }
        }
        return total;
    }
}