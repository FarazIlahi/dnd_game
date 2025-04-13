package com.example.dandd_game.Chapter3;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChapterThreeHardController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void holdMainGate(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hold the Main Gate!");

        if (roll >= 15) {
            alert.setHeaderText("Victory!");
            alert.setContentText("You rolled " + roll + ". Your troops held the gate long enough!");
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
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Overrun...");
                finalAlert.setContentText("You rolled " + finalRoll + ". The Kingdom has been defeated.");
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
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Too Late...");
                finalAlert.setContentText("You rolled " + finalRoll + ". Your party was overwhelmed.");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }
}
