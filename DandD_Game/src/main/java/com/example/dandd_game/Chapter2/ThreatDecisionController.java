package com.example.dandd_game.Chapter2;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ThreatDecisionController extends BaseController implements GameMechanics {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initalize() {
        super.init(rootPane);
    }

    @FXML
    private void exploreRuin(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ancient Ruins");
        alert.setHeaderText("You explore the ruins...");
        alert.setContentText("You discover a powerful artifact, helping you become stronger.");
        GameStateManager.getInstance().unlockAchievement("You chose to explore the ruins!");
        alert.showAndWait();
        switchScene(event, "Chapter3/ChapterThreeScene");
    }

    @FXML
    private void seekForgottenKingdom(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgotten Kingdom");
        GameStateManager.getInstance().unlockAchievement("You seek the Forgotten Kingdom...High Risk, High Reward!");

        if (roll >= 14) {
            alert.setHeaderText("Success!");
            alert.setContentText("You rolled a " + roll+ ". The Forgotten Kingdom offers to help you.");
            GameStateManager.getInstance().unlockAchievement("You successfully seek the Forgotten Kingdom!");
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeScene"); // i think something should be addded here to boost party stats
        } else {
            alert.setHeaderText("Failure!");
            alert.setContentText("You rolled a " + roll+ ". The Forgotten Kingdom refuses to help you.");
            GameStateManager.getInstance().unlockAchievement("You failed to seek the Forgotten Kingdom!");
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeHardScene");
        }
    }
}
