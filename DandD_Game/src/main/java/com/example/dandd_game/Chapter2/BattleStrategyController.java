package com.example.dandd_game.Chapter2;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BattleStrategyController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private void initalize() {
        super.init(rootPane);
    }

    @FXML
    private void reinforceWalls(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Castle Wall Reinforcement");
        if (GameStateManager.getInstance().unlockAchievement("You chose to reinforce the castle walls!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to reinforce the castle walls!");
        }
        if (roll >= 7) {
            alert.setHeaderText("Successfully reinforced the castle walls!");
            alert.setContentText("You rolled a " + roll + "\nSuccess! You reinforce the castle walls!");
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeScene");
        } else {
            alert.setHeaderText("Failed to reinforce the castle walls!");
            alert.setContentText("You rolled a " + roll + "\nFailed! You are caught!");
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeHardScene");
        }
    }

    @FXML
    private void trainSoldiers(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Training Party");
        GameStateManager gsm = GameStateManager.getInstance();
        if (GameStateManager.getInstance().unlockAchievement("You chose to train the party and prepare them for the final battle!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to train the party and prepare them for the final battle!");
        }
        if (roll >= 15) {
            alert.setHeaderText("Successfully trained the party!");
            alert.setContentText("Your forces are well-trained and ready for battle.");
            if (gsm.getKing() != null) {
                gsm.getKing().setBasic_attack(gsm.getKing().getBasic_attack() + 5); // attack boost for succeeding risky option
                System.out.println("attack boost, new val = " + gsm.getKing().getBasic_attack());
            }
            if (GameStateManager.getInstance().unlockAchievement("You trained the party!")) {
                GameStateManager.getInstance().queueAchievementPopup("You trained the party!");
            }
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeScene"); // add a boost to player stats here
        } else {
            alert.setHeaderText("Failed to train the party!");
            alert.setContentText("Training failed. Morale is low. The kingdom will struggle.");
            if (GameStateManager.getInstance().unlockAchievement("You failed to train the party!")) {
                GameStateManager.getInstance().queueAchievementPopup("You failed to train the party!");
            }
            alert.showAndWait();
            switchScene(event, "Chapter3/ChapterThreeHardScene");
        }
    }
}
