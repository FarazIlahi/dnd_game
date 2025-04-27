package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChapterThreeHardController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void holdMainGate(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hold the Main Gate!");

        if (roll >= 15) {
            alert.setHeaderText("Victory!");
            alert.setContentText("You rolled " + roll + ". Your troops held the gate long enough!");
            GameStateManager.getInstance().unlockAchievement("You successfully held the main gate!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            alert.setHeaderText("Gate Breached!");
            alert.setContentText("You rolled " + roll + ". A last stand in the throne room is your only hope.");
            alert.showAndWait();


            int finalRoll = rollDice(20);
            Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
            finalAlert.setTitle("Final Fight!");

            if (finalRoll >= 17) {
                finalAlert.setHeaderText("Against all odds, You Win!");
                finalAlert.setContentText("You rolled " + finalRoll + ". You managed to defend the throne!");
                finalAlert.showAndWait();
                GameStateManager.getInstance().unlockAchievement("You won the last stand! Victory!");
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Overrun...");
                finalAlert.setContentText("You rolled " + finalRoll + ". The Kingdom has been defeated.");
                GameStateManager.getInstance().unlockAchievement("You failed twice...");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }

    @FXML
    private void setTrap(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Set a Trap!");

        if (roll >= 17) {
            alert.setHeaderText("Great Ambush!");
            alert.setContentText("You rolled " + roll + ". The enemy is crushed by your trap.");
            GameStateManager.getInstance().unlockAchievement("You successfully set a trap!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            alert.setHeaderText("Trap Failed!");
            alert.setContentText("You rolled " + roll + ". The ambush was exposed. You must fight now.");
            alert.showAndWait();

            int finalRoll = rollDice(20);
            Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
            finalAlert.setTitle("Final Push");
            if (finalRoll >=18) {
                finalAlert.setHeaderText("Success!");
                finalAlert.setContentText("You rolled " + finalRoll + ". You managed to win with one final push!");
                GameStateManager.getInstance().unlockAchievement("You won the final push! Victory!");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Too Late...");
                finalAlert.setContentText("You rolled " + finalRoll + ". Your party was overwhelmed.");
                GameStateManager.getInstance().unlockAchievement("You failed twice...");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }
}
