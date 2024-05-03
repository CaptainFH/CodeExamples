package uk.ac.aber.cs221.gp05.buccaneergame.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameManager;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.GameBoard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.Position;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Port;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.*;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.State;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateEvent;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import static org.junit.Assume.assumeTrue;

public class StateTest {

    @BeforeClass
    public static void testInitializer() throws FileNotFoundException {
        GameInitialiser.game = new GameManager();
        // gameBoard.createBoard();
        // GameInitialiser.game.setBoard(gameBoard);
        //GameInitialiser.game.
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1", Colour.BLUE);
        Player player2 = new Player("Player2", Colour.GREEN);
        Player player3 = new Player("Player3", Colour.ORANGE);
        Player player4 = new Player("Player4", Colour.YELLOW);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        GameInitialiser.game.setPlayerList(players);
        GameInitialiser.game.preparePlayers(players);
        GameBoard gameBoard = new GameBoard();
        gameBoard.createBoard();
        GameInitialiser.game.setBoard(gameBoard);
        GameInitialiser.game.prepareBoard();
        GameInitialiser.game.delegateCrew();


        StateManager state = new StateManager(State.TURN, 4);
        GameInitialiser.game.setState(state);
        //GameInitialiser.game.delegateCrew(players);
        // GameInitialiser.game.setBoard();


        ArrayList<Port> portlist = new ArrayList<>();








    }

    @Test
    public void test1(){

    }

    //This test checks that if chosen player has no crew cards chance card is returned to bottom
    //function works
    @Test
    public void test2one(){
        //SETUP
        ArrayList<CrewCard> nullCards = new ArrayList<>();
        ArrayList<Player> players = GameInitialiser.game.getPlayerList();
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        players.get(1).getCrewCards().clear();
        players.get(2).getCrewCards().clear();
        players.get(3).getCrewCards().clear();


        int pNumofCard = currentPlayer.getCrewCards().size();
        // chosen.setCrewCards(nullCards);
        //  int cNumofCard = chosen.getCrewCards().size();
        //  Queue<ChanceCard> preTest = GameInitialiser.game.getChanceCardQueue();
        //  = new ArrayList<>();
        //cplayer.add(chosen);

        //checks current players number of crew cards to see if it remained the same
        StateEvent.chanceCard02(currentPlayer, players);
        Assert.assertEquals(currentPlayer.getCrewCards().size(), pNumofCard);
        //This require the number of crew cards the chosen player now has after the chance card has been executed
        // Assert.assertEquals(chosen.getCrewCards().size(), cNumofCard);
        // Assert.assertNotEquals(GameInitialiser.game.getChanceCardQueue().poll().getText(), preTest.poll().getText());



    }


    //chance card 2 both players crew cards updated correctly
    //function works
    @Test
    public void test2two(){
        //SETUP
        GameBoard gb = new GameBoard();
        gb.createBoard();
        GameInitialiser.game.setBoard(gb);

        ArrayList<Player> players = GameInitialiser.game.getPlayerList();
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();


        // cplayer.add(chosen);
        //  int cNumofCard = chosen.getCrewCards().size();
        int pNumofCard = currentPlayer.getCrewCards().size();

        //CHECKS IF CREW CARD NUMBER OF CURRENT PLAYER HAS
        StateEvent.chanceCard02(currentPlayer, players);
        //  Assert.assertEquals(chosen.getCrewCards().size(), pNumofCard-3);
        Assert.assertEquals(currentPlayer.getCrewCards().size(), pNumofCard+3);

    }

    @Test
    public void test3() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player playerSaved = currentPlayer;
        ArrayList<CrewCard> pirateCrew = GameInitialiser.game.getPirateIslandCrewCards();

        Position startCoords = new Position(2,2);
        currentPlayer.setLocation(startCoords);

        StateEvent.chanceCard03(GameInitialiser.game.getBoard(), currentPlayer, pirateCrew);

        int currentX = currentPlayer.getLocation().getX();
        int currentY = currentPlayer.getLocation().getY();

