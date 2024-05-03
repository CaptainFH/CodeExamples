package uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable;

/**
 * This class represents instances of chance cards, it contains an index specific to each instance
 *
 * @author Parsa Daneshwar Haghighi (pad31)
 * @version 0.1
 *
 * Copyright (c) 2022 Aberystwyth University.
 * All rights reserved.
 */

public class ChanceCard {
    private String text;

    public ChanceCard() {;}

    public ChanceCard(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
