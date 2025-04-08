package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.io.IOException;

public class CharacterSelectController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView king;
    @FXML
    private ImageView knight;
    @FXML
    private ImageView cleric;
    @FXML
    private ImageView mage;
    private GameStateManager gameState = GameStateManager.getInstance();


    @FXML
    private void initialize(){
        super.init(rootPane);
        infoLabel.setText("Player 1, choose your character.");
        if(gameState.getPlayerCount() == 1){
            only1Player();
        }
        alreadySelectedCheck();
    }

    public void only1Player(){
        disableImage(knight);
        disableImage(cleric);
        disableImage(mage);
    }

    public void alreadySelectedCheck(){
        for (Character character : gameState.getParty()) {
            String characterName = character.getName();
            switch (characterName){
                case "King":
                    disableImage(king);
                    break;
                case "Knight":
                    disableImage(knight);
                    break;
                case "Cleric":
                    disableImage(cleric);
                    break;
                case "Mage":
                    disableImage(mage);
                    break;
            }
        }
    }

    @FXML
    private void dosmth(ActionEvent event){

    }
    @FXML
    private void goToStatRoll(MouseEvent  event) throws IOException {
        ImageView clickedImage = (ImageView) event.getSource();
        switch (clickedImage.getId()){
            case "king":
                gameState.createKing();
                gameState.setCurrentCharacter(gameState.getKing());
                gameState.addToParty(gameState.getCurrentCharacter());

                break;
            case "knight":
                gameState.createKnight();
                gameState.setCurrentCharacter(gameState.getKnight());
                gameState.addToParty(gameState.getCurrentCharacter());
                break;
            case "cleric":
                gameState.createCleric();
                gameState.setCurrentCharacter(gameState.getCleric());
                gameState.addToParty(gameState.getCurrentCharacter());
                break;
            case "mage":
                gameState.createMage();
                gameState.setCurrentCharacter(gameState.getMage());
                gameState.addToParty(gameState.getCurrentCharacter());
                break;
        }
        switchScene(event,"StatRoll");
    }
    @FXML
    public void hovered(MouseEvent event){
        ImageView clickedImage = (ImageView) event.getSource();
        highlight(clickedImage);
    }
    @FXML
    public void unHovered(MouseEvent event){
        ImageView clickedImage = (ImageView) event.getSource();
        unhighlight(clickedImage);
    }

}
