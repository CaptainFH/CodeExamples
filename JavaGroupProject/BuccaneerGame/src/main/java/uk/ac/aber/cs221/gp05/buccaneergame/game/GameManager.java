package uk.ac.aber.cs221.gp05.buccaneergame.game;

import uk.ac.aber.cs221.gp05.buccaneergame.game.board.GameBoard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Port;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.Tile;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.*;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateManager;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class responsible for the managing of the game
 *
 * @author Jack Gannon (jag78)
 * @author Igor Daniulaj (igd4)
 * @author Parsa Daneshwar Haghighi (pad31)
 */

public class GameManager {
    private StateManager gamestate;

    private Queue<ChanceCard> chanceCardQueue = new LinkedList<>();
    private Queue<CrewCard> crewCardQueue = new LinkedList<>();
    private ArrayList<Player> playerList = new ArrayList<>();
    private GameBoard board;

    private ArrayList<Treasure> treasures = new ArrayList<>();
    // The Pirate Island crew card deck
    private ArrayList<CrewCard> pirateIslandCrewCards = new ArrayList<>();
    // The Flat Island crew card deck
    private ArrayList<CrewCard> flatIslandCrewCards = new ArrayList<>();
    // The Flat Island treasure pile
    private ArrayList<Treasure> flatIslandTreasure = new ArrayList<>();

    //Setters and Getters for the above
    public ArrayList<CrewCard> getPirateIslandCrewCards() {
        return pirateIslandCrewCards;
    }

    public void setPirateIslandCrewCards(ArrayList<CrewCard> pirateIslandCrewCards) {
        this.pirateIslandCrewCards = pirateIslandCrewCards;
    }

    public ArrayList<CrewCard> getFlatIslandCrewCards() {
        return flatIslandCrewCards;
    }

    public void setFlatIslandCrewCards(ArrayList<CrewCard> flatIslandCrewCards) {
        this.flatIslandCrewCards = flatIslandCrewCards;
    }

    public ArrayList<Treasure> getFlatIslandTreasure() {
        return flatIslandTreasure;
    }

    public void setFlatIslandTreasure(ArrayList<Treasure> flatIslandTreasure) {
        this.flatIslandTreasure = flatIslandTreasure;
    }


    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setState(StateManager newState) {
        this.gamestate = newState;
    }

    public GameBoard getBoard() {
        return board;
    }

    public GameManager() {
        board = new GameBoard();
    }

    public void delegateCrew() throws FileNotFoundException {
        // 6 x 1 2 3 r and 1 2 3 b

        ArrayList<CrewCard> crewCards = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            for (int pairs = 0; pairs < 6; pairs++) {
                crewCards.add(new CrewCard(i));
                crewCards.add(new CrewCard(i * -1));
            }
        }
        Collections.shuffle(crewCards);
        Queue<CrewCard> shuffledCrew = new LinkedList<>(crewCards);

