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
    private ArrayList<Button> p1_buttons = new ArrayList<Button>();
    private String p1ID;

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
    private ArrayList<Button> p2_buttons = new ArrayList<Button>();
    private String p2ID;

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
    private ArrayList<Button> p3_buttons = new ArrayList<Button>();
    private String p3ID;

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
    private ArrayList<Button> p4_buttons = new ArrayList<Button>();
    private String p4ID;

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
        setupGrid();
        loadCharacter();
        setParty();
        updateTurn();
    }

    @FXML
    private void doSmth(ActionEvent event){
        gameState.nextTurn();
        updateTurn();
    }
    public void setupGrid(){
        combatGrid.getColumnConstraints().clear();
        combatGrid.getRowConstraints().clear();
        ColumnConstraints column = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        column.setPercentWidth(100);
        row.setPercentHeight(100);
        for (int i = 0; i < 20; i++) {
            combatGrid.getColumnConstraints().add(column);
            combatGrid.getRowConstraints().add(row);
        }
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                Region cell = new Region();
                cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
                cell.setPrefSize(100, 100);
                combatGrid.add(cell, c, r);
            }
        }
    }
    public void setKeybinds(){
        keyManager.addKeyBinding("W", this::moveUp);
        keyManager.addKeyBinding("A", this::moveLeft);
        keyManager.addKeyBinding("S", this::moveDown);
        keyManager.addKeyBinding("D", this::moveRight);
    }

    public void loadCharacter(){
        for (Character character : gameState.getParty()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y);
        }
        for (Character character : gameState.getEnemies()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y);
        }
        shuffleTurnOrder();
    }
    public void updateProfiles(Character character, int x, int y){
        ImageView profile = new ImageView(localImages.getImage(character.getID()));
        profile.setFitWidth(40);
        profile.setFitHeight(40);
        combatGrid.add(profile, x, y);
    }
    public void clearProfiles(int x, int y){
        removeImageViewFromCell(y, x, combatGrid);
    }
    public void removeImageViewFromCell(int row, int column, GridPane gridPane) {
        List<Node> toRemove = new ArrayList<>();

        Node node = iterate(row,column,gridPane);
        if(node != null){
            toRemove.add(node);
        }
        gridPane.getChildren().removeAll(toRemove);
    }
    public Node  iterate(int row, int column, GridPane gridPane){
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            rowIndex = (rowIndex == null) ? 0 : rowIndex;
            colIndex = (colIndex == null) ? 0 : colIndex;

            if (rowIndex == row && colIndex == column && node instanceof ImageView) {
                return node;
            }
        }
        return null;
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
                p1_buttons.add(p1_attack);
                p1_buttons.add(p1_special);
                p1_buttons.add(p1_move);
                p1ID = character.getID();
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
                p2_buttons.add(p2_attack);
                p2_buttons.add(p2_special);
                p2_buttons.add(p2_move);
                p2ID = character.getID();
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
                p3_buttons.add(p3_attack);
                p3_buttons.add(p3_special);
                p3_buttons.add(p3_move);
                p3ID = character.getID();
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
                p4_buttons.add(p4_attack);
                p4_buttons.add(p4_special);
                p4_buttons.add(p4_move);
                p4ID = character.getID();
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
        updateTurnOrder();
        String id = gameState.getCurrentCharacter().getID();
        disableButtons(p1_buttons);
        disableButtons(p2_buttons);
        disableButtons(p3_buttons);
        disableButtons(p4_buttons);
        if(id.equals(p1ID)){
            enableButtons(p1_buttons);
        }
        else if (id.equals(p2ID)) {
            enableButtons(p2_buttons);
        }
        else if (id.equals(p3ID)) {
            enableButtons(p3_buttons);
        }
        else if (id.equals(p4ID)) {
            enableButtons(p4_buttons);
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
        if ((canMove(x, y - 1) && myTurn())){
            move("Up", x, y);
        }
    }
    public void moveDown(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if((canMove(x, y + 1) && myTurn())){
            move("Down", x, y);
        }
    }
    public void moveRight(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if ((canMove(x + 1, y) && myTurn())){
            move("Right", x, y);
        }
    }
    public void moveLeft(){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        if((canMove(x - 1, y) && myTurn())){
            move("Left", x, y);
        }
    }

    public void move(String direction, int x, int y){
        switch (direction){
            case "Up":
                clearProfiles(x, y);
                y--;
                gameState.getCurrentCharacter().getPosition().setY(y);
                updateProfiles(gameState.getCurrentCharacter(), x , y);
                gameState.decreaseMoveCount();
                break;
            case "Down":
                clearProfiles(x, y);
                y++;
                gameState.getCurrentCharacter().getPosition().setY(y);
                updateProfiles(gameState.getCurrentCharacter(), x, y);
                gameState.decreaseMoveCount();
                break;
            case "Left":
                clearProfiles(x, y);
                x--;
                gameState.getCurrentCharacter().getPosition().setX(x);
                updateProfiles(gameState.getCurrentCharacter(), x, y);
                gameState.decreaseMoveCount();
                break;
            case "Right":
                clearProfiles(x, y);
                x++;
                gameState.getCurrentCharacter().getPosition().setX(x);
                updateProfiles(gameState.getCurrentCharacter(), x, y);
                gameState.decreaseMoveCount();
                break;
        }
    }
    public boolean canMove(int x, int y){
        if((x >= 0) && (x < 20) && (y >= 0) && (y < 20)){
            Node node = iterate(y, x, combatGrid);
            if(node == null){
                return true;
            }
        }
        return false;
    }

    public boolean myTurn(){
        if(gameState.getMoveCount() > 0){
            return true;
        }
        return false;
    }
}
