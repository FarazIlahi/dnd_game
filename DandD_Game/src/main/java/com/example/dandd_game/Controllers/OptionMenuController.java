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

    private int audio;
    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    private void initialize() {
        checkScene(gameState.getCurrentScene());

    }
    public void checkScene(String scene){
        switch (scene){
            case "Combat":
            case "GameLoads":
            case "TitleScreen":
            case "NewUserRegisterController":
            case "LoginController":
                disableNode(save_btn);
                disableNode(load_btn);
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
    private void loadGame() {
        try {
            GameSaves.loadGame();
            String scene = GameStateManager.getInstance().getCurrentScene();
            switchScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToMenu() {
        try {
            switchScene("GameLoads");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
