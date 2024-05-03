package uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *  A template for crew card used to store fight and
 *  movement values for a Player
 *
 * @author Igor Daniulak (igd4)
 * @author Jay Kirkham (jak77)
 * @version 0.1
 * @date 02/05/2022
 *
 * Copyright (c) 2022 Aberystwyth University.
 * All rights reserved.
 */
public class CrewCard {
    //fields
    private int value;
    private Image picture;


    //constructors
    /**
     * creates a new Crew Card
     * @param value the number of the card (positive for red, negative for black, from 1 to 3)
     */
    public CrewCard(int value) throws FileNotFoundException {
        this.value = value;
        switch (value){
            case -1 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_1_red.png"));
            case -2 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_2_red.png"));
            case -3 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_3_red.png"));
            case 1 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_1_black.png"));
            case 2 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_2_black.png"));
            case 3 -> picture = new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\hoverMenu\\card_3_black.png"));
        }
        //picture = **PATH
    }

    public CrewCard(int value, String url) throws FileNotFoundException {
        this.value = value;
        this.picture = new Image(new FileInputStream(url));
    }

    public int getValue() {
        return value;
    }

    public Image getPicture() {
        return picture;
    }

    //methods
}
