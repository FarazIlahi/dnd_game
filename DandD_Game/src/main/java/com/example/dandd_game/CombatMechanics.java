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
    void setDefenseCount(int num);
    int getDefenseCount();
    void setDefendedAlly(Character ally);
    Character getDefendedAlly();

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
            if(getIsAttacking()){
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)) {
                    if (!gameState.getParty().contains(owner)) {
                        highlight(profile);
                        owner.setHighlighted(true);
                    }
                }
            }
            else if (getIsUsingSpecial()) {
                ImageView iv = (ImageView) event.getSource();
                Character owner = (Character) iv.getUserData();
                if(withinRange(owner)) {
                    switch (gameState.getCurrentCharacter().getID()){
                        case "King":
                            if (!gameState.getParty().contains(owner)) {
                            highlight(profile);
                            owner.setHighlighted(true);
                        }
                            break;
                        case "Mage":
                            if (!gameState.getParty().contains(owner)) {
                                for(Character enemy : gameState.getEnemies()){
                                    if(withinRange(enemy)){
                                        highlight(enemy.getProfile());
                                    }
                                }
                                highlight(profile);
                                owner.setHighlighted(true);
                            }
                            break;
                        case "Knight":
                            if (gameState.getParty().contains(owner)) {
                                highlight(profile);
                                owner.setHighlighted(true);
                            }
                            break;
                        case "Cleric":
                            if (gameState.getParty().contains(owner)) {
                                for(Character ally : gameState.getParty()){
                                    if(withinRange(ally)){
                                        highlight(ally.getProfile());
                                    }
                                }
                                highlight(profile);
                                owner.setHighlighted(true);
                            }
                            break;
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
            ImageView iv = (ImageView) event.getSource();
            Character owner = (Character) iv.getUserData();
            if(owner.getHighlighted()){
                if(gameState.getCurrentCharacter().getID().equals("Cleric")){
                    for(Character ally : gameState.getParty()){
                        if(withinRange(ally)){
                            unhighlight(ally.getProfile());
                        }
                    }
                }
                else if (gameState.getCurrentCharacter().getID().equals("Mage")) {
                    for(Character enemy : gameState.getEnemies()){
                        if(withinRange(enemy)){
                            unhighlight(enemy.getProfile());
                        }
                    }
                }
                unhighlight(profile);
                owner.setHighlighted(false);
            }
            else if(getIsShowingRange()){
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
                    pauseMethod(0.5, updateTurn);
                    pauseMethod(1.0, () -> checkIsDead(owner, combatGrid));
                }
                else if (getIsUsingSpecial()) {
                    runPlayerSpecialBackEnd(owner, combatGrid);
                    pauseMethod(0.5, updateTurn);
                    pauseMethod(1.0, () -> checkIsDead(owner, combatGrid));
                    if(gameState.getCurrentCharacter().getID().equals("Mage")) {
                        for(Character enemy : gameState.getEnemies()){
                            if(withinRange(enemy)){
                                pauseMethod(1.0, () -> checkIsDead(enemy, combatGrid));
                            }
                        }
                    }
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
        return closest;
    }
    default void moveEnemy(Character target, int range, Runnable updateTurn, GridPane combatGrid) throws IOException {
        if(canMoveCount()){
            moveEnemyTowardTarget(gameState.getCurrentCharacter().getProfile(), target, range, updateTurn, combatGrid);
        }
    }
    default void moveEnemyTowardTarget(Node enemy, Character target, int stepsLeft, Runnable updateTurn, GridPane combatGrid) {
        if (gameState.getMoveCount() <= 0 || withinRange(target)) {
            if(withinRange(target)){
                runEnemyAttackBackEnd(target,updateTurn, .5, combatGrid);
            }
            else {
                pauseMethod(.5, updateTurn);
            }
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

            moveEnemyTowardTarget(enemy, target, stepsLeft - 1, updateTurn, combatGrid);
        });
        gameState.getCurrentCharacter().getPosition().setX(toX);
        gameState.getCurrentCharacter().getPosition().setY(toY);
        gameState.decreaseMoveCount();
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


    default void runEnemyAttack(GridPane combatGrid, Runnable updateTurn) throws IOException {
        if(gameState.getCurrentCharacter().getIsDead()){
            updateTurn.run();
            return;
        }
        Position pos = gameState.getCurrentCharacter().getPosition();
        Character target = findClosest(pos.getX(), pos.getY());
        if(withinRange(target)){
            runEnemyAttackBackEnd(target, updateTurn, 0.5, combatGrid);
        }
        else {
            moveEnemy(target, gameState.getCurrentCharacter().getRange(), updateTurn, combatGrid);
        }
    }
    default void runEnemyAttackBackEnd(Character target, Runnable updateTurn, Double time, GridPane combatGrid){
        target.setHp(target.getHp() - gameState.getCurrentCharacter().getBasic_attack());
        updateHp(target, target.getHpBar(), target.getHpInfo());
        showBasicEffect(target, combatGrid);
        pauseMethod(time, updateTurn);
        pauseMethod(1.0, () -> checkIsDead(target, combatGrid));
    }
    default void showPlayerRange(Boolean bool, GridPane combatGrid){
        updateShowRange(bool, combatGrid);
    }
    default void runPlayerAttackBackEnd(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        setAttacking(false);
        target.setHp(target.getHp() - gameState.getCurrentCharacter().getBasic_attack());
        updateHp(target, target.getHpBar(), target.getHpInfo());
        showBasicEffect(target, combatGrid);
    }
    default void runPlayerSpecialBackEnd(Character target, GridPane combatGrid){
        gameState.getCurrentCharacter().updateSpecial();
        switch (gameState.getCurrentCharacter().getID()){
            case "King":
                runKingSpecial(target, combatGrid);
                break;
            case "Knight":
                runKnightSpecial(target, combatGrid);
                break;
            case "Cleric":
                runClericSpecial(target, combatGrid);
                break;
            case "Mage":
                runMageSpecial(target, combatGrid);
                break;
        }

        updateSpecial();
    }
    default void runKingSpecial(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        setUsingSpecial(false);
        target.setHp(target.getHp() - gameState.getCurrentCharacter().specialMove());
        updateHp(target, target.getHpBar(), target.getHpInfo());
        showSpecialEffect(target, combatGrid);
    }
    default void runKnightSpecial(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        setUsingSpecial(false);
        target.setDef(target.getDef() + gameState.getCurrentCharacter().specialMove());
        setDefenseCount(gameState.getTurnOrder().size());
        setDefendedAlly(target);
        showSpecialEffect(target, combatGrid);
    }
    default void runClericSpecial(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        setUsingSpecial(false);
        for(Character ally : gameState.getParty()){
            if(withinRange(ally)){
                ally.setHp(ally.getHp() - gameState.getCurrentCharacter().specialMove());
                if(!ally.getHighlighted()){
                    unhighlight(ally.getProfile());
                }
                updateHp(ally, ally.getHpBar(), ally.getHpInfo());
                showSpecialEffect(ally, combatGrid);
            }
        }
    }
    default void runMageSpecial(Character target, GridPane combatGrid){
        updateShowRange(false, combatGrid);
        unhighlight(target.getProfile());
        target.setHighlighted(false);
        setUsingSpecial(false);
        for(Character enemy : gameState.getEnemies()){
            if(withinRange(enemy)){
                enemy.setHp(enemy.getHp() - gameState.getCurrentCharacter().specialMove());
                if(!enemy.getHighlighted()){
                    unhighlight(enemy.getProfile());
                }
                updateHp(enemy, enemy.getHpBar(), enemy.getHpInfo());
                showSpecialEffect(enemy, combatGrid);
            }
        }
    }
    default void showBasicEffect(Character target, GridPane combatGrid){
        ImageView targetImage = target.getProfile();
        ImageView effect = new ImageView();
        switch (gameState.getCurrentCharacter().getID()){
            case "Goblin":
            case "Orc":
            case "King":
            case "Knight":
                effect.setImage(localImages.getImage("Slash"));
                break;
            case "Cleric":
            case "Mage":
            case "Sorcerer":
                effect.setImage(localImages.getImage("Explosion"));
        }
        updateEffect(effect, target, combatGrid);
    }
    default void showSpecialEffect(Character target, GridPane combatGrid){
        ImageView targetImage = target.getProfile();
        ImageView effect = new ImageView();
        switch (gameState.getCurrentCharacter().getID()){
            case "King":
                effect.setImage(localImages.getImage("XAttack"));
                break;
            case "Knight":
                effect.setImage(localImages.getImage("Shield"));
                break;
            case "Cleric":
                effect.setImage(localImages.getImage("Heal"));
                break;
            case "Mage":
                effect.setImage(localImages.getImage("ModAttack"));
                break;
        }
        updateEffect(effect, target, combatGrid);
    }
    default void updateEffect(ImageView effect, Character target, GridPane combatGrid){
        ImageView targetImage = target.getProfile();
        effect.setFitWidth(targetImage.getFitWidth());
        effect.setFitHeight(targetImage.getFitHeight());
        StackPane enemyWrapper = new StackPane();
        enemyWrapper.getChildren().addAll(targetImage, effect);
        combatGrid.add(enemyWrapper, target.getPosition().getX(), target.getPosition().getY());
        pauseMethod(.9, () -> combatGrid.getChildren().remove(enemyWrapper));
        pauseMethod(.9, () -> combatGrid.getChildren().add(targetImage));
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
            setDefenseCount(getDefenseCount() - 1);
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
    default void updateSpecial(){
        Character current = gameState.getCurrentCharacter();
        current.getSpecialBar().setProgress((double) current.getSpecial() / current.getMax_special());
        current.getSpecialInfo().setText(current.specialToSrting());
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
    default void setUniqueStyles(Character character){
        switch (character.getID()){
            case "King":
                character.getSpecialBar().getStyleClass().add("king-progress-bar");
                character.getButtons().get(0).getStyleClass().add("attack-button");
                character.getButtons().get(1).getStyleClass().add("attack-button");
                break;
            case "Knight":
                character.getSpecialBar().getStyleClass().add("knight-progress-bar");
                character.getButtons().get(0).getStyleClass().add("attack-button");
                character.getButtons().get(1).getStyleClass().add("shield-button");
                break;
            case "Cleric":
                character.getSpecialBar().getStyleClass().add("cleric-progress-bar");
                character.getButtons().get(0).getStyleClass().add("magic-attack-button");
                character.getButtons().get(1).getStyleClass().add("heal-button");
                break;
            case "Mage":
                character.getSpecialBar().getStyleClass().add("mage-progress-bar");
                character.getButtons().get(0).getStyleClass().add("magic-attack-button");
                character.getButtons().get(1).getStyleClass().add("magic-attack-button");
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
    default boolean canUseSpecial(){
        Character character = gameState.getCurrentCharacter();
        return character.getSpecial() >= character.getSpecialCost();
    }
    default void resetDefendedAlly(){
        if(getDefenseCount() == 0){
            getDefendedAlly().setDef(getDefendedAlly().getDef() - gameState.getKnight().specialMove());
        }
    }
}
