package com.example.dandd_game.Chapter2;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ThreatDecisionController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;

    @FXML
    private void initalize() {
        super.init(rootPane);
    }

    @FXML
    private void exploreRuin(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ancient Ruins");
        alert.setHeaderText("You explore the ruins...");
        alert.setContentText("You discover a powerful artifact, helping you become stronger.");
        if (GameStateManager.getInstance().unlockAchievement("You chose to explore the ruins!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to explore the ruins!");
        }
        alert.showAndWait();
        switchScene(event, "Chapter3/ChapterThreeScene");
    }

    @FXML
    private void seekForgottenKingdom(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgotten Kingdom");
        if (GameStateManager.getInstance().unlockAchievement("You seek the Forgotten Kingdom...High Risk, High Reward!")) {
            GameStateManager.getInstance().queueAchievementPopup("You seek the Forgotten Kingdom...High Risk, High Reward!");
        }

        if (roll >= 14) {
            alert.setHeaderText("Success!");
            alert.setContentText("You rolled a " + roll+ ". The Forgotten Kingdom offers to help you.");
            GameStateManager gsm = GameStateManager.getInstance();
            if (GameStateManager.getInstance().unlockAchievement("You successfully seek the Forgotten Kingdom!")) {
                GameStateManager.getInstance().queueAchievementPopup("You successfully seek the Forgotten Kingdom!");
            }
            if (gsm.getKing() != null) {
                gsm.getKing().setBasic_attack(gsm.getKing().getBasic_attack() + 5); // attack boost for succeeding risky option
                System.out.println("attack boost, new val = " + gsm.getKing().getBasic_attack());
            }
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeScene");
        } else {
            alert.setHeaderText("Failure!");
            alert.setContentText("You rolled a " + roll+ ". The Forgotten Kingdom refuses to help you.");
            if (GameStateManager.getInstance().unlockAchievement("You failed to seek the Forgotten Kingdom!")) {
                GameStateManager.getInstance().queueAchievementPopup("You failed to seek the Forgotten Kingdom!");
            }
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeHardScene");
        }
    }
    @FXML
    public void hovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        highlight(clickedButton);
    }
    @FXML
    public void unHovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        unhighlight(clickedButton);
    }
}
