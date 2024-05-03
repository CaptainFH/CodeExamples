package uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles;

import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;

/**
 * Main tile class to be used as a base for the game board
 * This will be inherited by the port and island enums
 *
 * @author Fernando Rodriguez (fet8)
 * @version 0.1 class created
 */
/*
    This tile will be used as the base for the game board.
    This should be inherited by port and island and using that inheritance to differ between them
 */
public class Tile {
    //Current player should be used to trigger a battle
    //If equal null, no player is present on the tile
//    private Player player;
    public Tile(){};

//    public void setPlayer(Player p) {
//        this.player = p;
//    }
    public Tile returnTileType(){
        return this;
    }

//    public boolean hasPlayer(){
//        return player != null;
//    }

    @Override
    public String toString() {
        return "Tile{" +
//                "player=" + player +
                '}';
    }
    public boolean isIsland(){
        return false;
    }
    public boolean isPort(){
        return false;
    }

    public boolean isTreasureIsland(){
        return false;
    }
}
