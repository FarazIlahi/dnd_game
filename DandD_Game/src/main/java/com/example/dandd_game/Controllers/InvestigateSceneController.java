package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class InvestigateSceneController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label investigateText;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void infiltrate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infiltration");
        alert.setHeaderText("You sneak around the army behind enemy lines");
        alert.setContentText("You gather intel and prepare your next move");
        alert.showAndWait();
        switchScene(event, "InfiltrateScene");

    }

    @FXML
    private void attackSorcerer(ActionEvent event) throws IOException {
        int roll=rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attack Sorcerer");
        alert.setHeaderText("You attack the sorcerer!");
        if (roll >= 17) {
            alert.setContentText("You rolled a " + roll + "\nSuccess! You defeat the sorcerer and take control.");
            // next scene: game win
            alert.showAndWait();
            switchScene(event, "GameWinScene");
        } else {
            alert.setContentText("You rolled a " + roll + "\nFailed! The sorcerer takes control of your mind...");
            // next scene: game over
            alert.showAndWait();
            switchScene(event, "GameOverScene");
        }
    }
}
