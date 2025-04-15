package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.CombatMechanics;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.LocalImages;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class CombatController extends BaseController implements GameMechanics, CombatMechanics {

    @FXML
    private Pane root;
    @FXML
    private GridPane combatGrid;
    @FXML
    private TextArea turnOrderArea;

    @FXML
    private ImageView p1_profile;
    @FXML
    private Label p1_name;
    @FXML
    private Label p1_hpInfo;
    @FXML
    private Label p1_specialInfo;
    @FXML
    private ProgressBar p1_hpBar;
    @FXML
    private ProgressBar p1_specialBar;
    @FXML
    private Button p1_attack;
    @FXML
    private Button p1_special;
    @FXML
    private Button p1_move;

    @FXML
    private ImageView p2_profile;
    @FXML
    private Label p2_name;
    @FXML
    private Label p2_hpInfo;
    @FXML
    private Label p2_specialInfo;
    @FXML
    private ProgressBar p2_hpBar;
    @FXML
    private ProgressBar p2_specialBar;
    @FXML
    private Button p2_attack;
    @FXML
    private Button p2_special;
    @FXML
    private Button p2_move;

    @FXML
    private ImageView p3_profile;
    @FXML
    private Label p3_name;
    @FXML
    private Label p3_hpInfo;
    @FXML
    private Label p3_specialInfo;
    @FXML
    private ProgressBar p3_hpBar;
    @FXML
    private ProgressBar p3_specialBar;
    @FXML
    private Button p3_attack;
    @FXML
    private Button p3_special;
    @FXML
    private Button p3_move;

    @FXML
    private ImageView p4_profile;
    @FXML
    private Label p4_name;
    @FXML
    private Label p4_hpInfo;
    @FXML
    private Label p4_specialInfo;
    @FXML
    private ProgressBar p4_hpBar;
    @FXML
    private ProgressBar p4_specialBar;
    @FXML
    private Button p4_attack;
    @FXML
    private Button p4_special;
    @FXML
    private Button p4_move;

    @FXML
    private ImageView e1_profile;
    @FXML
    private Label e1_name;
    @FXML
    private Label e1_hpInfo;
    @FXML
    private ProgressBar e1_hpBar;

    private boolean moving = false;
    private boolean attacking;
    private boolean specialMove;

    private GameStateManager gameState = GameStateManager.getInstance();
    private LocalImages localImages = LocalImages.getInstance();

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            root.setFocusTraversable(true);
            root.requestFocus();
        });
        super.init(root);
        setKeybinds();
        setupGrid(combatGrid);
        loadCharacter(combatGrid);
        setParty();
        setEnemies();
        updateTurn();
    }
    @FXML
    private void move(){
        moving = !moving;
        updateMoveButton();
        if(moving){
            disableNode(gameState.getCurrentCharacter().getButtons().get(0));
            disableNode(gameState.getCurrentCharacter().getButtons().get(1));
        }
        else{
            if(!canMoveCount()){
                disableNode(gameState.getCurrentCharacter().getButtons().getLast());
            }
            enableNode(gameState.getCurrentCharacter().getButtons().get(0));
            enableNode(gameState.getCurrentCharacter().getButtons().get(1));
        }
    }

    @FXML
    private void attack(){
        moving = false;
        updateMoveButton();
        doTurn(this::updateTurn);
    }
    @FXML
    private void special(){
        moving = false;
        updateMoveButton();
        doTurn(this::updateTurn);
    }

    public void setKeybinds(){
        keyManager.addKeyBinding("W", this::moveUp);
        keyManager.addKeyBinding("A", this::moveLeft);
        keyManager.addKeyBinding("S", this::moveDown);
        keyManager.addKeyBinding("D", this::moveRight);
    }

    public void setParty(){
        int count = 1;
        for(Character character : gameState.getParty()){
            setPlayer(character, count);
            count++;
        }
    }
    public void setPlayer(Character character, int pNum){
        String playerNum = "" + pNum;
        switch (playerNum){
            case "1":
                p1_profile.setImage(localImages.getImage(character.getID()));
                p1_name.setText(character.getName());
                p1_hpBar.setVisible(true);
                p1_specialBar.setVisible(true);
                p1_attack.setVisible(true);
                p1_special.setVisible(true);
                p1_move.setVisible(true);
                p1_hpBar.setProgress(updateHp(character));
                p1_hpInfo.setText(character.hpToString());
                p1_specialInfo.setText(character.specialToSrting());
                setProgressBar(character, p1_specialBar);
                character.setNameLabel(p1_name);
                character.setHpBar(p1_hpBar);
                character.setSpecialBar(p1_specialBar);
                character.setHpInfo(p1_hpInfo);
                character.setSpecialInfo(p1_specialInfo);
                character.addButton(p1_attack);
                character.addButton(p1_special);
                character.addButton(p1_move);
                break;
            case "2":
                p2_profile.setImage(localImages.getImage(character.getID()));
                p2_name.setText(character.getName());
                p2_hpBar.setVisible(true);
                p2_specialBar.setVisible(true);
                p2_attack.setVisible(true);
                p2_special.setVisible(true);
                p2_move.setVisible(true);
                p2_hpBar.setProgress(updateHp(character));
                p2_hpInfo.setText(character.hpToString());
                p2_specialInfo.setText(character.specialToSrting());
                setProgressBar(character, p2_specialBar);
                character.setNameLabel(p2_name);
                character.setHpBar(p2_hpBar);
                character.setSpecialBar(p2_specialBar);
                character.setHpInfo(p2_hpInfo);
                character.setSpecialInfo(p2_specialInfo);
                character.addButton(p2_attack);
                character.addButton(p2_special);
                character.addButton(p2_move);
                break;
            case "3":
                p3_profile.setImage(localImages.getImage(character.getID()));
                p3_name.setText(character.getName());
                p3_hpBar.setVisible(true);
                p3_specialBar.setVisible(true);
                p3_attack.setVisible(true);
                p3_special.setVisible(true);
                p3_move.setVisible(true);
                p3_hpBar.setProgress(updateHp(character));
                p3_hpInfo.setText(character.hpToString());
                p3_specialInfo.setText(character.specialToSrting());
                setProgressBar(character, p3_specialBar);
                character.setNameLabel(p3_name);
                character.setHpBar(p3_hpBar);
                character.setSpecialBar(p3_specialBar);
                character.setHpInfo(p3_hpInfo);
                character.setSpecialInfo(p3_specialInfo);
                character.addButton(p3_attack);
                character.addButton(p3_special);
                character.addButton(p3_move);
                break;
            case "4":
                p4_profile.setImage(localImages.getImage(character.getID()));
                p4_name.setText(character.getName());
                p4_hpBar.setVisible(true);
                p4_specialBar.setVisible(true);
                p4_attack.setVisible(true);
                p4_special.setVisible(true);
                p4_move.setVisible(true);
                p4_hpBar.setProgress(updateHp(character));
                p4_hpInfo.setText(character.hpToString());
                p4_specialInfo.setText(character.specialToSrting());
                setProgressBar(character, p4_specialBar);
                character.setNameLabel(p4_name);
                character.setHpBar(p4_hpBar);
                character.setSpecialBar(p4_specialBar);
                character.setHpInfo(p4_hpInfo);
                character.setSpecialInfo(p4_specialInfo);
                character.addButton(p4_attack);
                character.addButton(p4_special);
                character.addButton(p4_move);
                break;
        }
    }
    public void setEnemies(){
        int count = 1;
        for(Character character : gameState.getEnemies()){
            setEnemy(character, count);
            count++;
        }
    }
    public void setEnemy(Character character, int eNum){
        String enemyNum = "" + eNum;
        switch (enemyNum) {
            case "1":
                e1_profile.setImage(localImages.getImage(character.getID()));
                e1_name.setText(character.getName());
                e1_hpBar.setVisible(true);
                e1_hpBar.setProgress(updateHp(character));
                e1_hpInfo.setText(character.hpToString());

                character.setNameLabel(e1_name);
                character.setHpBar(e1_hpBar);
                character.setHpInfo(e1_hpInfo);
                break;
        }
    }
    public void setProgressBar(Character character, ProgressBar bar){
        switch (character.getID()){
            case "King":
                bar.getStyleClass().add("king-progress-bar");
                break;
            case "Knight":
                bar.getStyleClass().add("knight-progress-bar");
                break;
            case "Cleric":
                bar.getStyleClass().add("cleric-progress-bar");
                break;
            case "Mage":
                bar.getStyleClass().add("mage-progress-bar");
                break;
        }
    }
    public void updateTurn(){
        moving = false;
        updateTurnOrder();
        String targetID = gameState.getCurrentCharacter().getID();
        for(Character character : gameState.getParty()){
            disableButtons(character.getButtons());
        }
        for(Character character : gameState.getParty()){
            if(targetID.equals(character.getID())){
                enableButtons(character.getButtons());
            }
        }

    }
    public void updateTurnOrder(){
        turnOrderArea.clear();
        for (Character character : gameState.getTurnOrder()) {
            turnOrderArea.setText(turnOrderArea.getText() + character.getName() + "\n");
        }
        gameState.setCurrentCharacter(gameState.getTurnOrder().getFirst());
    }
    public void moveUp(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if (canMoveOnGrid(x, y - 1) && canMoveCount() && moving){
            move("Up", x, y);
        }
    }
    public void moveDown(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if(canMoveOnGrid(x, y + 1) && canMoveCount() && moving){
            move("Down", x, y);
        }
    }
    public void moveRight(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if (canMoveOnGrid(x + 1, y) && canMoveCount() && moving){
            move("Right", x, y);
        }
    }
    public void moveLeft(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if(canMoveOnGrid(x - 1, y) && canMoveCount() && moving){
            move("Left", x, y);
        }
    }

    public void move(String direction, int x, int y){
        switch (direction){
            case "Up":
                clearProfiles(x, y, combatGrid);
                y--;
                gameState.getCurrentCharacter().getPosition().setY(y);
                updateProfiles(gameState.getCurrentCharacter(), x , y, combatGrid);
                gameState.decreaseMoveCount();
                break;
            case "Down":
                clearProfiles(x, y, combatGrid);
                y++;
                gameState.getCurrentCharacter().getPosition().setY(y);
                updateProfiles(gameState.getCurrentCharacter(), x, y, combatGrid);
                gameState.decreaseMoveCount();
                break;
            case "Left":
                clearProfiles(x, y, combatGrid);
                x--;
                gameState.getCurrentCharacter().getPosition().setX(x);
                updateProfiles(gameState.getCurrentCharacter(), x, y, combatGrid);
                gameState.decreaseMoveCount();
                break;
            case "Right":
                clearProfiles(x, y, combatGrid);
                x++;
                gameState.getCurrentCharacter().getPosition().setX(x);
                updateProfiles(gameState.getCurrentCharacter(), x, y, combatGrid);
                gameState.decreaseMoveCount();
                break;
        }
        updateMoveButton();
    }
    public boolean canMoveOnGrid(int x, int y){
        if((x >= 0) && (x < 20) && (y >= 0) && (y < 20)){
            Node node = iterate(y, x, combatGrid);
            if(node == null){
                return true;
            }
        }
        return false;
    }
    public void updateMoveButton(){
        if (moving){
            gameState.getCurrentCharacter().getButtons().getLast().setText("Moves: " + gameState.getMoveCount());
        }
        else{
            gameState.getCurrentCharacter().getButtons().getLast().setText("Move");
        }
    }
    public boolean canMoveCount(){
        if(gameState.getMoveCount() > 0){
            return true;
        }
        return false;
    }
}
