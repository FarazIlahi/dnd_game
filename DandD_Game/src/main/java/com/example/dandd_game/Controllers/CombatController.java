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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CombatController extends BaseController implements GameMechanics, CombatMechanics {

    @FXML
    private Pane root;
    @FXML
    private GridPane combatGrid;
    @FXML
    private TextArea turnOrderArea;
    private GameStateManager gameState = GameStateManager.getInstance();
    private LocalImages localImages = LocalImages.getInstance();

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            root.setFocusTraversable(true);
            root.requestFocus();
        });
        super.init(root);
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
        loadCharacter();
        updateTurnOrder();
        keyManager.addKeyBinding("W", this::moveUp);
        keyManager.addKeyBinding("A", this::moveLeft);
        keyManager.addKeyBinding("S", this::moveDown);
        keyManager.addKeyBinding("D", this::moveRight);
    }

    @FXML
    private void doSmth(ActionEvent event){
        gameState.nextTurn();
        updateTurnOrder();
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
        gameState.nextTurn();
        updateTurnOrder();
        return false;
    }

}
