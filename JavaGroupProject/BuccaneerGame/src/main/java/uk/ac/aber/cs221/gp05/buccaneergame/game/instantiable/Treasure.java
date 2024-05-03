/**
 * This class is an enum that sets the value given to each treasure item
 *
 * @author Parsa Daneshwar Haghighi
 * @version 0.1
 * @date 02/05/2022
 * Copyright (c) 2021 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable;

/**
 * Enum class referencing the five possible treasures and their vaules.
 *
 * @author Parsa Haghighi (pad31)
 * @version 1.0 Class created
 */

public enum Treasure {
    DIAMOND(5), RUBY(5), GOLD(4), PEARL(3), RUM(2);

    private int value;
    private Treasure(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
