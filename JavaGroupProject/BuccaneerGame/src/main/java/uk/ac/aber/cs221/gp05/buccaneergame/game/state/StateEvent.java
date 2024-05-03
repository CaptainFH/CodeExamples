/**
 * This class contains the methods for each chance card once a chance card is selected the correct method executes the contents
 *
 * @author Parsa Daneshwar Haghighi
 * @version 0.1
 * @date 02/05/2022
 * Copyright (c) 2021 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.gp05.buccaneergame.game.state;
import javafx.fxml.FXMLLoader;
import javafx.stage.Popup;
import uk.ac.aber.cs221.gp05.buccaneergame.controllers.ChanceTradeEvent;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.GameBoard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.Position;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Port;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Tile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.*;
import static uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser.game;

public class StateEvent {

    //All these methods will have the correct chance card loaded

    //Complete code in methods when ready

    //Methods

    /**
     * switches into the appropriate chance card function
     *
     * @param chanceCardID appropriate Chance Card ID
     */
    public static void selectChanceCard(int chanceCardID, boolean choseCards) throws Exception {
        switch (chanceCardID) {
            //case 1 -> chanceCard01(GameInitialiser.game.getBoard(), GameInitialiser.game.findCurrentPlayer());
            case 2 -> chanceCard02(GameInitialiser.game.findCurrentPlayer(), game.getPlayerList());
            case 3 -> chanceCard03(GameInitialiser.game.getBoard(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            case 4 -> chanceCard04(GameInitialiser.game.getBoard(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            case 5 -> chanceCard05(GameInitialiser.game.getBoard(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            //case 6 -> chanceCard06(GameInitialiser.game.getBoard(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            //case 7 -> chanceCard07(GameInitialiser.game.findCurrentPlayer());
            case 8 -> chanceCard08(GameInitialiser.game.getFlatIslandTreasure(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            case 9 -> chanceCard09(GameInitialiser.game.getFlatIslandTreasure(), GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            case 10 -> chanceCard10(GameInitialiser.game.getPirateIslandCrewCards(), GameInitialiser.game.findCurrentPlayer());
            case 11 -> chanceCard11(GameInitialiser.game.findCurrentPlayer(), choseCards);
            case 12 -> chanceCard12(GameInitialiser.game.findCurrentPlayer(), choseCards);
            case 13 -> chanceCard13(GameInitialiser.game.findCurrentPlayer(), choseCards);
            case 14 -> chanceCard14(GameInitialiser.game.findCurrentPlayer(), choseCards);
            //case 15 -> chanceCard15(GameInitialiser.game.findCurrentPlayer());
            case 16 -> chanceCard16(GameInitialiser.game.findCurrentPlayer());
            case 17 -> chanceCard17(GameInitialiser.game.findCurrentPlayer());
            case 18 -> chanceCard18(GameInitialiser.game.findCurrentPlayer());
            case 19 -> chanceCard19(GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());
            case 20 -> chanceCard20(GameInitialiser.game.findCurrentPlayer());
            //case 21 -> chanceCard21(GameInitialiser.game.findCurrentPlayer());
            case 22 -> chanceCard22(GameInitialiser.game.getPlayerList(), GameInitialiser.game.getPirateIslandCrewCards());
            //case 23 -> chanceCard23(GameInitialiser.game.findCurrentPlayer());
            //case 24 -> chanceCard24(GameInitialiser.game.findCurrentPlayer());
            //case 25 -> chanceCard25();
            //case 26 -> chanceCard26();
            case 27 -> chanceCard27(GameInitialiser.game.findCurrentPlayer(), choseCards);
            //case 28 -> chanceCard28(GameInitialiser.game.findCurrentPlayer(), GameInitialiser.game.getPirateIslandCrewCards());


            default -> System.out.println("selectChanceCard Default");
        }
    }

    /**
     * might be necessary in different methods
     *
     * @param board the game board
     * @param coordinates to check if the players on
     * @return true if there is NOT a player on the tile, false otherwise
     */
    public static boolean checkIfTileFree(GameBoard board, Position coordinates) {
        //loops through the players and sees if there is already a player in it
        Tile[][] position = board.getTileMatrix();
        return (position[coordinates.getX()][coordinates.getY()] == null);
    }

    /**
     * selects this chance card (ship is blown 5 spaces)
     *
     * @param board    appropriate GameBoard name
     * @param tookCard appropriate Player who took card name
     */
    public static void chanceCard01(GameBoard board, Player tookCard) throws Exception {
        Position oldPosition = tookCard.getLocation();
        Position newPosition = new Position(oldPosition.getX(), oldPosition.getY());
        Direction oldDirection = tookCard.getDirection();
        Direction newDirection;
        switch (oldDirection) {
            case N -> newDirection = Direction.S;
            case NE -> newDirection = Direction.SW;
            case E -> newDirection = Direction.W;
            case SE -> newDirection = Direction.NW;
            case S -> newDirection = Direction.N;
            case SW -> newDirection = Direction.NE;
            case W -> newDirection = Direction.E;
            case NW -> newDirection = Direction.SE;
            default -> throw new Exception();
        }
        //N,NE,E,SE,S,SW,W,NW
        switch (newDirection) {
            case N -> {
                newPosition.setX(newPosition.getX() - 5);
            }
            case NE -> {
                newPosition.setX(newPosition.getX() - 5);
                newPosition.setY(newPosition.getY() + 5);
            }
            case E -> {
                newPosition.setY(newPosition.getY() + 5);
            }
            case SE -> {
                newPosition.setX(newPosition.getX() + 5);
                newPosition.setY(newPosition.getY() + 5);
            }
            case S -> {
                newPosition.setX(newPosition.getX() + 5);
            }
            case SW -> {
                newPosition.setX(newPosition.getX() + 5);
                newPosition.setY(newPosition.getY() - 5);
            }
            case W -> {
                newPosition.setY(newPosition.getY() - 5);
            }
            case NW -> {
                newPosition.setX(newPosition.getX() - 5);
                newPosition.setY(newPosition.getY() - 5);

            }
            default -> throw new Exception("Invalid direction");
        }
        if (tookCard.getCrewCards().size() < 4) {
            //not yet implemented

            Queue<CrewCard> cardsToBeTaken = game.getCrewCardQueue();
            tookCard.addCrewCard(cardsToBeTaken.poll());
            tookCard.addCrewCard(cardsToBeTaken.poll());
            tookCard.addCrewCard(cardsToBeTaken.poll());
            tookCard.addCrewCard(cardsToBeTaken.poll());
            //to be uncommented
        }
        //get the direction from user input
        tookCard.rotatePlayer(tookCard, Direction.N);
    }


    /**
     * selects this chance card (take 3 cards from another player)
     *
     * @param tookCard   appropriate Player who took card name
     * @param allPlayers appropriate arraylist containing all players in the game
     */
    public static void chanceCard02(Player tookCard, ArrayList<Player> allPlayers) {
        Random rand = new Random();
        int random = rand.nextInt(4);
        Player chosen = allPlayers.get(random);

        while (chosen == tookCard) {
            random = rand.nextInt(4);
            chosen = allPlayers.get(random);
        }
        ArrayList<CrewCard> cards = chosen.getCrewCards();

        if (chosen.getCrewCards().size() < 3) {
            for (int i = 0; i < chosen.getCrewCards().size(); i++) {
                CrewCard cardToAdd = cards.get(i);
                chosen.removeCrewCard(cardToAdd);
                tookCard.addCrewCard(cardToAdd);
            }
        } else {
            for (int j = 0; j < 3; j++) {
                CrewCard cardToAdd = cards.get(j);
                chosen.removeCrewCard(cardToAdd);
                tookCard.addCrewCard(cardToAdd);
            }

        }
    }

    /**
     * selects this chance card (blown to Mud Bay)
     *
     * @param board             appropriate GameBoard name
     * @param tookCard          appropriate Player who took card name
     * @param pirateIslandCards arraylist of crew cards on Pirate Island
     */
    public static void chanceCard03(GameBoard board, Player tookCard, ArrayList<CrewCard> pirateIslandCards) {
        Position mudBay = new Position(19, 19);
        //this method moves player directly to chosen tile on the game board
        board.teleport(tookCard, mudBay);
        if (tookCard.getCrewCards().size() <= 3) {
            for (int i = 0; i < 4; i++) {
                CrewCard cardToAdd = pirateIslandCards.get(i);
                tookCard.removeCrewCard(cardToAdd);
                tookCard.addCrewCard(cardToAdd);
            }
        }
    }

    /**
     * selects this chance card (blown to Cliff Creek)
     *
     * @param board             appropriate GameBoard name
     * @param tookCard          appropriate Player who took card name
     * @param pirateIslandCards arraylist of crew cards on Pirate Island
     */
    public static void chanceCard04(GameBoard board, Player tookCard, ArrayList<CrewCard> pirateIslandCards) {
        Position cliffCreek = new Position(19, 19);
        //this method moves player directly to chosen tile on the game board
        board.teleport(tookCard, cliffCreek);
        if (tookCard.getCrewCards().size() <= 3) {
            for (int i = 0; i < 4; i++) {
                CrewCard cardToAdd = pirateIslandCards.get(i);
                tookCard.removeCrewCard(cardToAdd);
                tookCard.addCrewCard(cardToAdd);
            }
        }
    }

    /**
     * selects this chance card (blown to home port)
     *
     * @param board             appropriate GameBoard name
     * @param tookCard          appropriate Player who took card name
     * @param pirateIslandCards arraylist of crew cards on Pirate Island
     */
    public static void chanceCard05(GameBoard board, Player tookCard, ArrayList<CrewCard> pirateIslandCards) {
        PortTile homePort = tookCard.getPortTile();
        Position newPosition;
        if (homePort.getPort() == Port.LONDON) {
            newPosition = new Position(0, 6);
            tookCard.setLocation(newPosition);
            board.teleport(tookCard, newPosition);

        } else if (homePort.getPort() == Port.GENOA) {
            newPosition = new Position(6, 19);
            tookCard.setLocation(newPosition);
            board.teleport(tookCard, newPosition);

        } else if (homePort.getPort() == Port.MARSEILLES) {
            newPosition = new Position(19, 13);
            tookCard.setLocation(newPosition);
            board.teleport(tookCard, newPosition);

        } else if (homePort.getPort() == Port.CADIZ) {
            newPosition = new Position(13, 0);
            tookCard.setLocation(newPosition);
            board.teleport(tookCard, newPosition);
        }
        //this method moves player directly to chosen tile on the game board

        if (tookCard.getCrewCards().size() <= 3) {
            for (int i = 0; i < 4; i++) {
                CrewCard cardToAdd = pirateIslandCards.get(i);
                tookCard.removeCrewCard(cardToAdd);
                tookCard.addCrewCard(cardToAdd);
            }
        }
        ArrayList<Treasure> tToAdd = tookCard.getTreasures();
        homePort.setTreasurePile(tToAdd);
        int loop = tookCard.getTreasures().size();
        for (int j = 0; j < loop; j++) {
            tookCard.removeTreasure(tToAdd.get(0));
        }
    }

    /**
     * selects this chance card (blown to the nearest directed port)
     *
     * @param board             appropriate GameBoard name
     * @param tookCard          appropriate Player who took card name
     * @param pirateIslandCards arraylist of crew cards on Pirate Island
     */
    //Fernando's Jank - Only this function.
    public static void chanceCard06(GameBoard board, Player tookCard, ArrayList<CrewCard> pirateIslandCards) {
        Position[] portList = new Position[6];
        portList[0] = new Position(0, 6);//0 is london
        portList[1] = new Position(6, 19);//1 is genda
        portList[2] = new Position(19, 13);//2 is marseilles
        portList[3] = new Position(13, 0);//3 is cadiz
        portList[4] = new Position(0, 13);
        portList[5] = new Position(19, 6);
        int[] distanceFromShip = new int[4];
        int smallest = 401;
        int closestPort = 5;
        for (int i = 0; i < 6; i++) {
            distanceFromShip[i] = abs(tookCard.getLocation().getY() - portList[i].getY()) + abs(tookCard.getLocation().getX() - portList[i].getX());
        }
        switch (tookCard.getDirection()) {
            case N:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getY() - portList[i].getY()) >= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case NE:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getY() - portList[i].getY()) >= 0 && (tookCard.getLocation().getX() - portList[i].getX()) >= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case E:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getX() - portList[i].getX()) >= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case SE:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getX() - portList[i].getX()) >= 0 && (tookCard.getLocation().getY() - portList[i].getY()) <= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case S:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getY() - portList[i].getY()) <= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case SW:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getY() - portList[i].getY()) <= 0 && (tookCard.getLocation().getX() - portList[i].getX()) <= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case W:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getX() - portList[i].getX()) <= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
            case NW:
                for (int i = 0; i < 6; i++) {
                    if (smallest > distanceFromShip[i] && (tookCard.getLocation().getX() - portList[i].getX()) <= 0 && (tookCard.getLocation().getY() - portList[i].getY()) >= 0) {
                        smallest = distanceFromShip[i];
                        closestPort = i;
                    }
                }
                break;
        }
        board.teleport(tookCard, portList[closestPort]);
        if (tookCard.getCrewCards().size() < 4) {
            for (int i = 0; i < 4; i++) {
                tookCard.addCrewCard(game.getCrewCardQueue().poll());
            }
        }
    }

    /**
     * selects this chance card (item overboard ship)
     *
     * @param tookCard appropriate Player who took card name
     */
    public static void chanceCard07(Player tookCard) {
        Position locationOfCurrentPlayer = tookCard.getLocation();
        Position locationOfPlayer1 = game.getPlayerList().get(1).getLocation();
        Position locationOfPlayer2 = game.getPlayerList().get(2).getLocation();
        Position locationOfPlayer3 = game.getPlayerList().get(3).getLocation();
        Position locationOfPlayer4 = game.getPlayerList().get(4).getLocation();
        int sumCurrent = locationOfCurrentPlayer.getX() + locationOfCurrentPlayer.getY();
        int sum1 = locationOfPlayer1.getX() + locationOfPlayer1.getY();
        int sum2 = locationOfPlayer2.getX() + locationOfPlayer2.getY();
        int sum3 = locationOfPlayer3.getX() + locationOfPlayer3.getY();
        int sum4 = locationOfPlayer4.getX() + locationOfPlayer4.getY();
        int diff1 = Math.abs(sumCurrent - sum1);
        int diff2 = Math.abs(sumCurrent - sum2);
        int diff3 = Math.abs(sumCurrent - sum3);
        int diff4 = Math.abs(sumCurrent - sum4);
        ArrayList<Integer> closestPlayerToBeDetermined = new ArrayList<>();
        closestPlayerToBeDetermined.add(diff1);
        closestPlayerToBeDetermined.add(diff2);
        closestPlayerToBeDetermined.add(diff3);
        closestPlayerToBeDetermined.add(diff4);
        Collections.sort(closestPlayerToBeDetermined);
        if (!(Objects.equals(closestPlayerToBeDetermined.get(1), closestPlayerToBeDetermined.get(2)))) {
            int value = closestPlayerToBeDetermined.get(1);
            if (value == diff1) {
                if (game.getPlayerList().get(1).getCrewCards().size() != 2) {
                    ArrayList<Treasure> checkForLowestValueTreasure = tookCard.getTreasures();
                    if (checkForLowestValueTreasure.size() != 0) {
                        Treasure treasureToBeAwarded = checkForLowestValueTreasure.get(0);
                        if (checkForLowestValueTreasure.size() != 1) {
                            if (checkForLowestValueTreasure.get(1).getValue() < treasureToBeAwarded.getValue()) {
                                treasureToBeAwarded = checkForLowestValueTreasure.get(1);
                            }
                            tookCard.removeTreasure(treasureToBeAwarded);
                            game.getPlayerList().get(1).addTreasure(treasureToBeAwarded);

                        }

                    }
                } else {
                    ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
                    ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 1 || c.getValue() == -1) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 2 || c.getValue() == -2) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 3 || c.getValue() == -3) {
                            cardsExchanged.add(c);
                        }
                    }
                    tookCard.removeCrewCard(cardsExchanged.get(0));
                    game.getPlayerList().get(1).addCrewCard(cardsExchanged.get(0));
                    if (cardsToBeAddedIfTreasureNotPresent.size() != 1) {
                        tookCard.removeCrewCard(cardsExchanged.get(1));
                        game.getPlayerList().get(1).addCrewCard(cardsExchanged.get(1));
                    }
                }

            }
            if (value == diff2) {
                if (game.getPlayerList().get(2).getCrewCards().size() != 2) {
                    ArrayList<Treasure> checkForLowestValueTreasure = tookCard.getTreasures();
                    if (checkForLowestValueTreasure.size() != 0) {
                        Treasure treasureToBeAwarded = checkForLowestValueTreasure.get(0);
                        if (checkForLowestValueTreasure.size() != 1) {
                            if (checkForLowestValueTreasure.get(2).getValue() < treasureToBeAwarded.getValue()) {
                                treasureToBeAwarded = checkForLowestValueTreasure.get(1);
                            }
                            tookCard.removeTreasure(treasureToBeAwarded);
                            game.getPlayerList().get(2).addTreasure(treasureToBeAwarded);

                        }

                    }
                } else {
                    ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
                    ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 1 || c.getValue() == -1) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 2 || c.getValue() == -2) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 3 || c.getValue() == -3) {
                            cardsExchanged.add(c);
                        }
                    }
                    tookCard.removeCrewCard(cardsExchanged.get(0));
                    game.getPlayerList().get(2).addCrewCard(cardsExchanged.get(0));
                    if (cardsToBeAddedIfTreasureNotPresent.size() != 1) {
                        tookCard.removeCrewCard(cardsExchanged.get(1));
                        game.getPlayerList().get(2).addCrewCard(cardsExchanged.get(1));
                    }
                }

            }
            if (value == diff3) {
                if (game.getPlayerList().get(3).getCrewCards().size() != 2) {
                    ArrayList<Treasure> checkForLowestValueTreasure = tookCard.getTreasures();
                    if (checkForLowestValueTreasure.size() != 0) {
                        Treasure treasureToBeAwarded = checkForLowestValueTreasure.get(0);
                        if (checkForLowestValueTreasure.size() != 1) {
                            if (checkForLowestValueTreasure.get(1).getValue() < treasureToBeAwarded.getValue()) {
                                treasureToBeAwarded = checkForLowestValueTreasure.get(1);
                            }
                            tookCard.removeTreasure(treasureToBeAwarded);
                            game.getPlayerList().get(3).addTreasure(treasureToBeAwarded);

                        }

                    }
                } else {
                    ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
                    ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 1 || c.getValue() == -1) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 2 || c.getValue() == -2) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 3 || c.getValue() == -3) {
                            cardsExchanged.add(c);
                        }
                    }
                    tookCard.removeCrewCard(cardsExchanged.get(0));
                    game.getPlayerList().get(3).addCrewCard(cardsExchanged.get(0));
                    if (cardsToBeAddedIfTreasureNotPresent.size() != 1) {
                        tookCard.removeCrewCard(cardsExchanged.get(1));
                        game.getPlayerList().get(3).addCrewCard(cardsExchanged.get(1));
                    }
                }

            }
            if (value == diff4) {
                if (game.getPlayerList().get(4).getCrewCards().size() != 2) {
                    ArrayList<Treasure> checkForLowestValueTreasure = tookCard.getTreasures();
                    if (checkForLowestValueTreasure.size() != 0) {
                        Treasure treasureToBeAwarded = checkForLowestValueTreasure.get(0);
                        if (checkForLowestValueTreasure.size() != 1) {
                            if (checkForLowestValueTreasure.get(1).getValue() < treasureToBeAwarded.getValue()) {
                                treasureToBeAwarded = checkForLowestValueTreasure.get(1);
                            }
                            tookCard.removeTreasure(treasureToBeAwarded);
                            game.getPlayerList().get(4).addTreasure(treasureToBeAwarded);

                        }

                    }
                } else {
                    ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
                    ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 1 || c.getValue() == -1) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 2 || c.getValue() == -2) {
                            cardsExchanged.add(c);
                        }
                    }
                    for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                    ) {
                        if (c.getValue() == 3 || c.getValue() == -3) {
                            cardsExchanged.add(c);
                        }
                    }
                    tookCard.removeCrewCard(cardsExchanged.get(0));
                    game.getPlayerList().get(4).addCrewCard(cardsExchanged.get(0));
                    if (cardsToBeAddedIfTreasureNotPresent.size() != 1) {
                        tookCard.removeCrewCard(cardsExchanged.get(1));
                        game.getPlayerList().get(4).addCrewCard(cardsExchanged.get(1));
                    }
                }

            }
            //give One treasure from your ship or 2 crew
            //cards from your hand are lost and washed
            //overboard to the nearest ship.
        }
    }


    /**
     * selects this chance card (item overboard Flat Island)
     *
     * @param flatIslandTreasure appropriate arraylist for items on Flat Island
     * @param tookCard           appropriate Player who took card name
     */
    public static void chanceCard08(ArrayList<Treasure> flatIslandTreasure, Player tookCard, ArrayList<CrewCard> flatIslandCrew) {
        ArrayList<Treasure> playerTreasure = tookCard.getTreasures();
        if (playerTreasure.size() > 0) {

            Collections.sort(playerTreasure);
            Treasure toRemove = playerTreasure.get(0);
            playerTreasure.remove(0);
            flatIslandTreasure.add(toRemove);
        } else {
            ArrayList<CrewCard> cards2remove = tookCard.getCrewCards();
            //sort cards in accending order
            //take the 2 lowest cards and send to flat island
            ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
            ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 1 || c.getValue() == -1) {
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 2 || c.getValue() == -2) {
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 3 || c.getValue() == -3) {
                    cardsExchanged.add(c);
                }
            }
            for (int j = 0; j < 2; j++) {
                flatIslandCrew.add(cardsExchanged.get(0));
                cardsExchanged.remove(0);
            }

        }

    }

    /**
     * selects this chance card (best item overboard Flat Island)
     *
     * @param flatIslandTreasure appropriate arraylist for items on Flat Island
     * @param tookCard           appropriate Player who took card name
     */
    public static void chanceCard09(ArrayList<Treasure> flatIslandTreasure, Player tookCard, ArrayList<CrewCard> flatIslandCrew) {
        ArrayList<Treasure> playerTreasure = tookCard.getTreasures();
        if (playerTreasure.size() > 0) {

            Collections.sort(playerTreasure);
            Treasure toRemove = playerTreasure.get((playerTreasure.size()) - 1);
            tookCard.removeTreasure(toRemove);
            flatIslandTreasure.add(toRemove);
        } else {
            ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = tookCard.getCrewCards();
            ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 1 || c.getValue() == -1) {
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 2 || c.getValue() == -2) {
                    cardsExchanged.add(c);
                }
            }
            for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
            ) {
                if (c.getValue() == 3 || c.getValue() == -3) {
                    cardsExchanged.add(c);
                }
            }
            flatIslandCrew.add(cardsExchanged.get(tookCard.getCrewCards().size() - 1));
            cardsExchanged.remove((tookCard.getCrewCards().size() - 1));


        }
    }

    /**
     * selects this chance card (best crew card Pirate Island)
     *
     * @param pirateIslandCrewCards appropriate arraylist for crew cards on Flat Island
     * @param tookCard              appropriate Player who took card name
     */
    public static void chanceCard10(ArrayList<CrewCard> pirateIslandCrewCards, Player tookCard) {
        ArrayList<CrewCard> crewArray = tookCard.getCrewCards();

        // While a card has not been removed, pass through the array until you find a card of high value. Add that card to the local pirate island array
        boolean deleted = false;
        while (!deleted) {
            for (CrewCard card : crewArray) {
                if (abs(card.getValue()) == 3) {
                    pirateIslandCrewCards.add(card);
                    crewArray.remove(card);
                    deleted = true;
                    break;
                }
            }
            for (CrewCard card : crewArray) {
                if (abs(card.getValue()) == 2 & !deleted) {
                    pirateIslandCrewCards.add(card);
                    crewArray.remove(card);
                    deleted = true;
                    break;
                }
            }
            if (!deleted) {
                crewArray.remove(0);
                deleted = true;
            }
        }

        // Sets the player and game manager decks to be the same as the local decks
        GameInitialiser.game.setPirateIslandCrewCards(pirateIslandCrewCards);
        tookCard.setCrewCards(crewArray);
    }

    /**
     * take treasure up to 5 or 2 crew cards from Pirate Island
     *
     * @param player     appropriate Player who took card name
     * @param choseCards whether the player chose to take crewcards or treasure
     */
    public static void chanceCard11(Player player, boolean choseCards) throws IOException {

        if (choseCards) {
            Queue<CrewCard> cards = game.getCrewCardQueue();
            for (int debounce = 0; debounce < 2; debounce++) { // 2 = cards taken
                if (!cards.isEmpty()) {
                    player.addCrewCard(cards.poll());
                }
            }
        } else {
            ArrayList<Treasure> treasures = game.getTreasures();
            while (treasures.size()!=0){
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i)!=null){
                        while (game.findCurrentPlayer().getTreasures().size()<2){
                            game.findCurrentPlayer().addTreasure(treasures.get(i));
                            treasures.remove(i);
                        }
                        ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                        FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                        Popup popup = new Popup();
                        popup.getContent().add(loader.load());


                    }
                }
            }
        }
    }

    /**
     * take treasure up to 4 or 2 crew cards from Pirate Island
     *
     * @param player     appropriate Player who took card name
     * @param choseCards whether the player chose to take crewcards or treasure
     */
    public static void chanceCard12(Player player, boolean choseCards) throws IOException {

        if (choseCards) {
            Queue<CrewCard> cards = game.getCrewCardQueue();
            for (int debounce = 0; debounce < 2; debounce++) { // 2 = cards taken
                if (!cards.isEmpty()) {
                    player.addCrewCard(cards.poll());
                }
            }
        } else {
            ArrayList<Treasure> treasures = game.getTreasures();
            while (treasures.size()!=0){
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i)!=null){
                        while (game.findCurrentPlayer().getTreasures().size()<2){
                            game.findCurrentPlayer().addTreasure(treasures.get(i));
                            treasures.remove(i);
                        }
                        ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                        FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                        Popup popup = new Popup();
                        popup.getContent().add(loader.load());


                    }
                }
            }
        }
    }

    /**
     * take treasure up to 5 or 2 crew cards from Pirate Island
     *
     * @param player     appropriate Player who took card name
     * @param choseCards whether the player chose to take crewcards or treasure
     */
    public static void chanceCard13(Player player, boolean choseCards) throws IOException {

        if (choseCards) {
            Queue<CrewCard> cards = game.getCrewCardQueue();
            for (int debounce = 0; debounce < 2; debounce++) { // 2 = cards taken
                if (!cards.isEmpty()) {
                    player.addCrewCard(cards.poll());
                }
            }
        } else {
            ArrayList<Treasure> treasures = game.getTreasures();
            while (treasures.size()!=0){
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i)!=null){
                        while (game.findCurrentPlayer().getTreasures().size()<2){
                            game.findCurrentPlayer().addTreasure(treasures.get(i));
                            treasures.remove(i);
                        }
                        ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                        FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                        Popup popup = new Popup();
                        popup.getContent().add(loader.load());


                    }
                }
            }
        }
    }

    /**
     * take treasure up to 7 or 3 crew cards from Pirate Island
     *
     * @param player     appropriate Player who took card name
     * @param choseCards whether the player chose to take crewcards or treasure
     */
    public static void chanceCard14(Player player, boolean choseCards) throws IOException {

        if (choseCards) {
            Queue<CrewCard> cards = game.getCrewCardQueue();
            for (int debounce = 0; debounce < 3; debounce++) { // 3 = cards taken
                if (!cards.isEmpty()) {
                    player.addCrewCard(cards.poll());
                }
            }
        } else {
            ArrayList<Treasure> treasures = game.getTreasures();
            while (treasures.size()!=0){
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i)!=null){
                        while (game.findCurrentPlayer().getTreasures().size()<2){
                            game.findCurrentPlayer().addTreasure(treasures.get(i));
                            treasures.remove(i);
                        }
                        ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                        FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                        Popup popup = new Popup();
                        popup.getContent().add(loader.load());


                    }
                }
            }
        }
    }

    /**
     * take crew cards from Pirate Island
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard15(Player player) {
        Queue<CrewCard> cards = game.getCrewCardQueue();
        for (int debounce = 0; debounce < 3; debounce++) { // 2 = cards taken
            if (!cards.isEmpty()) {
                player.addCrewCard(cards.poll());
            }
        }
    }

    /**
     * take treasure up to 7 and crew to 10
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard16(Player player) throws IOException {
        ArrayList<CrewCard> cards = player.getCrewCards();
        int valueOfCards = 0;
        for (CrewCard card : cards) {
            valueOfCards += abs(card.getValue());
        }
        while (valueOfCards > 10) {
            double randIndex = rint(cards.size() - 1);
            CrewCard removeCard = cards.get((int) randIndex);
            valueOfCards -= abs(removeCard.getValue());
            cards.remove(removeCard);
        }

        ArrayList<Treasure> treasures = game.getTreasures();
        while (treasures.size()!=0){
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i)!=null){
                    while (game.findCurrentPlayer().getTreasures().size()<2){
                        game.findCurrentPlayer().addTreasure(treasures.get(i));
                        treasures.remove(i);
                    }
                    ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                    FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                    Popup popup = new Popup();
                    popup.getContent().add(loader.load());


                }
            }
        }
    }

    /**
     * take treasure up to 6 and crew to 11
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard17(Player player) throws IOException {
        ArrayList<CrewCard> cards = player.getCrewCards();
        int valueOfCards = 0;
        for (CrewCard card : cards) {
            valueOfCards += abs(card.getValue());
        }
        while (valueOfCards > 11) {
            double randIndex = rint(cards.size() - 1);
            CrewCard removeCard = cards.get((int) randIndex);
            valueOfCards -= abs(removeCard.getValue());
            cards.remove(removeCard);
        }

        ArrayList<Treasure> treasures = game.getTreasures();
        while (treasures.size()!=0){
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i)!=null){
                    while (game.findCurrentPlayer().getTreasures().size()<2){
                        game.findCurrentPlayer().addTreasure(treasures.get(i));
                        treasures.remove(i);
                    }
                    ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                    FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                    Popup popup = new Popup();
                    popup.getContent().add(loader.load());


                }
            }
        }
    }


    /**
     * take treasure up to 4 take 2 crew if total is 7 or less
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard18(Player player) throws IOException {
        ArrayList<CrewCard> cards = player.getCrewCards();
        Queue<CrewCard> cardQueue = game.getCrewCardQueue();
        int valueOfCards = 0;
        for (CrewCard card : cards) {
            valueOfCards += abs(card.getValue());
        }
        if (valueOfCards < 7) {
            cards.add(cardQueue.poll());
            cards.add(cardQueue.poll());
        }
        ArrayList<Treasure> treasures = game.getTreasures();
        while (treasures.size()!=0){
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i)!=null){
                    while (game.findCurrentPlayer().getTreasures().size()<2){
                        game.findCurrentPlayer().addTreasure(treasures.get(i));
                        treasures.remove(i);
                    }
                    ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                    FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                    Popup popup = new Popup();
                    popup.getContent().add(loader.load());


                }
            }
        }
    }

    /**
     * exchange hand
     *
     * @param pirateIslandCrewCards appropriate arraylist for crew cards on Pirate Island
     * @param tookCard              appropriate Player who took card name
     */
    public static void chanceCard19(Player tookCard, ArrayList<CrewCard> pirateIslandCrewCards) {
        int crewSize = tookCard.getCrewCards().size();
        if (crewSize > pirateIslandCrewCards.size()) {
            crewSize = pirateIslandCrewCards.size();
            for (int i = 0; i < crewSize; i++) {
                pirateIslandCrewCards.add(tookCard.getCrewCards().get(0));
                tookCard.removeCrewCard(tookCard.getCrewCards().get(0));
            }
        } else {
            for (int i = 0; i < crewSize; i++) {
                pirateIslandCrewCards.add(tookCard.getCrewCards().get(0));
                tookCard.removeCrewCard(tookCard.getCrewCards().get(0));
            }

        }
    }

    /**
     * exchange cards with another player or drop at pirate island
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard20(Player player) {
        ArrayList<Tile> listOfNearTiles = new ArrayList<>();
        Tile[][] matrix = game.getBoard().getTileMatrix();

        listOfNearTiles.add(matrix[7][7]);
        listOfNearTiles.add(matrix[7][8]);
        listOfNearTiles.add(matrix[7][9]);
        listOfNearTiles.add(matrix[7][10]);
        listOfNearTiles.add(matrix[7][11]);
        listOfNearTiles.add(matrix[7][12]);
        listOfNearTiles.add(matrix[8][12]);
        listOfNearTiles.add(matrix[9][12]);
        listOfNearTiles.add(matrix[10][12]);
        listOfNearTiles.add(matrix[11][12]);
        listOfNearTiles.add(matrix[12][12]);
        listOfNearTiles.add(matrix[12][11]);
        listOfNearTiles.add(matrix[12][10]);
        listOfNearTiles.add(matrix[12][9]);
        listOfNearTiles.add(matrix[12][8]);
        listOfNearTiles.add(matrix[12][7]);
        listOfNearTiles.add(matrix[11][7]);
        listOfNearTiles.add(matrix[10][7]);
        listOfNearTiles.add(matrix[9][7]);
        listOfNearTiles.add(matrix[8][7]);

        boolean noOtherPlayer = true;
        for (Player player2 : game.getPlayerList()) {
            for (Tile tile : listOfNearTiles) {
                if (matrix[player2.getLocation().getX()][player2.getLocation().getY()] == tile) {
                    noOtherPlayer = false;
                    for (int number = 0; number < 2; number++) {
                        CrewCard card1 = player.getCrewCards().get((int) rint(player.getCrewCards().size() - 1));
                        player.removeCrewCard(card1);
                        CrewCard card2 = player.getCrewCards().get((int) rint(player.getCrewCards().size() - 1));
                        player.removeCrewCard(card2);
                        CrewCard card3 = player.getCrewCards().get((int) rint(player2.getCrewCards().size() - 1));
                        player2.removeCrewCard(card3);
                        CrewCard card4 = player.getCrewCards().get((int) rint(player2.getCrewCards().size() - 1));
                        player2.removeCrewCard(card4);

                        player2.addCrewCard(card1);
                        player2.addCrewCard(card2);
                        player.addCrewCard(card3);
                        player.addCrewCard(card4);
                    }
                }
            }
        }
        if (noOtherPlayer) {
            CrewCard card1 = player.getCrewCards().get((int) rint(player.getCrewCards().size() - 1));
            player.removeCrewCard(card1);
            CrewCard card2 = player.getCrewCards().get((int) rint(player.getCrewCards().size() - 1));
            player.removeCrewCard(card2);
            game.getPirateIslandCrewCards().add(card1);
            game.getPirateIslandCrewCards().add(card2);
        }
    }

    /**
     * Long John Silver
     * * @param tookCard appropriate Player who took card name
     */
    public static void chanceCard21(Player player) throws FileNotFoundException {
        CrewCard longJohn = new CrewCard(5, "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\ a chance card image for Long John Silver");
        player.addCrewCard(longJohn);           //NEEDS IMAGE
    }

    public static void chanceCard22(ArrayList<Player> allPlayers, ArrayList<CrewCard> pirateIslandCrewCards) {

        for (Player player : allPlayers) {
            if (player.getCrewCards().size() > 7) {
                int extraCards = player.getCrewCards().size() - 7;
                ArrayList<CrewCard> cardsToBeAddedIfTreasureNotPresent = player.getCrewCards();
                ArrayList<CrewCard> cardsExchanged = new ArrayList<>();
                for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                ) {
                    if (c.getValue() == 1 || c.getValue() == -1) {
                        cardsExchanged.add(c);
                    }
                }
                for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                ) {
                    if (c.getValue() == 2 || c.getValue() == -2) {
                        cardsExchanged.add(c);
                    }
                }
                for (CrewCard c : cardsToBeAddedIfTreasureNotPresent
                ) {
                    if (c.getValue() == 3 || c.getValue() == -3) {
                        cardsExchanged.add(c);
                    }
                }
                for (int i = 0; i < extraCards; i++) {
                    pirateIslandCrewCards.add(cardsExchanged.get(player.getCrewCards().size() - 1));
                    cardsExchanged.remove((player.getCrewCards().size() - 1));
                }
            }

        }

    }

    /**
     * doubloons (card can be traded for crew or treasure up to 5)
     * * @param tookCard appropriate Player who took card name
     */
    public static void chanceCard23(Player player) throws FileNotFoundException {
        CrewCard doubloon = new CrewCard(5, "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\ a chance card image for doubloon");
        player.addCrewCard(doubloon);           //NEEDS IMAGE
    }

    /**
     * card can be traded for crew or treasure up to 4
     *
     * @param player appropriate Player who took card name
     */
    public static void chanceCard24(Player player) throws FileNotFoundException {
        CrewCard pieceOf8 = new CrewCard(4, "src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\ a chance card image for piece of 8");
        player.addCrewCard(pieceOf8);           //NEEDS IMAGE
    }

    /**
     * move to anchor tile and take up to 7 treasure
     *
     * @param board                  appropriate GameBoard name
     * @param tookCard               appropriate Player who took card name
     * @param treasureIslandTreasure appropriate arraylist for treasure on Treasure Island
     */
    public static void chanceCard25(GameBoard board, Player tookCard, ArrayList<Treasure> treasureIslandTreasure) throws Exception {
        ArrayList<Treasure> treasures = game.getTreasures();
        while (treasures.size()!=0){
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i)!=null){
                    while (game.findCurrentPlayer().getTreasures().size()<2){
                        game.findCurrentPlayer().addTreasure(treasures.get(i));
                        treasures.remove(i);
                    }
                    ChanceTradeEvent.setCurrents(tookCard, 5); // 5 = treasure value max
                    FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                    Popup popup = new Popup();
                    popup.getContent().add(loader.load());


                }
            }
        }
    }

    /**
     * move to anchor tile and take up to 7 treasure
     *
     * @param board                  appropriate GameBoard name
     * @param tookCard               appropriate Player who took card name
     * @param treasureIslandTreasure appropriate arraylist for treasure on Treasure Island
     */
    public static void chanceCard26(GameBoard board, Player tookCard, ArrayList<Treasure> treasureIslandTreasure) throws Exception{
        ArrayList<Treasure> treasures = game.getTreasures();
        while (treasures.size()!=0){
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i)!=null){
                    while (game.findCurrentPlayer().getTreasures().size()<2){
                        game.findCurrentPlayer().addTreasure(treasures.get(i));
                        treasures.remove(i);
                    }
                    ChanceTradeEvent.setCurrents(tookCard, 5); // 5 = treasure value max
                    FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                    Popup popup = new Popup();
                    popup.getContent().add(loader.load());


                }
            }
        }
    }

    /**
     * take treasure up to 5 or 3 crew cards
     *
     * @param player     appropriate Player who took card name
     * @param choseCards whether the player chose to take crewcards or treasure
     */
    public static void chanceCard27(Player player, boolean choseCards) throws IOException {

        if (choseCards) {
            Queue<CrewCard> cards = game.getCrewCardQueue();
            for (int debounce = 0; debounce < 3; debounce++) { // 2 = cards taken
                if (!cards.isEmpty()) {
                    player.addCrewCard(cards.poll());
                }
            }
        } else {
            ArrayList<Treasure> treasures = game.getTreasures();
            while (treasures.size()!=0){
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i)!=null){
                        while (game.findCurrentPlayer().getTreasures().size()<2){
                            game.findCurrentPlayer().addTreasure(treasures.get(i));
                            treasures.remove(i);
                        }
                        ChanceTradeEvent.setCurrents(player, 5); // 5 = treasure value max
                        FXMLLoader loader = new FXMLLoader(StateEvent.class.getResource("ChanceTradeScreen.fxml"));
                        Popup popup = new Popup();
                        popup.getContent().add(loader.load());


                    }
                }
            }
        }
    }

    /**
     * take 2 crew cards from Pirate Island
     *
     * @param tookCard              appropriate Player who took card name
     * @param pirateIslandCrewCards appropriate arraylist for crew cards on Pirate Island
     */
    public static void chanceCard28(Player tookCard, ArrayList<CrewCard> pirateIslandCrewCards) {
        ArrayList<CrewCard> crewArray = tookCard.getCrewCards();

        // This if cluster mitigates if the pirate island deck is empty
        if (pirateIslandCrewCards.get(1) == null) {
            if (pirateIslandCrewCards.get(0) != null) {
                crewArray.add(pirateIslandCrewCards.get(0));
            }
            pirateIslandCrewCards.remove(0);
        } else {
            // This looks horrendous to me, please let me know if there's a better way, or just fix it yourself
            crewArray.add(pirateIslandCrewCards.get(0));
            crewArray.add(pirateIslandCrewCards.get(1));
            pirateIslandCrewCards.remove(0);
            pirateIslandCrewCards.remove(1);
        }
        // Sets the actual decks to be the new versions made above.
        GameInitialiser.game.setPirateIslandCrewCards(pirateIslandCrewCards);
        tookCard.setCrewCards(crewArray);
    }
}
