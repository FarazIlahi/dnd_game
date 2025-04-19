package com.example.dandd_game;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.Controllers.BaseController;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
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
    boolean getIsShowingRange();

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
    default void loadCharacter(GridPane combatGrid, Runnable updateTurn){
        for (Character character : gameState.getParty()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y, combatGrid, updateTurn);
        }
        for (Character character : gameState.getEnemies()) {
            gameState.addToTurn(character);
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            updateProfiles(character, x, y, combatGrid, updateTurn);
        }
        shuffleTurnOrder();
    }

    default void updateProfiles(Character character, int x, int y, GridPane combatGrid, Runnable updateTurn){
        ImageView profile = character.getProfile();
        profile.setFitWidth(40);
        profile.setFitHeight(40);
        combatGrid.add(profile, x, y);
        profile.setUserData(character);
        profile.setOnMouseEntered(event -> {
            if(getIsAttacking() || getIsUsingSpecial()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)) {
                    if (!gameState.getParty().contains(owner)) {
                        highlight(profile);
                        owner.setHighlighted(true);
                    }
                }
            }
            else if(getIsShowingRange()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                gameState.setCurrentCharacter(owner);
                updateShowRange(true, combatGrid);
            }
        });
        profile.setOnMouseExited(event -> {
            if(getIsAttacking() || getIsUsingSpecial()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)){
                    if(!gameState.getParty().contains(owner)){
                        unhighlight(profile);
                        owner.setHighlighted(false);
                    }
                }
            }
            else if(getIsShowingRange()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                gameState.setCurrentCharacter(owner);
                updateShowRange(false, combatGrid);
            }
        });
        profile.setOnMouseClicked(event -> {
            ImageView iv = (ImageView) event.getSource();
            Character owner = (Character) iv.getUserData();
            if(owner.getHighlighted()){
                if(getIsAttacking()){
                    runPlayerAttackBackEnd(owner, combatGrid);
                    checkIsDead(owner, combatGrid);
                    updateTurn.run();
                }
                else if (getIsUsingSpecial()) {
                    updateShowRange(false, combatGrid);
                    runPlayerSpecialBackEnd();
                }
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
    default void removeImageViewFromCell(int row, int column, GridPane gridPane) {
        List<Node> toRemove = new ArrayList<>();

        Node node = iterate(column,row,gridPane);
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
    default boolean enemeyAttackCheck(){
        if(gameState.getEnemies().contains(gameState.getCurrentCharacter())){
            return true;
        }
        return false;
    }
    default Character findClosest(int enemyX, int enemyY) throws IOException {
        Character closest = gameState.getParty().getFirst();
        int tempx = closest.getPosition().getX();
        int tempy = closest.getPosition().getY();
        int oldDistance = Math.abs(tempx - enemyX) + Math.abs(tempy - enemyY);
        for(Character character : gameState.getParty()){
            int x = character.getPosition().getX();
            int y = character.getPosition().getY();
            int newDistance = Math.abs(x - enemyX) + Math.abs(y - enemyY);
            if(newDistance < oldDistance){
                closest = character;
                oldDistance = newDistance;
            }
        }
        if(closest != null){
        }
        if(!withinRange(closest)){
            moveEnemy(closest, gameState.getCurrentCharacter().getRange());
            if(!withinRange(closest)){
                closest = null;
            }
        }

        return closest;
    }
    default void moveEnemy(Character target, int range) throws IOException {
        if(canMoveCount()){
            moveEnemyTowardTarget(gameState.getCurrentCharacter().getProfile(), target, range);
        }
    }
    default void moveEnemyTowardTarget(Node enemy, Character target, int stepsLeft) {
        if (gameState.getMoveCount() <= 0 || withinRange(target)) {
            return;
        }
        int enemyX = gameState.getCurrentCharacter().getPosition().getX();
        int enemyY = gameState.getCurrentCharacter().getPosition().getY();
        int targetX = target.getPosition().getX();
        int targetY = target.getPosition().getY();
        Position position = calculateNewPos(enemyX, enemyY, targetX, targetY);
        int toX = position.getX();
        int toY = position.getY();
        int newX = toX - enemyX;
        int newY = toY - enemyY;
        double cellWidth = enemy.getBoundsInParent().getWidth();
        double cellHeight = enemy.getBoundsInParent().getHeight();
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), enemy);
        transition.setByX(newX * cellWidth);
        transition.setByY(newY * cellHeight);
        transition.setOnFinished(e -> {
            GridPane.setColumnIndex(enemy, toX);
            GridPane.setRowIndex(enemy, toY);
            enemy.setTranslateX(0);
            enemy.setTranslateY(0);
            gameState.getCurrentCharacter().getPosition().setX(toX);
            gameState.getCurrentCharacter().getPosition().setY(toY);
            gameState.decreaseMoveCount();
            moveEnemyTowardTarget(enemy, target, stepsLeft - 1);
        });
        transition.play();
    }

    default Position calculateNewPos(int enemyX, int enemyY, int targetX, int targetY){
        int distX = enemyX - targetX;
        int distY = enemyY - targetY;
        int toX = enemyX;
        int toY = enemyY;
        if(Math.abs(distX) >= Math.abs(distY)){
            if(distX > 0){
                toX--;
            }
            else {
                toX++;
            }
        }
        else {
            if(distY > 0){
                toY--;
            }
            else {
                toY++;
            }
        }
        return new Position(toX, toY);
    }


    default void runEnemyAttackBackEnd(Runnable updateTurn) throws IOException {
        Position pos = gameState.getCurrentCharacter().getPosition();
        Character target = findClosest(pos.getX(), pos.getY());
        if(target != null){
            target.setHp(target.getHp() - gameState.getCurrentCharacter().getBasic_attack());
            updateHp(target, target.getHpBar(), target.getHpInfo());
        }

        pauseMethod(2.6, updateTurn);

    }
    default void showPlayerAttack(Boolean bool, GridPane combatGrid){
        updateShowRange(bool, combatGrid);
        setAttacking(bool);
    }
    default void runPlayerAttackBackEnd(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        target.setHp(target.getHp() - gameState.getCurrentCharacter().getBasic_attack());
        updateHp(target, target.getHpBar(), target.getHpInfo());
    }

    default void runPlayerSpecial(){

    }
    default void runPlayerSpecialBackEnd(){

    }
    default void checkIsDead(Character character, GridPane combatGrid){
        if(character.getIsDead()){
            if(gameState.getParty().contains(character)){
                gameState.removeFromParty(character);
            }
            else {
                gameState.removeFromEnemys(character);
            }
            Position position = character.getPosition();
            removeImageViewFromCell(position.getX(), position.getY(), combatGrid);
            gameState.removeFromTurnOrder(character);
        }
    }

    default void updateShowRange(boolean bool, GridPane combatGrid){
        int x = gameState.getCurrentCharacter().getPosition().getX();
        int y = gameState.getCurrentCharacter().getPosition().getY();
        int range = gameState.getCurrentCharacter().getRange();
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                if (Math.abs(i - x) + Math.abs(j - y) <= range) {
                    if (i >= 0 && i < cellNodes.length && j >= 0 && j < cellNodes[0].length) {
                        toggleHighlightCell(cellNodes[i][j], bool);
                        Node image = iterate(i, j, combatGrid);
                        if(image != null){
                            Character character = (Character)image.getUserData();
                            if(!(gameState.getCurrentCharacter().equals(character))){
                                if(bool){
                                    image.getStyleClass().add("glow-image");
                                }
                                else {
                                    image.getStyleClass().remove("glow-image");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    default void toggleHighlightCell(Node node, boolean highlight) {
        if (node != null) {
            if (highlight) {
                node.setStyle("-fx-background-color: black; -fx-border-color: gray;");
            } else {
                node.setStyle("-fx-border-color: black; -fx-background-color: white;");
            }
        }
    }
    default void updateHp(Character character, ProgressBar bar, Label label){
        bar.setProgress((double) character.getHp() / character.getMaxHp());
        label.setText(character.hpToString());
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
    default boolean canMoveOnGrid(int x, int y, GridPane combatGrid){
        if((x >= 0) && (x < 20) && (y >= 0) && (y < 20)){
            Node node = iterate(y, x, combatGrid);
            if(node == null){
                return true;
            }
        }
        return false;
    }

}
