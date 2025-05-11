package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameSaves;
import com.example.dandd_game.GameStateManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.nio.Buffer;

import javafx.scene.input.KeyCode;

public class OptionMenuController extends BaseController implements GameMechanics {
    @FXML
    Slider audioSlider;
    @FXML
    Label audioNum;
    @FXML
    Pane rootPane;
    @FXML
    Button save_btn;
    @FXML
    Button load_btn;
    @FXML
    Button menu_btn;


    @FXML private Button upButton;
    @FXML private Button downButton;
    @FXML private Button leftButton;
    @FXML private Button rightButton;
    @FXML private Button resetKeys;

    @FXML private Label upKey;
    @FXML private Label downKey;
    @FXML private Label leftKey;
    @FXML private Label rightKey;

    @FXML private void onUpKeyChange() { waitingForKey = KeyBindTarget.UP; rootPane.requestFocus(); }
    @FXML private void onDownKeyChange() { waitingForKey = KeyBindTarget.DOWN; rootPane.requestFocus(); }
    @FXML private void onLeftKeyChange() { waitingForKey = KeyBindTarget.LEFT; rootPane.requestFocus(); }
    @FXML private void onRightKeyChange() { waitingForKey = KeyBindTarget.RIGHT; rootPane.requestFocus(); }

    private enum KeyBindTarget { UP, DOWN, LEFT, RIGHT }
    private KeyBindTarget waitingForKey = null;

    private int audio;
    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    private void initialize() {
        checkScene(gameState.getCurrentScene());
        updateKeyLabels();

        rootPane.setOnKeyPressed(event -> {
            if (waitingForKey != null) {
                KeyCode code = event.getCode();

                // Manually exclude non-bindable keys
                if (code == KeyCode.ESCAPE || code == KeyCode.SHIFT || code == KeyCode.CONTROL ||
                        code == KeyCode.ALT || code == KeyCode.TAB || code == KeyCode.CAPS ||
                        code == KeyCode.UNDEFINED) {
                    return;
                }

                String keyText = event.getText().toUpperCase();
                if (keyText.isEmpty()) {
                    keyText = code.toString(); // fallback for non-character keys
                }

                // Check for conflicts
                boolean isConflict = switch (waitingForKey) {
                    case UP -> keyText.equals(gameState.getDownKey()) ||
                            keyText.equals(gameState.getLeftKey()) ||
                            keyText.equals(gameState.getRightKey());
                    case DOWN -> keyText.equals(gameState.getUpKey()) ||
                            keyText.equals(gameState.getLeftKey()) ||
                            keyText.equals(gameState.getRightKey());
                    case LEFT -> keyText.equals(gameState.getUpKey()) ||
                            keyText.equals(gameState.getDownKey()) ||
                            keyText.equals(gameState.getRightKey());
                    case RIGHT -> keyText.equals(gameState.getUpKey()) ||
                            keyText.equals(gameState.getDownKey()) ||
                            keyText.equals(gameState.getLeftKey());
                };

                if (isConflict) {
                    waitingForKey = null;
                    return;
                }

                // Set the key
                switch (waitingForKey) {
                    case UP -> gameState.setUpKey(keyText);
                    case DOWN -> gameState.setDownKey(keyText);
                    case LEFT -> gameState.setLeftKey(keyText);
                    case RIGHT -> gameState.setRightKey(keyText);
                }

                updateKeyLabels();
                waitingForKey = null;
            }
        });
    }

    public void checkScene(String scene){
        switch (scene){
            case "Combat":
            case "GameLoads":
            case "TitleScreen":
            case "newUserRegister":
            case "login":
                disableNode(save_btn);
                disableNode(menu_btn);
                break;
        }
    }



    @FXML private void saveGame() {
        try {
            int slot = GameSaves.getSelectedSlot();
            GameSaves.saveGame(slot);
            GameSaves.saveAchievements();
            System.out.println("Game saved to slot " + slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToMenu() {
        try {
            setIs_on_settings(false);
            switchScene("GameLoads");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateKeyLabels() {
        upKey.setText(gameState.getUpKey());
        downKey.setText(gameState.getDownKey());
        leftKey.setText(gameState.getLeftKey());
        rightKey.setText(gameState.getRightKey());
    }
    @FXML
    private void resetToDefaultKeys() {
        gameState.setUpKey("W");
        gameState.setDownKey("S");
        gameState.setLeftKey("A");
        gameState.setRightKey("D");
        updateKeyLabels();
    }
}
