package uk.ac.aber.cs221.gp05.buccaneergame.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp05.buccaneergame.game.GameInitialiser;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Colour;
import uk.ac.aber.cs221.gp05.buccaneergame.game.instantiable.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for the control of the player selection system
 * This class uses a FXML UI with buttons and text entry fields which are used to implement functionality
 *
 * @author Fernando Rodriguez (fet8)
 * @author Jack Gannon (jag78)
 * @version 0.1 Class created
 * @version 0.2 Added functionality for the input of player names and passing them to the game manager
 * @version 0.3 Added the color selection function
 * @version 0.4 Added functionality to the buttons allowing for the selecting of color using buttons,
 *              Pressing the "set sail" button to show readiness and launching the game using the "start" button.
 * @see Player
 */

//Controller class for select player

public class SelectPlayerCtrl {

    //Main background
    @FXML
    private AnchorPane background;
    //Title
    @FXML
    private AnchorPane titlePane;
    @FXML
    private AnchorPane playerSelectMain;
    //Player Contents
    @FXML
    private AnchorPane player1;
    @FXML
    private AnchorPane player2;
    @FXML
    private AnchorPane player3;
    @FXML
    private AnchorPane player4;
    //SetSail buttons
    @FXML
    private Pane setSail1;
    @FXML
    private Pane setSail2;
    @FXML
    private Pane setSail3;
    @FXML
    private Pane setSail4;
    //Back button

    @FXML
    private Pane backButton;
    @FXML
    private Text backButtonText;
    //Start button
    @FXML
    private Pane startButton;
    @FXML
    private Text startButtonText;

    @FXML
    private TextField player1NameInput;
    @FXML
    private TextField player2NameInput;
    @FXML
    private TextField player3NameInput;
    @FXML
    private TextField player4NameInput;

    //Get names that are inputted
    //get colours that are inputted
    //pass these through to the game manager
    private ArrayList<Player> players = new ArrayList<>();
    private Colour[] playerColours = new Colour[4];

    private boolean[] areButtonsPressed = new boolean[]{false,false,false,false};
    private int playersReady = 0;

    private boolean canBeReady1 = true;

    private boolean canBeReady2 = true;

    private boolean canBeReady3 = true;

    private boolean canBeReady4 = true;
    @FXML
    public void createPlayer1(){
        if (!(player1NameInput.getText().length()  < 0 || player1NameInput.getText().length() > 27) && !(player1NameInput.getText().trim().equals("")) && !(playerColours[0] == null)) {
            if (canBeReady1) {
                getInfomation(player1, 0);
                canBeReady1 = false;
                lockInput(setSail1);
            }
        }
    }
    @FXML
    public void createPlayer2(){
        if (!(player2NameInput.getText().length()  < 0 || player2NameInput.getText().length() > 27) && !(player2NameInput.getText().trim().equals("")) && !(playerColours[1] == null)) {
            if (canBeReady2) {
                getInfomation(player2, 1);
                canBeReady2 = false;
                lockInput(setSail2);
            }
        }
    }
    @FXML
    public void createPlayer3(){
        if (!(player3NameInput.getText().length()  < 0 || player3NameInput.getText().length() > 27)&& !(player3NameInput.getText().trim().equals("")) && !(playerColours[2] == null)) {
            if (canBeReady3) {
                getInfomation(player3, 2);
                canBeReady3 = false;
                lockInput(setSail3);
            }
        }
    }
    @FXML
    public void createPlayer4(){
        if (!(player4NameInput.getText().length()  < 0 || player4NameInput.getText().length() > 27) && !(player4NameInput.getText().trim().equals("")) && !(playerColours[3] == null)) {
            if (canBeReady4) {
                getInfomation(player4, 3);
                canBeReady4 = false;
                lockInput(setSail4);
            }
        }
    }
    private void lockInput(Pane setSail){
        setSail.setStyle("-fx-background-color: red; -fx-border-color:  #73623a; -fx-border-width: 4");

    }
    boolean canStart = false;
    @FXML
    public void getInfomation(AnchorPane playerInfo, int player){
        /*
        For each field in the fxml
        create a new player,
            Set players name to the text in field
            Set players colour
            Add player to the array list
        Pass the arrayList of players to the game manager
         */
        TextField nameInputted = (TextField) playerInfo.getChildren().get(2);
        System.out.println("Player " + nameInputted.getText() + " created");
        players.add(new Player (nameInputted.getText(), playerColours[player]));
        playersReady++;
        if(playersReady == 4) {
            startButton.setStyle("-fx-background-color:  #00b048; -fx-border-color: black; -fx-border-width: 3");
            canStart = true;
        }
    }
    @FXML
    public void backToMenu() throws IOException {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
    Scene sceneToBeChangedTo = new Scene(root);
    Stage mainWindow = (Stage) backButton.getScene().getWindow();
    mainWindow.setScene(sceneToBeChangedTo);
    }
    @FXML
    public void sendInfo() throws IOException {
        System.out.print("Start was called");
        if (canStart) {
            GameInitialiser.game.preparePlayers(players);
            //createBoard
            GameInitialiser.game.getBoard().createBoard();
            GameInitialiser.game.delegateCrew();
            GameInitialiser.game.delegateTreasure();

            loadGame();

        }
    }



