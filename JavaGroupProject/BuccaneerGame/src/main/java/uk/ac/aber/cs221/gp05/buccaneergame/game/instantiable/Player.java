package uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable;

import uk.ac.aber.cs221.gp05.buccaneergame.game.board.Position;
import uk.ac.aber.cs221.gp05.buccaneergame.game.board.tiles.PortTile;

import java.util.ArrayList;

/**
 * Player is the class that contains all the needed information the player has - including name, colour, home port, their crew cards, their treasure and their location.
 *
 * @author Fernando Rodriguez (fet8)
 * @author Parsa Haghighi (pad31)
 * @version 0.2
 */


// Every single one of these problems are form fenando
public class Player {
    private String name; //Name was mentioned on the design doc -- needs update on the UML Diagram
    private Colour colour; //Colour was mentioned on the design doc -- needs update on the UML Diagram
    private PortTile homePort; //Port was mentioned on the desgin doc -- needs update on the UML Diagram
    private ArrayList<CrewCard> crewCards; //Used an array list for the sake of varying number of crewcards
    private ArrayList<Treasure> treasures; // Same reasoning
    private Position location; //added as infered in the design spec

    public void setTreasures(ArrayList<Treasure> treasures) {
        this.treasures = treasures;
    }

    private Direction direction;

    public Player(String newName, Colour newColour){
        this.name = newName;
        this.colour = newColour;
//        this.homePort = startUpPort;
//        this.playerLocation = startingCord;
        // ^^ Code to be deleted as these are randomly set when creating the board so no need to make hardcoded.
        this.crewCards = new ArrayList<>();
        this.treasures = new ArrayList<>();


    }

    public Player() {
    }

    public void rotatePlayer(Player toBeRotated, Direction direction) {
        //MAKE WHEN READY
        toBeRotated.setDirection(direction);
    }

    public void addTreasure(Treasure toBeAdded){
        treasures.add(toBeAdded);
    }

    //Loop made to be sure only one object is deleted
    public Treasure removeTreasure(Treasure toBeRemoved){
        for(int i = 0; i < treasures.size(); i++){
            if (treasures.get(i).equals(toBeRemoved)){
                treasures.remove(i);
                return toBeRemoved;
            }
        }
        return null;
    }

    public void addCrewCard(CrewCard toBeAdded){
        crewCards.add(toBeAdded);
    }

    //Loop made to be sure only one object is deleted
    public CrewCard removeCrewCard(CrewCard toBeRemoved){
        for(int i = 0; i < crewCards.size(); i++){
            if (crewCards.get(i).equals(toBeRemoved)){
                crewCards.remove(i);
                return toBeRemoved;
            }
        }
        return null;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getCrewPower(){
        int result = 0;
        for(CrewCard e : crewCards){
            if(e.getValue() < 4){
                result += Math.abs(e.getValue());
            }
        }
        if (result==0){
            return 1;
        }
        return result;
    }

    public ArrayList<Treasure> getTreasures() {
        treasures.trimToSize();
        return treasures;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", colour=" + colour +
                ", homePort=" + homePort +
                ", crewCards=" + crewCards +
                ", treasures=" + treasures +
                ", playerLocation=" + location +
                ", direction=" + direction +
                '}';
    }

    public Position getLocation(){
        return location;
    }

    public void setLocation(Position newCoords){

        location = newCoords;
    }
    //Function made to be sure the system can identify the player's colour.
    public Colour getColour(){
        return colour;
    }

    public PortTile getPortTile() {
        return homePort;
    }

    public ArrayList<CrewCard> getCrewCards() {
        return crewCards;
    }

    public void setCrewCards(ArrayList<CrewCard> crewCards) {
        this.crewCards = crewCards;
    }

    public ArrayList<Treasure> getTreasure() {
        return treasures;
    }

    public String getName() {
        return name;
    }
    public int getFightingPower(){
        int total = 0;
        for(CrewCard e: crewCards) {
            if (e.getValue() == 4 || e.getValue() == 5) { // Code added due to the presence of chance cards within crew cards.

            } else {
                total += e.getValue();
            }
        }
        if (total < 0){
            total = total*(-1);
        }
        return total;
    }
    public void setHomePort(PortTile port) {
        this.homePort = port;
    }
}
