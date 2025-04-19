package com.example.dandd_game.Controllers;

import com.example.dandd_game.*;
import com.example.dandd_game.Characters.Character;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class CombatController extends BaseController implements GameMechanics, CombatMechanics {

    @FXML
    private Pane root;
    @FXML
    private GridPane combatGrid;
    @FXML
    private TextArea turnOrderArea;
    @FXML
    private Button end_btn;
    @FXML
    private Button show_btn;

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
    private boolean animationMoving = false;
    private boolean attacking = false;
    private boolean usingSpecial = false;
    private boolean showingRange = false;

    private GameStateManager gameState = GameStateManager.getInstance();
    private LocalImages localImages = LocalImages.getInstance();

    @Override
    public void setAttacking(boolean bool){
        this.attacking = bool;
    }
    @Override
    public void setUsingSpecial(boolean bool){
        this.usingSpecial = bool;
    }
    @Override
    public boolean getIsAttacking(){
        return this.attacking;
    }
    @Override
    public boolean getIsUsingSpecial(){
        return this.usingSpecial;
    }
    @Override
    public boolean getIsShowingRange(){
        return this.showingRange;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            root.setFocusTraversable(true);
            root.requestFocus();
        });
        super.init(root);
        setKeybinds();
        setParty();
        setEnemies();
        setupGrid(combatGrid);
        Platform.runLater(() -> {
            loadCharacter(combatGrid, () -> {
                try {
                    updateTurn();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                updateTurn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    private void move(){
        moving = !moving;
        updateMoveButton();
        ArrayList<Button> list = gameState.getCurrentCharacter().getButtons();
        updateButtons(moving,list.get(0), list.get(1), show_btn, end_btn);
    }
    @FXML
    private void attack(){
        moving = false;
        setAttacking(!attacking);
        ArrayList<Button> list = gameState.getCurrentCharacter().getButtons();
        updateButtons(attacking,list.get(1), list.get(2), show_btn, end_btn);
        showPlayerAttack(attacking, combatGrid);
        updateMoveButton();
    }
    @FXML
    private void special(){
        moving = false;
        updateMoveButton();
    }
    @FXML
    private void passTurn() throws IOException {
        updateTurn();
    }
    @FXML
    private void showRange(){
        showingRange = !showingRange;
        gameState.setCurrentCharacter(gameState.getTurnOrder().getFirst());
        ArrayList<Button> list = gameState.getCurrentCharacter().getButtons();
        updateButtons(showingRange,list.get(0), list.get(1), list.get(2), end_btn);
        gameState.setCurrentCharacter(gameState.getTurnOrder().getFirst());
    }
    public void updateButtons(Boolean check,Button other1, Button other2, Button other3, Button other4){
        if(enemeyAttackCheck()){
            return;
        }
        if(check){
            disableNode(other1);
            disableNode(other2);
            disableNode(other3);
            disableNode(other4);
        }
        else{
            enableNode(other1);
            enableNode(other2);
            enableNode(other3);
            enableNode(other4);
            if(!canMoveCount()){
                disableNode(gameState.getCurrentCharacter().getButtons().get(2));
            }
        }
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
                updateHp(character, p1_hpBar, p1_hpInfo);
                p1_specialInfo.setText(character.specialToSrting());
                setSpecialBar(character, p1_specialBar);
                character.setProfile(new ImageView(p1_profile.getImage()));
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
                updateHp(character, p2_hpBar, p2_hpInfo);
                p2_specialInfo.setText(character.specialToSrting());
                setSpecialBar(character, p2_specialBar);
                character.setProfile(new ImageView(p2_profile.getImage()));
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
                updateHp(character, p3_hpBar, p3_hpInfo);
                p3_specialInfo.setText(character.specialToSrting());
                setSpecialBar(character, p3_specialBar);
                character.setProfile(new ImageView(p3_profile.getImage()));
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
                updateHp(character, p4_hpBar, p4_hpInfo);
                p4_specialInfo.setText(character.specialToSrting());
                setSpecialBar(character, p4_specialBar);
                character.setProfile(new ImageView(p4_profile.getImage()));
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
                updateHp(character, e1_hpBar, e1_hpInfo);
                character.setProfile(new ImageView(e1_profile.getImage()));
                character.setNameLabel(e1_name);
                character.setHpBar(e1_hpBar);
                character.setHpInfo(e1_hpInfo);
                break;
        }
    }

    public void updateTurn() throws IOException {
        moving = false;
        attacking = false;
        usingSpecial = false;
        showingRange = false;
        gameState.nextTurn();
        gameState.resetMoveCount();
        updateMoveButton();
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
        if(enemeyAttackCheck()){
            disableNode(show_btn);
            disableNode(end_btn);
            runEnemyAttack(() -> {
                try {
                    updateTurn();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            enableNode(show_btn);
            enableNode(end_btn);
        }

    }
    public void updateTurnOrder(){
        turnOrderArea.setText("Turn Order\n");
        for (Character character : gameState.getTurnOrder()) {
            turnOrderArea.setText(turnOrderArea.getText() + character.getName() + "\n");
        }
        gameState.setCurrentCharacter(gameState.getTurnOrder().getFirst());
    }
    public void moveUp(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if (canMoveOnGrid(x, y - 1, combatGrid) && canMoveCount()){
            if(moving || enemeyAttackCheck()){
                move("Up", x, y);
            }
        }
    }
    public void moveDown(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if(canMoveOnGrid(x, y + 1, combatGrid) && canMoveCount()){
            if(moving || enemeyAttackCheck()){
                move("Down", x, y);
            }
        }
    }
    public void moveRight(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if (canMoveOnGrid(x + 1, y, combatGrid) && canMoveCount()){
            if(moving || enemeyAttackCheck()){
                move("Right", x, y);
            }
        }
    }
    public void moveLeft(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if(canMoveOnGrid(x - 1, y, combatGrid) && canMoveCount()){
            if(moving || enemeyAttackCheck()){
                move("Left", x, y);
            }
        }
    }

    public void move(String direction, int x, int y){
        switch (direction){
            case "Up":
                moveTo(gameState.getCurrentCharacter().getProfile(), x, y, x, y- 1);
                break;
            case "Down":
                moveTo(gameState.getCurrentCharacter().getProfile(), x, y, x, y + 1);
                break;
            case "Left":
                moveTo(gameState.getCurrentCharacter().getProfile(), x, y, x - 1, y);
                break;
            case "Right":
                moveTo(gameState.getCurrentCharacter().getProfile(), x, y, x + 1, y);
                break;
        }
        updateMoveButton();
    }
    public void moveTo(Node node, int fromX, int fromY, int toX, int toY) {
        if(animationMoving){
            return;
        }
        animationMoving = true;
        int dx = toX - fromX;
        int dy = toY - fromY;

        double cellWidth = node.getBoundsInParent().getWidth();
        double cellHeight = node.getBoundsInParent().getHeight();

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), node);

        transition.setByX(dx * cellWidth);
        transition.setByY(dy * cellHeight);

        transition.setOnFinished(e -> {
            GridPane.setColumnIndex(node, toX);
            GridPane.setRowIndex(node, toY);
            node.setTranslateX(0);
            node.setTranslateY(0);
            animationMoving = false;
        });
        gameState.getCurrentCharacter().getPosition().setX(toX);
        gameState.getCurrentCharacter().getPosition().setY(toY);
        gameState.decreaseMoveCount();

        transition.play();
    }


    public void updateMoveButton(){
        if((enemeyAttackCheck())){
            return;
        }
        if (moving){
            gameState.getCurrentCharacter().getButtons().getLast().setText("Moves: " + gameState.getMoveCount());
        }
        else{
            gameState.getCurrentCharacter().getButtons().getLast().setText("Move");
        }
    }
}