        // Check the player is in the right place, check they have been given the right amount of chance cards if necessary
        Assert.assertEquals(currentX, 1);
        Assert.assertEquals(currentY, 1);
        if (playerSaved.getCrewCards().size() > 2){
            Assert.assertEquals(playerSaved.getCrewCards(), currentPlayer.getCrewCards());
        } else {
            Assert.assertEquals(currentPlayer.getCrewCards().size()-4, playerSaved.getCrewCards().size());
        }
    }

    @Test public void test4() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player playerSaved = currentPlayer;
        Position cliffCreekCoord = new Position(20,20);

        StateEvent.selectChanceCard(4, false);


        // Check the player is in the right place, check they have been given the right amount of chance cards if necessary
        Assert.assertEquals(currentPlayer.getLocation().getX(), 20);
        Assert.assertEquals(currentPlayer.getLocation().getY(), 20);
        if (playerSaved.getCrewCards().size() > 2){
            Assert.assertEquals(playerSaved.getCrewCards(), currentPlayer.getCrewCards());
        } else {
            Assert.assertEquals(currentPlayer.getCrewCards().size()-4, playerSaved.getCrewCards().size());
        }
    }

    @Test public void test5() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player playerSaved = currentPlayer;
        Port homePort = currentPlayer.getPortTile().getPort();


        StateEvent.chanceCard05(GameInitialiser.game.getBoard(), currentPlayer, GameInitialiser.game.getPirateIslandCrewCards());

        //Checks the players location to see that they have been sent to their home ports
        if (homePort == Port.LONDON){
            Assert.assertEquals(currentPlayer.getLocation().getX(), 1);
            Assert.assertEquals(currentPlayer.getLocation().getY(), 14);
        } else if (homePort == Port.CADIZ){
            Assert.assertEquals(currentPlayer.getLocation().getX(), 14);
            Assert.assertEquals(currentPlayer.getLocation().getY(), 20);
        } else if (homePort == Port.GENOA){
            Assert.assertEquals(currentPlayer.getLocation().getX(), 7);
            Assert.assertEquals(currentPlayer.getLocation().getY(), 1);
        } else if (homePort == Port.MARSEILLES){
            Assert.assertEquals(currentPlayer.getLocation().getX(), 20);
            Assert.assertEquals(currentPlayer.getLocation().getY(), 7);
        }
        // Check the player is in the right place, check they have been given the right amount of chance cards if necessary

        if (playerSaved.getCrewCards().size() > 2){
            Assert.assertEquals(playerSaved.getCrewCards(), currentPlayer.getCrewCards());
        } else {
            Assert.assertEquals(currentPlayer.getCrewCards().size()-4, playerSaved.getCrewCards().size());
        }
    }

    @Test public void test6() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player playerSaved = currentPlayer;
        Position london = new Position(1,14);
        Position cadiz = new Position(7,1);
        Position genoa = new Position(20,7);
        Position marsilles = new Position(14,20);
        //Position venice = new Position(12,0);
        //Position amsterdam = new Position(6,19);


        StateEvent.selectChanceCard(6, true);


        Assert.assertEquals(1, currentPlayer.getLocation().getX());
        Assert.assertEquals(14, currentPlayer.getLocation().getX());

        Assert.assertEquals(7, currentPlayer.getLocation().getX());
        Assert.assertEquals(1, currentPlayer.getLocation().getX());

        Assert.assertEquals(20, currentPlayer.getLocation().getX());
        Assert.assertEquals(7, currentPlayer.getLocation().getX());

        Assert.assertEquals(14, currentPlayer.getLocation().getX());
        Assert.assertEquals(20, currentPlayer.getLocation().getX());


        //Assert.assertEquals(venice, currentPlayer.getLocation());
        //Assert.assertEquals(amsterdam, currentPlayer.getLocation());

        if (playerSaved.getCrewCards().size() > 2){
            Assert.assertEquals(playerSaved.getCrewCards(), currentPlayer.getCrewCards());
        } else {
            Assert.assertEquals(currentPlayer.getCrewCards().size()-4, playerSaved.getCrewCards().size());
        }
    }


    //Chance card 07 tests if one treasure is removed from player
    @Test
    public void test7one(){
        Player player = GameInitialiser.game.findCurrentPlayer();
        player.addTreasure(Treasure.RUM);
        player.addTreasure(Treasure.DIAMOND);
        Player chosen = GameInitialiser.game.getPlayerList().get(1);
        if(player.getTreasures().size() > 0){
            int treasureSize = player.getTreasures().size();
            StateEvent.chanceCard07(player);
            Assert.assertEquals(player.getTreasures().size(), treasureSize-1);
        }else {
            int crewSize = player.getCrewCards().size();
            StateEvent.chanceCard07(player);
            Assert.assertEquals(player.getCrewCards().size(), crewSize-2);

        }


    }

    //Chance card 07 tests if two crew cards are removed from player
    @Test
    public void test7two() {
        Player player = GameInitialiser.game.findCurrentPlayer();

    }

    //chance card 08
    //Checks one treasure from player is sent to flat island
    @Test
    public void test8one(){
        Player player = GameInitialiser.game.findCurrentPlayer();
        player.addTreasure(Treasure.RUM);
        int treasureSize = player.getTreasure().size();
        int flatIslandTreasureSize = GameInitialiser.game.getFlatIslandTreasure().size();
        StateEvent.chanceCard08(GameInitialiser.game.getFlatIslandTreasure(), player, GameInitialiser.game.getFlatIslandCrewCards());
        Assert.assertEquals(GameInitialiser.game.getFlatIslandTreasure().size(), flatIslandTreasureSize+1);
        Assert.assertEquals(player.getTreasures().size(), treasureSize-1);
    }

    //chance card 08
    //checks if 2 of players crew cards is sent to flat island
    @Test
    public void test8two(){
        Player player = GameInitialiser.game.findCurrentPlayer();
        ArrayList<CrewCard> checkCards = GameInitialiser.game.getFlatIslandCrewCards();
        int crewSize = player.getCrewCards().size();
        int flatIslandCrewSize = GameInitialiser.game.getFlatIslandCrewCards().size();
        StateEvent.chanceCard08(GameInitialiser.game.getFlatIslandTreasure(), player, checkCards);
        Assert.assertEquals(checkCards.size(), flatIslandCrewSize+2);
        Assert.assertEquals(GameInitialiser.game.findCurrentPlayer().getCrewCards().size(), crewSize-2);
    }

    //chance card 09
    //check players most valuable treasure is sent to flat island
    @Test
    public void test9one(){
        Player player = GameInitialiser.game.findCurrentPlayer();
        ArrayList<Treasure> playerTreasure = new ArrayList<>();
        playerTreasure.add(Treasure.RUBY);
        playerTreasure.add(Treasure.RUM);
        Collections.sort(playerTreasure);
        Treasure mvTreasure = playerTreasure.get(playerTreasure.size()-1);
        int treasureSize = player.getTreasure().size();
        int flatIslandTreasureSize = GameInitialiser.game.getFlatIslandTreasure().size();

        StateEvent.chanceCard09(GameInitialiser.game.getFlatIslandTreasure(), player, GameInitialiser.game.getFlatIslandCrewCards());
        // Assert.assertTrue(GameInitialiser.game.getFlatIslandTreasure().contains(mvTreasure));
        Assert.assertEquals(GameInitialiser.game.findCurrentPlayer().getTreasures().size(), treasureSize-1);
    }

    //chance card09
    //checks if player crew card is sent to flat island
    @Test
    public void test9two(){
        Player player = GameInitialiser.game.findCurrentPlayer();
        ArrayList<CrewCard> checkCards = GameInitialiser.game.getFlatIslandCrewCards();
        int crewSize = player.getCrewCards().size();
        int flatIslandCrewSize = GameInitialiser.game.getFlatIslandCrewCards().size();
        StateEvent.chanceCard09(GameInitialiser.game.getFlatIslandTreasure(), player, checkCards);
        Assert.assertEquals(checkCards.size(), flatIslandCrewSize+1);
        Assert.assertEquals(player.getCrewCards().size(), crewSize-1);
    }

    //chance card 10
    //checks if players best crew card is sent to pirate island
    @Test
    public void test10(){
        Player player = GameInitialiser.game.findCurrentPlayer();

        int crewSize = player.getCrewCards().size();
        int pirateIslandCrewSize = GameInitialiser.game.getPirateIslandCrewCards().size();
        StateEvent.chanceCard10(GameInitialiser.game.getPirateIslandCrewCards(), player);
        Assert.assertEquals(GameInitialiser.game.getPirateIslandCrewCards().size(), pirateIslandCrewSize+1);
        Assert.assertEquals(player.getCrewCards().size(), crewSize-1);
    }

    @Test public void test11ChooseTreasure() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(11, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 5 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<5);
    }
    @Test public void test11ChooseCrew() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.chanceCard11(currentPlayer, true);

        Assert.assertEquals(currentPlayer.getCrewCards().size()+2, savedPlayer.getCrewCards().size());
    }

    @Test public void test12ChooseTreasure() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(12, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 4 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<4);
    }
    @Test public void test12ChooseCrew() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(12, true);

        Assert.assertEquals(currentPlayer.getCrewCards().size()+2, savedPlayer.getCrewCards().size());
    }
    @Test public void test13ChooseTreasure() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(13, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 5 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<5);
    }
    @Test public void test13ChooseCrew() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(13, false);

        Assert.assertEquals(currentPlayer.getCrewCards().size()+2, savedPlayer.getCrewCards().size());
    }
    @Test public void test14ChooseTreasure() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(14, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 7 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<7);
    }
    @Test public void test14ChooseCrew() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(14, true);

        Assert.assertEquals(currentPlayer.getCrewCards().size(), savedPlayer.getCrewCards().size()+3);
    }
    @Test public void test15() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;

        //Choose treasure
        StateEvent.selectChanceCard(15, true);


        Assert.assertEquals(currentPlayer.getCrewCards().size()+2, savedPlayer.getCrewCards().size()+2);
    }
    @Test public void test16() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;
        ArrayList<CrewCard> pirateIslandCrewCards = GameInitialiser.game.getPirateIslandCrewCards();
        ArrayList<CrewCard> savedPirateIslandCrewCards = pirateIslandCrewCards;

        //Choose treasure
        StateEvent.selectChanceCard(16, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 7 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<=7);

        // Assuming that the crew power was over 10...
        assumeTrue(savedPlayer.getCrewPower()>10);
        // Make sure the crew power has been appropriately reduced.
        Assert.assertTrue(currentPlayer.getCrewPower()<=10);
        // Make sure pirate island deck has changed.
        Assert.assertNotEquals(pirateIslandCrewCards, savedPirateIslandCrewCards);
    }

    @Test public void test17() throws Exception{
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;
        ArrayList<CrewCard> pirateIslandCrewCards = GameInitialiser.game.getPirateIslandCrewCards();
        ArrayList<CrewCard> savedPirateIslandCrewCards = pirateIslandCrewCards;

        StateEvent.selectChanceCard(17, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 6 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<=6);

        // Assuming that the crew power was over 11...
        assumeTrue(savedPlayer.getCrewPower()>11);
        // Make sure the crew power has been appropriately reduced.
        Assert.assertTrue(currentPlayer.getCrewPower()<=11);
        // Make sure pirate island deck has changed.
        Assert.assertNotEquals(pirateIslandCrewCards, savedPirateIslandCrewCards);
    }
    @Test public void test18() throws Exception {
        // Setup
        Player currentPlayer = GameInitialiser.game.findCurrentPlayer();
        Player savedPlayer = currentPlayer;
        ArrayList<CrewCard> pirateIslandCrewCards = GameInitialiser.game.getPirateIslandCrewCards();
        ArrayList<CrewCard> savedPirateIslandCrewCards = pirateIslandCrewCards;

        StateEvent.selectChanceCard(17, false);

        // If the ship isn't full, check that its treasure has changed
        if (currentPlayer.getTreasures().size() < 2) {
            Assert.assertNotEquals(currentPlayer.getTreasures(), savedPlayer.getTreasures());
        }

        // Check what treasure has actually changed, make sure it is val 4 or less
        int valTaken = 0;
        for (Treasure tc : currentPlayer.getTreasures()){
            for (Treasure ts : savedPlayer.getTreasures()){
                if (tc == ts){
                    currentPlayer.removeTreasure(tc);
                }
            }
            valTaken += tc.getValue();
        }
        Assert.assertTrue(valTaken<=4);

        Assert.assertEquals(savedPlayer.getCrewCards().size()+2, currentPlayer.getCrewCards().size());
        // Make sure pirate island deck has changed.
        Assert.assertNotEquals(pirateIslandCrewCards, savedPirateIslandCrewCards);
    }

    //chance card 22
    //this checks if player crew cards are reduced if greater than seven
    @Test
    public void test22() throws FileNotFoundException {
        ArrayList<Player> player = GameInitialiser.game.getPlayerList();
        ArrayList<CrewCard> pirateIslandCrewCards = GameInitialiser.game.getPirateIslandCrewCards();
        int size1 = player.get(0).getCrewCards().size();
        CrewCard c1 = new CrewCard(1);
        CrewCard c2 = new CrewCard(2);
        CrewCard c3 = new CrewCard(3);
        player.get(0).addCrewCard(c1);
        player.get(0).addCrewCard(c2);
        player.get(0).addCrewCard(c3);
        int size2 = player.get(1).getCrewCards().size();
        int size3 = player.get(2).getCrewCards().size();
        int size4 = player.get(3).getCrewCards().size();
        StateEvent.chanceCard22(player, pirateIslandCrewCards);
        Assert.assertTrue(player.get(0).getCrewCards().size() <= 7);

        Assert.assertTrue(player.get(1).getCrewCards().size() <= 7);

        Assert.assertTrue(player.get(2).getCrewCards().size() <= 7);

        Assert.assertTrue(player.get(3).getCrewCards().size() <= 7);

    }

}