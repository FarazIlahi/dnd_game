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

public class GameWinController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private Button menuButton;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void goToMenu(ActionEvent event) throws IOException {
        if (GameStateManager.getInstance().unlockAchievement("You have won!")) {
            GameStateManager.getInstance().queueAchievementPopup("You have won!");
        }
        switchScene(event, "GameLoads"); // returns to game load scene
    }
}