    private void loadGame() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameBoard.fxml")));
        Scene sceneToBeChangedTo = new Scene(root);
        Stage mainWindow = (Stage) backButton.getScene().getWindow();
        mainWindow.setFullScreen(true);
        mainWindow.setScene(sceneToBeChangedTo);
    }
    @FXML
    public void orangeButton1() throws FileNotFoundException {
        if (!areButtonsPressed[0]) {
            if(!(playerColours[0] == null)){
                if(playerColours[0] == Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[0] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[0] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(0);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color:  #ff7f27; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player1.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_orange_big.png")));
            playerColours[0] = Colour.ORANGE;
            areButtonsPressed[0] = true;
            disableButtons(1,0);
        }
    }
    @FXML
    public void orangeButton2() throws FileNotFoundException {
        if (!areButtonsPressed[0]) {
            if(!(playerColours[1] == null)){
                if(playerColours[1] == Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[1] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[1] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player2.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(0);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color:  #ff7f27; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player2.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_orange_big.png")));
            playerColours[1] = Colour.ORANGE;
            areButtonsPressed[0] = true;
            disableButtons(2,0);
        }
    }
    @FXML
    public void orangeButton3() throws FileNotFoundException {
        if (!areButtonsPressed[0]) {
            if(!(playerColours[2] == null)){
                if(playerColours[2] == Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[2] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[2] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player3.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(0);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color:  #ff7f27; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player3.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_orange_big.png")));
            playerColours[2] = Colour.ORANGE;
            areButtonsPressed[0] = true;
            disableButtons(3,0);
        }
    }
    @FXML
    public void orangeButton4() throws FileNotFoundException {
        if (!areButtonsPressed[0]) {
            if(!(playerColours[3] == null)){
                if(playerColours[3] == Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[3] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[3] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player4.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(0);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color:  #ff7f27; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player4.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_orange_big.png")));
            playerColours[3] = Colour.ORANGE;
            areButtonsPressed[0] = true;
            disableButtons(4,0);
        }
    }
    @FXML
    public void blueButton1() throws FileNotFoundException {
        if (!areButtonsPressed[1]) {
            if(!(playerColours[0] == null)){
                if(playerColours[0] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[0] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[0] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(1);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #00a2e8; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player1.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_blue_big.png")));
            playerColours[0] = Colour.BLUE;
            areButtonsPressed[1] = true;
            disableButtons(1,1);
        }
    }
    @FXML
    public void blueButton2() throws FileNotFoundException {
        if (!areButtonsPressed[1]) {
            if(!(playerColours[1] == null)){
                if(playerColours[1] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[1]== Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[1]== Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player2.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(1);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #00a2e8; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player2.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_blue_big.png")));
            playerColours[1] = Colour.BLUE;
            areButtonsPressed[1] = true;
            disableButtons(2,1);
        }
    }
    @FXML
    public void blueButton3() throws FileNotFoundException {
        if (!areButtonsPressed[1]) {
            if(!(playerColours[2] == null)){
                if(playerColours[2]== Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[2]== Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[2] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player3.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(1);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #00a2e8; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player3.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_blue_big.png")));
            playerColours[2] = Colour.BLUE;
            areButtonsPressed[1] = true;
            disableButtons(3,1);
        }
    }
    @FXML
    public void blueButton4() throws FileNotFoundException {
        if (!areButtonsPressed[1]) {
            if(!(playerColours[3] == null)){
                if(playerColours[3] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[3]== Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                } else if (playerColours[3] == Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player4.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(1);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #00a2e8; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player4.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_blue_big.png")));
            playerColours[3] = Colour.BLUE;
            areButtonsPressed[1] = true;
            disableButtons(4,1);
        }
    }
    @FXML
    public void greenButton1() throws FileNotFoundException {
        if (!areButtonsPressed[2]) {
            if(!(playerColours[0] == null)){
                if(playerColours[0] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[0]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[0]== Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(2);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #1b8d3c; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player1.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_green_big.png")));
            playerColours[0] = Colour.GREEN;
            areButtonsPressed[2] = true;
            disableButtons(1,2);
        }
    }
    @FXML
    public void greenButton2() throws FileNotFoundException {
        if (!areButtonsPressed[2]) {
            if(!(playerColours[1] == null)){
                if(playerColours[1] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[1]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[1]== Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player2.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(2);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #1b8d3c; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player2.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_green_big.png")));
            playerColours[1] = Colour.GREEN;
            areButtonsPressed[2] = true;
            disableButtons(2,2);
        }
    }
    @FXML
    public void greenButton3() throws FileNotFoundException {
        if (!areButtonsPressed[2]) {
            if(!(playerColours[2] == null)){
                if(playerColours[2] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[2]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[2]== Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player3.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(2);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #1b8d3c; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player3.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_green_big.png")));
            playerColours[2] = Colour.GREEN;
            areButtonsPressed[2] = true;
            disableButtons(3,2);
        }
    }
    @FXML
    public void greenButton4() throws FileNotFoundException {
        if (!areButtonsPressed[2]) {
            if(!(playerColours[3] == null)){
                if(playerColours[3] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[3]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[3]== Colour.YELLOW){
                    activateButtons(3);
                    areButtonsPressed[3] = false;
                }
            }
            Pane playerClicked = (Pane) player4.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(2);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #1b8d3c; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player4.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_green_big.png")));
            playerColours[3] = Colour.GREEN;
            areButtonsPressed[2] = true;
            disableButtons(4,2);
        }
    }
    @FXML
    public void yellowButton1() throws FileNotFoundException {
        if (!areButtonsPressed[3]) {
            if(!(playerColours[0] == null)){
                if(playerColours[0] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[0]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[0] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                }
            }
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(3);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #fff200; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player1.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_yellow_big.png")));
            playerColours[0] = Colour.YELLOW;
            areButtonsPressed[3] = true;
            disableButtons(1,3);
        }
    }
    @FXML
    public void yellowButton2() throws FileNotFoundException {
        if (!areButtonsPressed[3]) {
            if(!(playerColours[1] == null)){
                if(playerColours[1] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[1]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[1] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                }
            }
            Pane playerClicked = (Pane) player2.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(3);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #fff200; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player2.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_yellow_big.png")));
            playerColours[1] = Colour.YELLOW;
            areButtonsPressed[3] = true;
            disableButtons(2,3);
        }
    }
    @FXML
    public void yellowButton3() throws FileNotFoundException {
        if (!areButtonsPressed[3]) {
            if(!(playerColours[2] == null)){
                if(playerColours[2] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[2]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[2] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                }
            }
            Pane playerClicked = (Pane) player3.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(3);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #fff200; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player3.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_yellow_big.png")));
            playerColours[2] = Colour.YELLOW;
            areButtonsPressed[3] = true;
            disableButtons(3,3);
        }
    }
    @FXML
    public void yellowButton4() throws FileNotFoundException {
        if (!areButtonsPressed[3]) {
            if(!(playerColours[3] == null)){
                if(playerColours[3] == Colour.ORANGE){
                    activateButtons(0);
                    areButtonsPressed[0] = false;
                } else if (playerColours[3]== Colour.BLUE){
                    activateButtons(1);
                    areButtonsPressed[1] = false;
                } else if (playerColours[3] == Colour.GREEN){
                    activateButtons(2);
                    areButtonsPressed[2] = false;
                }
            }
            Pane playerClicked = (Pane) player4.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(3);
            buttonClicked.setStyle("-fx-border-color: red; -fx-background-color: #fff200; -fx-border-width: 5");
            Pane selectedImagePane = (Pane) player4.getChildren().get(0);
            ImageView selectedImage = (ImageView) selectedImagePane.getChildren().get(0);
            selectedImage.setImage(new Image(new FileInputStream("src\\main\\resources\\uk\\ac\\aber\\cs221\\gp05\\buccaneergame\\assets\\selectPlayerView\\boat_yellow_big.png")));
            playerColours[3] = Colour.YELLOW;
            areButtonsPressed[3] = true;
            disableButtons(4,3);
        }
    }
    private void disableButtons(int notToDisable, int buttonToDisable){
        if(notToDisable != 1){
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(buttonToDisable);
            buttonClicked.setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-width: 5");
        }
        if(notToDisable != 2){
            Pane playerClicked = (Pane) player2.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(buttonToDisable);
            buttonClicked.setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-width: 5");
        }
        if(notToDisable != 3){
            Pane playerClicked = (Pane) player3.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(buttonToDisable);
            buttonClicked.setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-width: 5");
        }
        if(notToDisable != 4){
            Pane playerClicked = (Pane) player4.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(buttonToDisable);
            buttonClicked.setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-width: 5");
        }
    }
    private void activateButtons (int buttonToActivate){
        String[] coloursToChange = new String[]{" #ff7f27"," #00a2e8"," #1b8d3c",  "#fff200"};
            Pane playerClicked = (Pane) player1.getChildren().get(1);
            Pane buttonClicked = (Pane) playerClicked.getChildren().get(buttonToActivate);
            buttonClicked.setStyle("-fx-background-color: " + coloursToChange[buttonToActivate] + "; -fx-border-color: black; -fx-border-width: 5");
             playerClicked = (Pane) player2.getChildren().get(1);
             buttonClicked = (Pane) playerClicked.getChildren().get(buttonToActivate);
            buttonClicked.setStyle("-fx-background-color: " + coloursToChange[buttonToActivate] + "; -fx-border-color: black; -fx-border-width: 5");
             playerClicked = (Pane) player3.getChildren().get(1);
             buttonClicked = (Pane) playerClicked.getChildren().get(buttonToActivate);
            buttonClicked.setStyle("-fx-background-color: " + coloursToChange[buttonToActivate] + "; -fx-border-color: black; -fx-border-width: 5");
             playerClicked = (Pane) player4.getChildren().get(1);
             buttonClicked = (Pane) playerClicked.getChildren().get(buttonToActivate);
            buttonClicked.setStyle("-fx-background-color: " + coloursToChange[buttonToActivate] + "; -fx-border-color: black; -fx-border-width: 5");
    }



}
