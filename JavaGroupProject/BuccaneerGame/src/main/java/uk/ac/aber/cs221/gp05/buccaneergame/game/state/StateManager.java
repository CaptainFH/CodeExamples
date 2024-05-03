package uk.ac.aber.cs221.gp05.buccaneergame.game.state;


import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.CrewCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Treasure;

import java.util.ArrayList;

/**
 * A class that stores the current turn number and the current game state.
 *
 * @author Nye Jones (nyj)
 * @version 1.0
 *
 * Copyright (c) Aberystwyth University
 * All rights reserved
 */
public class StateManager {

    //Variables
    private State currentState;
    private int turnNumber;


    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
//Methods
    /**
     * Creates a StateManager from parameters for the initial state and turn number
     * @param state the initial state
     * @param turnNumber the initial turn number
     */
    public StateManager(State state, int turnNumber){
        this.currentState = state;
        this.turnNumber = turnNumber;
    }

    public void addTurn(){
        turnNumber++;
    }

}
