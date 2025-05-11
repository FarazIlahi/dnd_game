package com.example.dandd_game.Controllers;

import com.example.dandd_game.*;
import com.example.dandd_game.Characters.Character;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class TutorialController extends BaseController implements CombatMechanics {

    @FXML private Pane root;
    @FXML private Label tutorialText;
    @FXML private Button attackButton;
    @FXML private Button specialMoveButton;
    @FXML private Button nextButton;
    @FXML private GridPane combatGrid;

    private int step = 0;
    private Character player;

    @FXML
    public void initialize() {
        super.init(root);
        tutorialText.setText("Welcome to the tutorial.");
        disableAllControls();
        nextButton.setVisible(true);
        setupGrid(combatGrid);
        GameStateManager.getInstance().resetInstance();
        GameStateManager.getInstance().createKing();
        player = GameStateManager.getInstance().getKing();
        player.setPosition(new Position(0, 0));
        player.setProfile(new ImageView(LocalImages.getInstance().getImage("King")));
        GameStateManager.getInstance().addToParty(player);
        GameStateManager.getInstance().setCurrentCharacter(player);
        updateProfiles(player, 0, 0, combatGrid, () -> {});
        Platform.runLater(() -> root.requestFocus());
        root.setOnKeyPressed(this::handleMovement);
    }
    private void disableAllControls() {
        attackButton.setDisable(true);
        specialMoveButton.setDisable(true);
    }
    @FXML
    private void onNextPressed() {
        step++;
        disableAllControls();

        switch (step) {
            case 1:
                tutorialText.setText("This is your character. Try a basic attack.");
                attackButton.setDisable(false);
                break;
            case 2:
                tutorialText.setText("Now try your special ability.");
                specialMoveButton.setDisable(false);
                break;
            case 3:
                tutorialText.setText("Try to move using W A S D.");
                break;
            case 4:
                tutorialText.setText("Tutorial complete. You're ready!");
                nextButton.setText("Next");
                break;
            case 5:
                tutorialText.setText("Now try a real battle!");
                nextButton.setText("Continue");
                break;
            case 6:
                try {
                    launchMockCombat();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleMovement(KeyEvent event) {
        if (step < 3) return;
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        switch (event.getCode()) {
            case W -> y--;
            case S -> y++;
            case A -> x--;
            case D -> x++;
            default -> { return; }
        }
        if (canMoveOnGrid(x, y, combatGrid)) {
            removeImageViewFromCell(player.getPosition().getY(), player.getPosition().getX(), combatGrid);
            combatGrid.getChildren().remove(player.getProfile());
            player.getPosition().setX(x);
            player.getPosition().setY(y);
            combatGrid.add(player.getProfile(), x, y);
        }
    }
private void launchMockCombat() throws IOException {
    GameStateManager gameState = GameStateManager.getInstance();
    gameState.resetInstance();
    gameState.setPlayerCount(4);
    gameState.createKing();
    gameState.createKnight();
    gameState.createCleric();
    gameState.createMage();
    gameState.addToParty(gameState.getKing());
    gameState.addToParty(gameState.getKnight());
    gameState.addToParty(gameState.getCleric());
    gameState.addToParty(gameState.getMage());
    gameState.setDifficulty("Easy");
    gameState.createSorcerer();
    gameState.addToEnemys(gameState.getSorcerer());
    gameState.setNextScene("GameLoads");
    switchScene("Combat");
}


@Override
    public void setAttacking(boolean bool) {

    }
    @Override
    public boolean getIsAttacking() {
        return false;
    }
    @Override
    public void setUsingSpecial(boolean bool) {

    }
    @Override
    public boolean getIsUsingSpecial() {
        return false;
    }
    @Override
    public boolean getIsShowingRange() {
        return false;
    }

    @Override
    public void setDefenseCount(int num) {

    }

    @Override
    public int getDefenseCount() {
        return 0;
    }

    @Override
    public void setDefendedAlly(Character ally) {

    }

    @Override
    public Character getDefendedAlly() {
        return null;
    }
}
