/**
 * This class controls the display of chance cards, as well as calling the function from StateEvent to do what it says.
 *
 * @author Aneurin Alyn Ferguson Jones (nyj)
 * @verison 0.1, creation of basic skeleton
 * @version 0.2, first development pass
 * @date 04/05/2022
 *
 * Copyright (c) 2022 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp05.buccaneergame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.ChanceCard;
import uk.ac.aber.cs221.gp05.buccaneergame.game.state.StateEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser.game;

/**
 * Class responsible for the management of the chance cards
 * This class uses the ChanceCard class within the instantiable interface
 * It allows for the execution of chance cards
 *
 * @author Jay Kirkham (jak77)
 * @author Jack Gannon (jag78)
 * @version 0.1 Class is created
 * @version 0.2 Method for the execution of chance cards is implemented
 * @see ChanceCard
 */

public class ChanceCardCtrl implements Initializable {

    //VARIABLES-------------------------------------------------------
    //FXML
    @FXML
    private Label contentLabel;
    @FXML
    private Button close;
    @FXML
    private Button treasureButton;
    @FXML
    private Button cardButton;

    //PRIVATE
    //holds the chance card the controller is taking from
    private ChanceCard chanceCard = new ChanceCard();
    //The pure text taken from the chance card.
    private String rawText = new String("");
    //Stores 2 characters extracted from the rawText for conversion into an int
    private char[] characters = new char[2];
    //Stores the index of the chance card for sending to the event manager.
    private int chanceCardIndex;

    //Arraylist for alternative code
    //private ArrayList<Character> characters = new ArrayList<>();


    //METHODS-----------------------------------------------------------
    /**
     * Initialises the display by polling the chance card queue to get the chance card on top. Also gets the chance card index from the rawText.
     *
     * @param url required for the initialiser
     * @param resourcebundle also required for the initialiser
     */
    public void initialize(URL url, ResourceBundle resourcebundle) {
        treasureButton.setDisable(true);
        cardButton.setDisable(true);
        // Getting the chance card, putting its text in rawText, setting the label to display it
        chanceCard = GameInitialiser.game.getChanceCardQueue().poll();
        assert chanceCard != null;
        rawText = chanceCard.getText();
        contentLabel.setText(rawText);

        retrieveChanceCardIndex();

        if (    chanceCardIndex == 11 ||
                chanceCardIndex == 12 ||
                chanceCardIndex == 13 ||
                chanceCardIndex == 14 ||
                chanceCardIndex == 27) {

            close.setDisable(true);
            treasureButton.setDisable(false);
            cardButton.setDisable(false);
        }

        /*Alternative code to get the first 2 characters of the chance card text
        char character = rawText.charAt(0);
        characters.add(character);
        */
    }

    /**
     * Gets the first two characters of the chance card text, to be used as an identifying index.
     * */
    private void retrieveChanceCardIndex(){
        rawText.getChars(0,2, characters, 0);
        StringBuilder chanceCardS = new StringBuilder();
        for(int i : characters) {
            chanceCardS.append(i-48);
        }
        chanceCardIndex = Integer.parseInt(String.valueOf(chanceCardS));
    }

    /**
     * When the close button is pressed, the effects of the chance card are executed and the window is closed.
     *
     * @see StateEvent selectchanceCard()
     * */
    private void executeChanceCard(boolean isChosen) throws Exception {
        StateEvent.selectChanceCard(chanceCardIndex,isChosen);
    }

     //Gets FXML to close the window.
    @FXML
    private void closeWindowActionT() throws Exception {
        executeChanceCard(false);
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeWindowActionC() throws Exception {
        executeChanceCard(true);
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }


}

