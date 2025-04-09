package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BattleStrategyController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initalize() {
        super.init(rootPane);
    }

    @FXML
    private void reinforceWalls(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Castle Wall Reinforcement");
        if (roll >= 7) {
            alert.setHeaderText("Sucessfully reinforced the castle walls!");
            alert.setContentText("You rolled a " + roll + "\nSuccess! You reinforce the castle walls!");
            alert.showAndWait();
            switchScene(event, "ChapterThreeScene");
        } else {
            alert.setHeaderText("Failed to reinforce the castle walls!");
            alert.setContentText("You rolled a " + roll + "\nFailed! You are caught!");
            alert.showAndWait();
            switchScene(event, "ChapterThreeHardScene");
        }
    }

    @FXML
    private void trainSoldiers(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Training Party");

        if (roll >= 15) {
            alert.setHeaderText("Successfully trained the party!");
            alert.setContentText("Your forces are well-trained and ready for battle.");
            alert.showAndWait();
            switchScene(event, "ChapterThreeScene"); // add a boost to player stats here
        } else {
            alert.setHeaderText("Failed to train the party!");
            alert.setContentText("Training failed. Morale is low. The kingdom will struggle.");
            alert.showAndWait();
            switchScene(event, "ChapterThreeHardScene");
        }
    }
}
