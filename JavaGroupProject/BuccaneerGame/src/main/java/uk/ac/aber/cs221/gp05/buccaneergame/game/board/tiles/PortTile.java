package uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles;

import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.CrewCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;

import java.util.ArrayList;

/**
 * A class to keep information just about Port Tiles
 * extends Tile class which stores information about
 * every single Tile in 20x20 grid as a GameBoard
 *
 * @author Igor Daniulak (igd4)
 * @version 0.1
 */
public class PortTile extends Tile {
    //fields
    private Port port;
    private ArrayList<Treasure> treasurePile;
    private ArrayList<CrewCard> crewCards;

//constructors
    /**
     * to create a particular port
     * @param port appropriate enum name
     */
    public PortTile(Port port){
        treasurePile = new ArrayList<>();
        crewCards = new ArrayList<>();
        this.port = port;
    }

    public PortTile() {
        treasurePile = new ArrayList<>();
        crewCards = new ArrayList<>();

    }

    public ArrayList<CrewCard> getCrewCards() {
        return crewCards;
    }

    public void setCrewCards(ArrayList<CrewCard> crewCards) {
        this.crewCards = crewCards;
    }

    public void setTreasurePile(ArrayList<Treasure> treasurePile) {
        this.treasurePile = treasurePile;
    }
    public Tile returnTileType(){
        return this;
    }
    //methods
    public ArrayList<Treasure> getTreasure() {
        return treasurePile;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }
    public void addCrewCard(CrewCard card) {
        crewCards.add(card);
    }

    public void addTreasure(Treasure treasure) {
        treasurePile.add(treasure);
    }

    public void removeCrewCard(CrewCard card) {
        crewCards.remove(card);
    }

    public int getTotalTreasureValue(){
        int total = 0;
        for(Treasure e: treasurePile){
            total += e.getValue();
        }
        return total;
    }

    public void removeTreasure (Treasure treasure) {
        treasurePile.remove(treasure);
    }

    public boolean isPort(){
        return true;
    }
}
