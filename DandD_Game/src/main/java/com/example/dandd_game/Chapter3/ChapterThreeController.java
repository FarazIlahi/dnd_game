package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChapterThreeController extends BaseController implements GameMechanics {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void goToFinalDefend(ActionEvent event) throws IOException {
        if (GameStateManager.getInstance().unlockAchievement("You have reached the final defend scene!")) {
            GameStateManager.getInstance().queueAchievementPopup("You have reached the final defend scene!");
        }
        switchScene(event, "Chapter3/FinalDefendScene");
    }

    @FXML
    private void goToEnemyLeader(ActionEvent event) throws IOException {
        if (GameStateManager.getInstance().unlockAchievement("You have reached the final enemy leader scene!")) {
            GameStateManager.getInstance().queueAchievementPopup("You have reached the final enemy leader scene!");
        }
        switchScene(event, "Chapter3/EnemyLeaderScene");
    }
}
