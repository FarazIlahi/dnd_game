package com.example.dandd_game.Chapter1;
import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SecondSceneController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;
    @FXML
    private Button prepareSiegeButton;

    @FXML
    private Button investigateButton;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }



    @FXML
    private void goToSiege(ActionEvent event) throws IOException {
        if(GameStateManager.getInstance().unlockAchievement("You decided to prepare a siege!")) {
            GameStateManager.getInstance().queueAchievementPopup("You decided to prepare a siege!");
        }
        switchScene(event, "Chapter1/SiegeScene"); // defend siege
    }


    @FXML
    private void goToInvestigate(ActionEvent event) throws IOException {
        if (GameStateManager.getInstance().unlockAchievement("You decided to investigate!")) {
            GameStateManager.getInstance().queueAchievementPopup("You decided to investigate!");
        }
        switchScene(event, "Chapter1/InvestigateScene"); // investigate
    }
}
