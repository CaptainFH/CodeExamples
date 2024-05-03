package uk.ac.aber.cs221.gp05.buccaneergame.game.board;

import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.*;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Direction;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.State;


import java.util.ArrayList;

import static uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser.game;

/**
 * A class responsible for the creation of the island tiles and port tiles on the game borad alongside
 * the random assignment of home ports to players.
 *
 * @author Jack Gannon (jag78),
 * @author Mick Barabasz (mib48)
 * @version 0.1 Class created
 * @version 0.2 Tile matrix for the islands created
 * @see Player
 * @see Tile
 * @see Port
 * @see Direction
 */

public class GameBoard {
    private Tile[][] tileMatrix = new Tile[20][20];
    public IslandTile pirateI = new IslandTile(Island.PIRATE);
    public IslandTile treasureI = new IslandTile(Island.TREASURE);
    public IslandTile flatI = new IslandTile(Island.FLAT);


    public GameBoard() {
    }

    public Tile[][] getTileMatrix() {
        return tileMatrix;
    }

    public void createBoard() {

        ArrayList<Player> players = game.getPlayerList();
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                tileMatrix[x][y] = new Tile();
            }
        }

        // Assigns home ports randomly to players
        //THIS METHOD DOES NOT WORK FOR NOW
        for (Player p : players) {
            if (p.getPortTile().getPort() == Port.LONDON) {
                tileMatrix[0][6] = p.getPortTile();
                p.setDirection(Direction.E);
                p.setLocation(new Position(0, 6));
            } else if (p.getPortTile().getPort() == Port.GENOA) {
                tileMatrix[6][19] = p.getPortTile();
                p.setDirection(Direction.N);
                p.setLocation(new Position(6, 19));
            } else if (p.getPortTile().getPort() == Port.MARSEILLES) {
                tileMatrix[19][13] = p.getPortTile();
                p.setDirection(Direction.W);
                p.setLocation(new Position(19, 13));
            } else if (p.getPortTile().getPort() == Port.CADIZ) {
                tileMatrix[13][0] = p.getPortTile();
                p.setDirection(Direction.S);
                p.setLocation(new Position(13, 0));
            }

        }
        //Port of Venice at (1,7)
        //Port of London at (1,14)
        //Port of Cadiz at (14,20)
        //Port of Amsterdam at (20, 14)
        //Port of Marseilles at (20,7)
        //Port of Genoa at (7,1)
        //this is from spec, in spec (1,1) is bottom left
        //so its ours (0,19)
        //(x vertical , y horizontal)
        //the first value is the same as spec -1
        //the other is 20- this value-1
        //THIS IS CORRECT
        tileMatrix[0][13] = new PortTile(Port.VENICE);
        tileMatrix[19][6] = new PortTile(Port.AMSTERDAM);


        //tileMatrix[0][6] = new PortTile(Port.LONDON);
        //tileMatrix[13][0] = new PortTile(Port.CADIZ);
        //tileMatrix[6][19] = new PortTile(Port.GENOA);
        //tileMatrix[19][13] = new PortTile(Port.MARSEILLES);

        //Change the tiles to Island tiles where the islands are. Use the same format as I have for the port tiles.

        // Replaces tiles on the board with flat island tiles
        tileMatrix[1][1] = flatI;
        tileMatrix[1][2] = flatI;
        tileMatrix[1][3] = flatI;
        tileMatrix[1][4] = flatI;

        tileMatrix[2][1] = flatI;
        tileMatrix[2][2] = flatI;
        tileMatrix[2][3] = flatI;
        tileMatrix[2][4] = flatI;

        tileMatrix[3][1] = flatI;
        tileMatrix[3][2] = flatI;
        tileMatrix[3][3] = flatI;
        tileMatrix[3][4] = flatI;

        // Replaces tiles on the board with pirate island tiles


        tileMatrix[16][15] = pirateI;
        tileMatrix[16][16] = pirateI;
        tileMatrix[16][17] = pirateI;
        tileMatrix[16][18] = pirateI;


        tileMatrix[17][15] = pirateI;
        tileMatrix[17][16] = pirateI;
        tileMatrix[17][17] = pirateI;
        tileMatrix[17][18] = pirateI;


        tileMatrix[18][15] = pirateI;
        tileMatrix[18][16] = pirateI;
        tileMatrix[18][17] = pirateI;
        tileMatrix[18][18] = pirateI;

        // Replaces tiles on the board with treasureI island tiles

        tileMatrix[8][8] = treasureI;
        tileMatrix[8][9] = treasureI;
        tileMatrix[8][10] = treasureI;
        tileMatrix[8][11] = treasureI;

        tileMatrix[9][8] = treasureI;
        tileMatrix[9][9] = treasureI;
        tileMatrix[9][10] = treasureI;
        tileMatrix[9][11] = treasureI;

        tileMatrix[10][8] = treasureI;
        tileMatrix[10][9] = treasureI;
        tileMatrix[10][10] = treasureI;
        tileMatrix[10][11] = treasureI;

        tileMatrix[11][8] = treasureI;
        tileMatrix[11][9] = treasureI;
        tileMatrix[11][10] = treasureI;
        tileMatrix[11][11] = treasureI;
    }


    public void teleport(Player player, Position dest) {
        player.setLocation(dest);
    }

    public Island checkSurroundings(Position playerpos){
        int x = playerpos.getX();
        int y = playerpos.getY();
        if(x + 1 >= 20 || x - 1 < 0){
            return Island.NOT;
        }
        if(y + 1 >= 20 || y - 1 < 0){
            return Island.NOT;
        }
        if(tileMatrix[x-1][y-1] instanceof IslandTile ){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x-1][x-1]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x-1][y] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x-1][y]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x-1][y+1] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x-1][y+1]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x][y+1] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x][y+1]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x][y-1] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x][y-1]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x+1][y-1] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x+1][y-1]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x+1][y] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x+1][y]) ;//TL
            return tileIQ.getIsland();
        }
        if(tileMatrix[x+1][y+1] instanceof IslandTile){
            IslandTile tileIQ = ((IslandTile)tileMatrix[x+1][y+1]) ;//TL
            return tileIQ.getIsland();
        }
        return Island.NOT;
    }

    public ArrayList<Position> checkPossibleRotations(Player player) {
        Tile[][] forChecks = game.getBoard().getTileMatrix();
        ArrayList<Position> possibleRotations = new ArrayList<>();
        Position currentPosition = player.getLocation();
        Direction currentDirection = player.getDirection();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Position newPosition = new Position(currentPosition.getX() + i, currentPosition.getY() + j);
                if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                    if (newPosition.getY() == currentPosition.getY() && newPosition.getX() == currentPosition.getX()) {

                    } else {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {

                        } else possibleRotations.add(newPosition);

                    }


                }

            }

        }

        return possibleRotations;
    }

    public ArrayList<Position> checkPossibleMovesInPort(Player player) {
        Tile[][] forChecks = game.getBoard().getTileMatrix();
        ArrayList<Position> possibleMoves = new ArrayList<>();
        Position currentPosition = player.getLocation();
        Direction currentDirection = player.getDirection();
        int movementValue = player.getCrewPower();
        switch (currentDirection) {
            case N -> {


                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() - i);
                    newPosition.setX(currentPosition.getX());
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }
                    }
                }
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition2 = new Position();
                    newPosition2.setY(currentPosition.getY() - i);
                    newPosition2.setX(currentPosition.getX() + i);
                    if (newPosition2.getY() < 20 && newPosition2.getX() < 20 && newPosition2.getX() > -1 && newPosition2.getY() > -1) {
                        if (forChecks[newPosition2.getX()][newPosition2.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition2);
                        }

                    }
                }


                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition3 = new Position();
                    newPosition3.setY(currentPosition.getY() - i);
                    newPosition3.setX(currentPosition.getX() - i);
                    if (newPosition3.getY() < 20 && newPosition3.getX() < 20 && newPosition3.getX() > -1 && newPosition3.getY() > -1) {
                        if (forChecks[newPosition3.getX()][newPosition3.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition3);
                        }

                    }
                }
            }


            case E -> {


                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY());
                    newPosition.setX(currentPosition.getX() + i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    }
                }

                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition2 = new Position();
                    newPosition2.setY(currentPosition.getY() - i);
                    newPosition2.setX(currentPosition.getX() + i);
                    if (newPosition2.getY() < 20 && newPosition2.getX() < 20 && newPosition2.getX() > -1 && newPosition2.getY() > -1) {
                        if (forChecks[newPosition2.getX()][newPosition2.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition2);
                        }

                    }

                }
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition3 = new Position();
                    newPosition3.setY(currentPosition.getY() + i);
                    newPosition3.setX(currentPosition.getX() + i);
                    if (newPosition3.getY() < 20 && newPosition3.getX() < 20 && newPosition3.getX() > -1 && newPosition3.getY() > -1) {
                        if (forChecks[newPosition3.getX()][newPosition3.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition3);
                        }

                    }
                }


            }

            case S -> {


                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() + i);
                    newPosition.setX(currentPosition.getX());
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    }
                }

                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition2 = new Position();
                    newPosition2.setY(currentPosition.getY() + i);
                    newPosition2.setX(currentPosition.getX() + i);
                    if (newPosition2.getY() < 20 && newPosition2.getX() < 20 && newPosition2.getX() > -1 && newPosition2.getY() > -1) {
                        if (forChecks[newPosition2.getX()][newPosition2.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition2);
                        }

                    }
                }

                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition3 = new Position();
                    newPosition3.setY(currentPosition.getY() + i);
                    newPosition3.setX(currentPosition.getX() - i);
                    if (newPosition3.getY() < 20 && newPosition3.getX() < 20 && newPosition3.getX() > -1 && newPosition3.getY() > -1) {
                        if (forChecks[newPosition3.getX()][newPosition3.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition3);
                        }

                    }
                }

            }
            case W -> {


                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY());
                    newPosition.setX(currentPosition.getX() - i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    }
                }
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition2 = new Position();
                    newPosition2.setY(currentPosition.getY() + i);
                    newPosition2.setX(currentPosition.getX() - i);
                    if (newPosition2.getY() < 20 && newPosition2.getX() < 20 && newPosition2.getX() > -1 && newPosition2.getY() > -1) {
                        if (forChecks[newPosition2.getX()][newPosition2.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition2);
                        }

                    }
                }
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition3 = new Position();

                    newPosition3.setY(currentPosition.getY() - i);
                    newPosition3.setX(currentPosition.getX() - i);
                    if (newPosition3.getY() < 20 && newPosition3.getX() < 20 && newPosition3.getX() > -1 && newPosition3.getY() > -1) {
                        if (forChecks[newPosition3.getX()][newPosition3.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition3);
                        }

                    }


                }
            }
        }
        return possibleMoves;
    }

    public ArrayList<Position> checkPossibleMoves(Player player) {
        Tile[][] forChecks = game.getBoard().getTileMatrix();
        ArrayList<Position> possibleMoves = new ArrayList<>();
        Position currentPosition = player.getLocation();
        Direction currentDirection = player.getDirection();
        int movementValue = player.getCrewPower();
        switch (currentDirection) {
            case N -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() - i);
                    newPosition.setX(currentPosition.getX());
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
            case NE -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() - i);
                    newPosition.setX(currentPosition.getX() + i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
            case E -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY());
                    newPosition.setX(currentPosition.getX() + i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }

            case SE -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() + i);
                    newPosition.setX(currentPosition.getX() + i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
            case S -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() + i);
                    newPosition.setX(currentPosition.getX());
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }

                }
            }
            case SW -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() + i);
                    newPosition.setX(currentPosition.getX() - i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
            case W -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY());
                    newPosition.setX(currentPosition.getX() - i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
            case NW -> {
                for (int i = 1; i <= movementValue; i++) {
                    Position newPosition = new Position();
                    newPosition.setY(currentPosition.getY() - i);
                    newPosition.setX(currentPosition.getX() - i);
                    if (newPosition.getY() < 20 && newPosition.getX() < 20 && newPosition.getX() > -1 && newPosition.getY() > -1) {
                        if (forChecks[newPosition.getX()][newPosition.getY()].isIsland()) {
                            break;
                        } else {
                            possibleMoves.add(newPosition);
                        }

                    } else {
                        break;
                    }


                }
            }
        }
        return possibleMoves;
    }



}