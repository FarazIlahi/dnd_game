package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.LocalImages;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class CombatController extends BaseController implements GameMechanics {

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
        keyManager.addKeyBinding("A", this::moveRight);
        keyManager.addKeyBinding("S", this::moveDown);
        keyManager.addKeyBinding("D", this::moveRight);
    }

    public void loadCharacter(){
        for (Character character : gameState.getParty()) {
            gameState.addToTurn(character);
            displayProfiles(character);
        }
        for (Character character : gameState.getEnemies()) {
            gameState.addToTurn(character);
            displayProfiles(character);
        }
        shuffleTurnOrder(gameState.getTurnOrder());
    }
    public void displayProfiles(Character character){
        int x = character.getPosition().getX();
        int y = character.getPosition().getY();
        ImageView profile = new ImageView(localImages.getImage(character.getID()));
        profile.setFitWidth(40);
        profile.setFitHeight(40);
        combatGrid.add(profile, x, y);
    }
    public void updateTurnOrder(){
        for (Character character : gameState.getTurnOrder()) {
            turnOrderArea.setText(turnOrderArea.getText() + character.getName() + "\n");
        }
        gameState.setCurrentCharacter(gameState.getTurnOrder().get(0));
    }
    public void moveUp(){
        System.out.println("up");
    }
    public void moveDown(){
        System.out.println("down");
    }
    public void moveRight(){
        System.out.println("right");
    }
    public void moveLeft(){
        System.out.println("left");
    }

}