        for (Player p : playerList) {
            ArrayList<CrewCard> cards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                cards.add(shuffledCrew.poll());
            }
            p.setCrewCards(cards);
        }

        ArrayList<CrewCard> port1Cards = new ArrayList<>();
        port1Cards.add(shuffledCrew.poll());
        port1Cards.add(shuffledCrew.poll());

        ArrayList<CrewCard> port2Cards = new ArrayList<>();
        port2Cards.add(shuffledCrew.poll());
        port2Cards.add(shuffledCrew.poll());

        Tile[][] matrix = board.getTileMatrix();
        PortTile venice = (PortTile) matrix[0][13];
        PortTile amsterdam = (PortTile) matrix[19][6];
        venice.setCrewCards(port1Cards);
        amsterdam.setCrewCards(port2Cards);

        crewCardQueue = shuffledCrew;

    }

    public StateManager getGamestate() {
        return gamestate;
    }

    public void setGamestate(StateManager gamestate) {
        this.gamestate = gamestate;
    }

    public Queue<ChanceCard> getChanceCardQueue() {
        return chanceCardQueue;
    }

    public void setChanceCardQueue(Queue<ChanceCard> chanceCardQueue) {
        this.chanceCardQueue = chanceCardQueue;
    }

    public Queue<CrewCard> getCrewCardQueue() {
        return crewCardQueue;
    }

    public void setCrewCardQueue(Queue<CrewCard> crewCardQueue) {
        this.crewCardQueue = crewCardQueue;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    public void delegateTreasure() {
        Tile[][] matrix = board.getTileMatrix();
        PortTile venice = (PortTile) matrix[0][13];
        PortTile amsterdam = (PortTile) matrix[19][6];
        int vValue = 0;
        List<CrewCard> vCards;
        vCards = venice.getCrewCards();
        for (CrewCard c : vCards) {
            vValue = vValue + Math.abs(c.getValue());
        }
        int treasureV = 8 - vValue;

        int aValue = 0;
        List<CrewCard> aCards;
        aCards = amsterdam.getCrewCards();
        for (CrewCard c : aCards) {
            aValue = aValue + Math.abs(c.getValue());
        }

        int treasureA = 8 - aValue;


        if (treasureV == 2) {
            ((PortTile) matrix[0][13]).addTreasure(Treasure.RUM);
        } else if (treasureV == 3) {
            ((PortTile) matrix[0][13]).addTreasure(Treasure.PEARL);
        } else if (treasureV == 4) {
            ((PortTile) matrix[0][13]).addTreasure(Treasure.GOLD);
        } else if (treasureV == 5) {
            ((PortTile) matrix[0][13]).addTreasure(Treasure.RUBY);
        }else if (treasureV == 6){
            ((PortTile) matrix[0][13]).addTreasure(Treasure.RUM);
            ((PortTile) matrix[0][13]).addTreasure(Treasure.GOLD);
        }

        if (treasureA == 2) {
            ((PortTile) matrix[19][6]).addTreasure(Treasure.RUM);
        } else if (treasureA == 3) {
            ((PortTile) matrix[19][6]).addTreasure(Treasure.PEARL);
        } else if (treasureA == 4) {
            ((PortTile) matrix[19][6]).addTreasure(Treasure.GOLD);
        } else if (treasureA == 5) {
            ((PortTile) matrix[19][6]).addTreasure(Treasure.DIAMOND);
        } else if (treasureA == 6){
            ((PortTile) matrix[19][6]).addTreasure(Treasure.RUM);
            ((PortTile) matrix[19][6]).addTreasure(Treasure.GOLD);
        }
        for (int i = 0; i < 4; i++) {
            treasures.add(Treasure.RUM);
            treasures.add(Treasure.GOLD);
            treasures.add(Treasure.DIAMOND);
            treasures.add(Treasure.PEARL);
            treasures.add(Treasure.RUBY);
        }
        Collections.shuffle(treasures);

    }

    public Player findCurrentPlayer() {
        int tn = gamestate.getTurnNumber();
        return playerList.get(tn % 4);
    }

    public Player findPlayerBefore() {
        Player currentPlayer;
        int tn = (gamestate.getTurnNumber() + 5);
        return currentPlayer = playerList.get(tn % 4);
    }

    private void assignPorts(ArrayList<Player> players) {
        ArrayList<Port> portlist = new ArrayList<>();
        portlist.add(Port.LONDON);
        portlist.add(Port.CADIZ);
        portlist.add(Port.GENOA);
        portlist.add(Port.MARSEILLES);
        Collections.shuffle(portlist);

        for (int i = 0; i < players.size(); i++) {
            PortTile portTile = new PortTile();
            portTile.setPort(portlist.get(i));
            players.get(i).setHomePort(portTile);
        }
        playerList = players;
    }

    public void preparePlayers(ArrayList<Player> players) {
        assignPorts(players);
        playerList = players;
    }

    public void prepareBoard() {
        delegateTreasure();
    }
}
