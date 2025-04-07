package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class InfiltrateController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void proceedInfiltration(ActionEvent event) throws IOException {
        int roll= rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infiltration");
        alert.setHeaderText("You infiltrate the enemy lines and find a weak spot.");

        if (roll >=10) {
            alert.setContentText("You rolled a " + roll + "\nSuccess! You infiltrate the enemy lines and took advantage.");
            alert.showAndWait();
            switchScene(event, "ChapterTwoScene");
        } else {
            alert.setContentText("You rolled a " + roll + "\nFailed! You are caught.");
            alert.showAndWait();
            switchScene(event, "GameOverScene");
        }
    }
}
