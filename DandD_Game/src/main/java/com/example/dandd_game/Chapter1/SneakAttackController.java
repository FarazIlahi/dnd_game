package com.example.dandd_game.Chapter1;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SneakAttackController extends BaseController implements GameMechanics {

    @FXML
    private void attackFromBehind(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        if (roll >= 5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sneak Attack");
            alert.setHeaderText("You attack from behind!");
            alert.setContentText("You rolled a " + roll + "\nSuccess! You attack the enemy!");
            alert.showAndWait();
            switchScene(event, "Chapter2/ChapterTwoScene");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sneak Attack");
            alert.setHeaderText("You attack from behind!");
            alert.setContentText("You rolled a " + roll + "\nFailed! You are caught!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameOverScene");
        }
    }

}
