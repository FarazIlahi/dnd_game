package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameOverController extends BaseController implements GameMechanics{

    @FXML
    private Pane rootPane;
    @FXML
    private Button menuButton;

    @FXML
    private void initialize() {
        super.init(rootPane);
        super.stopMusic();
        super.setMusic("DandD_Game/src/main/resources/com/example/dandd_game/sounds/gameOver.wav");
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void goToMenu(ActionEvent event) throws IOException {
        if (GameStateManager.getInstance().unlockAchievement("You have Lost!")) {
            GameStateManager.getInstance().queueAchievementPopup("You have Lost!");
        }
        switchScene(event, "GameLoads"); // returns to game laod scene
    }
}
