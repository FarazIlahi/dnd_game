package com.example.dandd_game;

import com.example.dandd_game.Characters.Character;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CombatMechanics extends GameMechanics{
    Node[][] cellNodes = new Node[20][20];
    GameStateManager gameState = GameStateManager.getInstance();
    LocalImages localImages = LocalImages.getInstance();

    void setAttacking(boolean bool);

    void setUsingSpecial(boolean bool);

    boolean getIsAttacking();

    boolean getIsUsingSpecial();

    default void setupGrid(GridPane combatGrid){
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
                cellNodes[c][r] = cell;
            }
        }
    }


    default Node iterate(int row, int column, GridPane gridPane){
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
    default void loadCharacter(GridPane combatGrid){
        for (Character character : gameState.getParty()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y, combatGrid);
        }
        for (Character character : gameState.getEnemies()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y, combatGrid);
        }
        shuffleTurnOrder();
    }

    default void updateProfiles(Character character, int x, int y, GridPane combatGrid){
        ImageView profile = character.getProfile();
        profile.setFitWidth(40);
        profile.setFitHeight(40);
        combatGrid.add(profile, x, y);
        profile.setUserData(character);
        profile.setOnMouseEntered(event -> {
            if(getIsAttacking() || getIsUsingSpecial()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)){
                    highlight(profile);
                }
            }
        });
        profile.setOnMouseExited(event -> {
            if(getIsAttacking() || getIsUsingSpecial()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)){
                    unhighlight(profile);
                }
            }
        });
        profile.setOnMouseClicked(event -> {
            if(getIsAttacking()){

            }
            else if (getIsUsingSpecial()) {

            }
        });
    }
    default boolean withinRange(Character character){
        int x1 = gameState.getCurrentCharacter().getPosition().getX();
        int y1 = gameState.getCurrentCharacter().getPosition().getY();
        int range = gameState.getCurrentCharacter().getRange();
        int x2 = character.getPosition().getX();
        int y2 = character.getPosition().getY();
        if (Math.abs(x1 - x2) + Math.abs(y1 - y2) <= range) {
            return true;
        }
        return false;
    }
    default void clearProfiles(int x, int y, GridPane combatGrid){
        removeImageViewFromCell(y, x, combatGrid);
    }
    default void removeImageViewFromCell(int row, int column, GridPane gridPane) {
        List<Node> toRemove = new ArrayList<>();

        Node node = iterate(row,column,gridPane);
        if(node != null){
            toRemove.add(node);
        }
        gridPane.getChildren().removeAll(toRemove);
    }
    default void shuffleTurnOrder(){
        ArrayList<Character> list = gameState.getTurnOrder();
        Collections.shuffle(list);
        if(gameState.getEnemies().contains(list.getFirst())){
            shuffleTurnOrder();
        }
    }

    default void doTurn(Runnable method, GridPane combatGrid){
        runPlayerAttack();

        gameState.nextTurn();
        method.run();

        if(gameState.getEnemies().contains(gameState.getTurnOrder().getFirst())){
            System.out.println("enemy turn here");
            //doTurn(method, combatGrid);
        }
    }

    default void runPlayerAttack(){
        updateShowRange(true);
        setAttacking(true);
    }

    default void updateShowRange(boolean bool){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        int range = gameState.getCurrentCharacter().getRange();
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                if (Math.abs(i - x) + Math.abs(j - y) <= range) {
                    if (i >= 0 && i < cellNodes.length && j >= 0 && j < cellNodes[0].length) {
                        toggleHighlightCell(cellNodes[i][j], bool);
                    }
                }
            }
        }
    }
    default void toggleHighlightCell(Node node, boolean highlight) {
        if (node != null) {
            if (highlight) {
                node.setStyle("-fx-background-color: lightblue; -fx-border-color: gray;");
            } else {
                node.setStyle("-fx-border-color: black; -fx-background-color: white;");
            }
        }
    }
    default double updateHp(Character character){
        return (double) character.getHp() / character.getMaxHp();
    }
    default void disableButtons(ArrayList<Button> buttons){
        for(Button button : buttons){
            disableNode(button);
        }
    }
    default void enableButtons(ArrayList<Button> buttons){
        for(Button button : buttons){
            enableNode(button);
        }
    }
    default void setSpecialBar(Character character, ProgressBar bar){
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
    default boolean canMoveCount(){
        if(gameState.getMoveCount() > 0){
            return true;
        }
        return false;
    }
}
