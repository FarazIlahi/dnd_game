package com.example.dandd_game.Chapter3;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FinalDefendController extends BaseController implements GameMechanics {
    @FXML
    public AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void holdMainGate(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hold Main Gate");

        if (roll >= 10) {
            alert.setHeaderText("Success!");
            alert.setContentText("You held off the enemy long enough for reinforcements to arrive!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            alert.setHeaderText("Partial Failure!");
            alert.setContentText("The enemy broke through. You must have one final fight!");
            alert.showAndWait();

            int finalRoll = rollDice(20);
            Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
            finalAlert.setTitle("Final Fight!");

            if (finalRoll >= 5) {
                finalAlert.setHeaderText("Success!");
                finalAlert.setContentText("You rallied your forces and repelled the invaders!");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Failure!");
                finalAlert.setContentText("You were overwhelmed. The kingdom has fallen.");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }

    @FXML
    private void setTrap(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Set Trap");

        if (roll >= 8) {
            alert.setHeaderText("Perfect Ambush!");
            alert.setContentText("The trap destroys the enemy forces. You are victorious!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            alert.setHeaderText("Trap Failed!");
            alert.setContentText("You were caught. You must fight them now!");
            alert.showAndWait();

            int finalRoll = rollDice(20);
            Alert finalAlert = new Alert(Alert.AlertType.INFORMATION);
            finalAlert.setTitle("Final Fight!");

            if (finalRoll >= 12) {
                finalAlert.setHeaderText("Success!");
                finalAlert.setContentText("You were victorious in the fight! The kingdom is saved!");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                finalAlert.setHeaderText("Failure!");
                finalAlert.setContentText("You were defeated. The kingdom has fallen.");
                finalAlert.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }
}
