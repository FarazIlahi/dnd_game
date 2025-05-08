package com.example.dandd_game.Chapter2;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChapterTwoController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    public void initialize() {
        super.init(rootPane);
        super.stopMusic();
        super.setMusic("DandD_Game/src/main/resources/com/example/dandd_game/sounds/chapter2.wav");
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void goToPrepareBattle(ActionEvent event) throws IOException {
        switchScene(event, "Chapter2/PrepareBattleScene");
    }

    @FXML
    private void goToUncoverThreat(ActionEvent event) throws IOException {
        switchScene(event, "Chapter2/UncoverThreatScene");
    }
}
