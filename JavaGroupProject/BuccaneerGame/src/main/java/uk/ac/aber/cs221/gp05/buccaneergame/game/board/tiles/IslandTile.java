package uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles;

import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;

import java.util.ArrayList;

/**
 * A class to keep information just about Island Tiles
 * extends Tile class which stores information about
 * every single Tile in 20x20 grid as a GameBoard
 *
 * @author Igor Daniulak (igd4)
 * @version 0.1 Class created amd functionality added
 */
public class IslandTile extends Tile {
    //fields
    private Island island;
    private ArrayList<Treasure> treasures;

    public Island getIsland() {
        return island;
    }
    //constructors

    @Override
    public String toString() {
        return "IslandTile{" +
                "island=" + island +
                ", treasures=" + treasures +
                '}';
    }

    /**
     * creates an instance of IslandTile
     * @param island enum of appropriate created Island
     */
    public IslandTile(Island island){
        this.island = island;
        treasures = new ArrayList<>();
    }

    //methods
    public void addTreasure(Treasure treasure){
        treasures.add(treasure);
    }
    public Tile returnTileType(){
        return this;
    }
    public boolean isIsland(){
        return true;
    }

    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }
}
