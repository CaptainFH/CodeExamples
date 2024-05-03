package uk.ac.aber.cs221.gp05.buccaneergame.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameManager;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.GameBoard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.IslandTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Port;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class GameInitTests {

    @BeforeClass
    public static void testInitializer() {
        GameInitialiser.game = new GameManager();
        GameBoard gameBoard = new GameBoard();
        GameInitialiser.game.setBoard(gameBoard);
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
        GameInitialiser.game.delegateTreasure();

        GameInitialiser.game.preparePlayers(players);
    }

    @Test
    public void SEF007_009() {
        ArrayList<Player> players = GameInitialiser.game.getPlayerList();
        ArrayList<PortTile> ports = new ArrayList<>();
        for (Player player:players) {
            ports.add(player.getPortTile());
        }
        for (PortTile port:ports) {
            for (PortTile port2:ports) {
                Assert.assertNotEquals(port,port2);
            }
        }
    }

    @Test
    public void SEF008() {
        Player player1 = GameInitialiser.game.getPlayerList().get(0);
        Player player2 = GameInitialiser.game.getPlayerList().get(1);
        Player player3 = GameInitialiser.game.getPlayerList().get(2);
        Player player4 = GameInitialiser.game.getPlayerList().get(3);

        player1.setHomePort(new PortTile(Port.AMSTERDAM));
        Assert.assertNotEquals(player1.getPortTile(),Port.AMSTERDAM);

        player2.setHomePort(new PortTile(Port.AMSTERDAM));
        Assert.assertNotEquals(player2.getPortTile(),Port.AMSTERDAM);

        player3.setHomePort(new PortTile(Port.AMSTERDAM));
        Assert.assertNotEquals(player3.getPortTile(),Port.AMSTERDAM);

        player4.setHomePort(new PortTile(Port.AMSTERDAM));
        Assert.assertNotEquals(player4.getPortTile(),Port.AMSTERDAM);
    }

    @Test
    public void SEF010() throws FileNotFoundException {
        ArrayList<CrewCard> crewCards = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            for (int pairs = 0; pairs < 6; pairs++) {
                crewCards.add(new CrewCard(i));
                crewCards.add(new CrewCard(i * -1));
            }
        }

        ArrayList<CrewCard> crewCardsUnshuffled = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            for (int pairs = 0; pairs < 6; pairs++) {
                crewCards.add(new CrewCard(i));
                crewCards.add(new CrewCard(i * -1));
            }
        }


        Collections.shuffle(crewCards);
        Queue<CrewCard> shuffledCrew = new LinkedList<>(crewCards);
        Assert.assertNotEquals(crewCardsUnshuffled,shuffledCrew);
    }

    @Test
    public void SEF012() throws FileNotFoundException {
        Queue<CrewCard> crewCards = GameInitialiser.game.getCrewCardQueue();
        int crewCardSize = crewCards.size();

        CrewCard addCard = new CrewCard(1);
        crewCards.add(addCard);

        Assert.assertEquals(crewCardSize+1,crewCards.size());

        while (crewCards.size() > 1) {
            crewCards.poll();
        }
        Assert.assertEquals(addCard,crewCards.peek());
    }

    @Test
    public void SEF014_015() {
        GameInitialiser.loadChanceCard();
        Assert.assertEquals(GameInitialiser.game.getChanceCardQueue().size(),28);
    }

    @Test
    public void SEF016() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ChanceCard> cards = mapper.readValue(Paths.get("src/main/resources/uk/ac/aber/cs221/gp05/buccaneergame/assets/chance_cards.json").toFile(), new TypeReference<List<ChanceCard>>() {
        });
        Queue<ChanceCard> deck = new LinkedList<>();
        for (ChanceCard c: cards) {
            deck.add(c);
        }

        GameInitialiser.game.setChanceCardQueue(deck);
        Assert.assertEquals(cards, GameInitialiser.game.getChanceCardQueue());
    }

    @Test
    public void SEF017() {
        GameInitialiser.loadChanceCard();
        Queue<ChanceCard> chanceCards = GameInitialiser.game.getChanceCardQueue();
        ChanceCard peeked = chanceCards.peek();
        ChanceCard taken = chanceCards.poll();
        Assert.assertEquals(peeked,taken);
    }

    @Test
    public void SEF018() {
        GameInitialiser.loadChanceCard();
        Queue<ChanceCard> chanceCards = GameInitialiser.game.getChanceCardQueue();
        int sizeBefore = chanceCards.size();

        ChanceCard addCard = new ChanceCard();
        chanceCards.add(addCard);

        Assert.assertEquals(sizeBefore,chanceCards.size()-1);

        while (chanceCards.size() > 1) {
            chanceCards.poll();
        }
        Assert.assertEquals(addCard,chanceCards.peek());
    }

    @Test
    public void SEF019() {
        ArrayList<Treasure> treasures = GameInitialiser.game.getTreasures();
        int DiaCount = 0;
        int RubCount = 0;
        int GolCount = 0;
        int PeaCount = 0;
        int RumCount = 0;
        for (Treasure treasure:treasures) {
            switch (treasure) {
                case DIAMOND -> DiaCount++;
                case RUBY -> RubCount++;
                case GOLD -> GolCount++;
                case PEARL -> PeaCount++;
                case RUM -> RumCount++;
            }
        }
        Assert.assertEquals(DiaCount,4);
        Assert.assertEquals(RubCount,4);
        Assert.assertEquals(GolCount,4);
        Assert.assertEquals(PeaCount,4);
        Assert.assertEquals(RumCount,4);

        Assert.assertEquals(DiaCount+RubCount+GolCount+PeaCount+RubCount,20);
    }

    @Test
    public void SEF020() {
        Assert.assertEquals(Treasure.DIAMOND.getValue(),5);
        Assert.assertEquals(Treasure.RUBY.getValue(),5);
        Assert.assertEquals(Treasure.GOLD.getValue(),4);
        Assert.assertEquals(Treasure.PEARL.getValue(),3);
        Assert.assertEquals(Treasure.RUM.getValue(),2);
    }

    @Test
    public void SEF022() {
        Player player = GameInitialiser.game.getPlayerList().get(0);
        player.addTreasure(Treasure.DIAMOND);
        Treasure treasureGiven = player.getTreasure().get(0);
        Assert.assertEquals(treasureGiven,Treasure.DIAMOND);

        ArrayList<Treasure> treasureIsland = GameInitialiser.game.getTreasures();
        Treasure treasureTaken = treasureIsland.get(0);
        treasureIsland.remove(0);
        Assert.assertNotEquals(treasureGiven,treasureIsland.get(0));

    }

    @Test
    public void SEF023() {
        Player player = GameInitialiser.game.getPlayerList().get(0);
        player.setTreasures(new ArrayList<>());
        player.addTreasure(Treasure.DIAMOND);
        PortTile port = player.getPortTile();

        Treasure playerTreasure = player.getTreasure().get(0);
        player.removeTreasure(playerTreasure);
        port.addTreasure(playerTreasure);

        Assert.assertNotEquals(playerTreasure,player.getTreasure().get(0));
        Assert.assertEquals(playerTreasure,port.getTreasure().get(port.getTreasure().size() - 1));
    }

    @Test
    public void SEF024() {
        Player player = GameInitialiser.game.getPlayerList().get(0);
        player.addTreasure(Treasure.DIAMOND);
        ArrayList<Treasure> flat = GameInitialiser.game.getFlatIslandTreasure();

        Treasure playerTreasure = player.getTreasure().get(0);
        player.removeTreasure(playerTreasure);
        GameInitialiser.game.getFlatIslandTreasure().add(playerTreasure);

        Assert.assertNotEquals(playerTreasure,player.getTreasure().get(0));
        Assert.assertEquals(playerTreasure,flat.get(flat.size() - 1));
    }

    @Test
    public void SEF025() {
        //fuck
    }
}
