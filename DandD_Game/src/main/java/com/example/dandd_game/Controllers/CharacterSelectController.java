package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterSelectController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView king;
    @FXML
    private Label kingStats;
    @FXML
    private Label kingName;
    @FXML
    private ImageView knight;
    @FXML
    private Label knightStats;
    @FXML
    private Label knightName;
    @FXML
    private ImageView cleric;
    @FXML
    private Label clericStats;
    @FXML
    private Label clericName;
    @FXML
    private ImageView mage;
    @FXML
    private Label mageStats;
    @FXML
    private Label mageName;
    @FXML
    private Button ready_btn;

    private GameStateManager gameState = GameStateManager.getInstance();


    @FXML
    private void initialize(){
        super.init(rootPane);
        only1PlayerLeft();
        disableNode(ready_btn);
        updateInfoLabel();
        updateAllCharacterInfo();
        alreadySelectedCheck();
    }
    public void updateInfoLabel(){
        int num = gameState.getParty().size();
        if(num == gameState.getPlayerCount()){
            infoLabel.setText("Ready Up!");
            unDisableNode(ready_btn);
            disableAllNodes();
        }
        else {
            String whichPlayer = "Player 1";
            if(num == 1){
                whichPlayer = "Player 2";
            }
            else if (num == 2) {
                whichPlayer = "Player 3";
            }
            else if (num == 3) {
                whichPlayer = "Player 4";
            }
            infoLabel.setText(whichPlayer + ", choose your character.");
        }
    }
    public void disableAllNodes(){
        if(knight != null){
            disableNode(knight);
            knightStats.setText("");
            knightName.setText("");
        }
        if(cleric != null){
            disableNode(cleric);
            clericStats.setText("");
            clericName.setText("");
        }
        if(mage != null){
            disableNode(mage);
            mageStats.setText("");
            mageName.setText("");
        }
    }
    public void updateAllCharacterInfo(){
        updateSpecificStats(kingStats, gameState.getKing());
        updateSpecificName(kingName, gameState.getKing());

        updateSpecificStats(knightStats, gameState.getKnight());
        updateSpecificName(knightName, gameState.getKnight());

        updateSpecificStats(clericStats, gameState.getCleric());
        updateSpecificName(clericName, gameState.getCleric());

        updateSpecificStats(mageStats, gameState.getMage());
        updateSpecificName(mageName, gameState.getMage());
    }
    public void updateSpecificStats(Label label, Character character){
        if(character != null){
            int hp = character.getHp();
            int def = character.getDef();
            int basic_atk = character.getBasic_attack();
            int range = character.getRange();
            label.setText("\t" + "New Stats" +
                    "\nHP: " + hp +
                    "\nDef: " + def +
                    "\nBasic Attack: " + basic_atk +
                    "\nRange: " + range);
        }
    }
    public void updateSpecificName(Label label, Character character){
        if(character != null){
            String name = character.getName();
            label.setText(name);
        }
    }
    public void only1PlayerLeft(){
        ArrayList<Character> party = gameState.getParty();
        boolean kingInParty = party.indexOf(gameState.getKing()) == -1;
        boolean onePlayerLeft = (gameState.getPlayerCount() - party.size() == 1);
        if(onePlayerLeft && kingInParty){
            disableNode(knight);
            disableNode(cleric);
            disableNode(mage);
        }
    }
    public void alreadySelectedCheck(){
        for (Character character : gameState.getParty()) {
            String characterID = character.getID();
            switch (characterID){
                case "King":
                    disableNode(king);
                    break;
                case "Knight":
                    disableNode(knight);
                    break;
                case "Cleric":
                    disableNode(cleric);
                    break;
                case "Mage":
                    disableNode(mage);
                    break;
            }
        }
    }
    @FXML
    private void goToStatRoll(MouseEvent  event) throws IOException {
        updateInfoLabel();
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
    @FXML
    public void ready(ActionEvent event) throws IOException {
        switchScene(event, "Combat");
    }

}
