package com.example.dandd_game;

import com.example.dandd_game.Characters.Character;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CombatMechanics extends GameMechanics{

    GameStateManager gameState = GameStateManager.getInstance();
    LocalImages localImages = LocalImages.getInstance();
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
        ImageView profile = new ImageView(localImages.getImage(character.getID()));
        profile.setFitWidth(40);
        profile.setFitHeight(40);
        combatGrid.add(profile, x, y);
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

    default void doTurn(Runnable method){
        gameState.nextTurn();
        method.run();

        if(gameState.getEnemies().contains(gameState.getTurnOrder().getFirst())){
            System.out.println("enemy turn here");
            doTurn(method);
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

}
