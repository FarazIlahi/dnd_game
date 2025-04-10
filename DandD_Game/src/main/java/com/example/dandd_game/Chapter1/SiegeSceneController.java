package com.example.dandd_game.Chapter1;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SiegeSceneController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label siegeText;

    @FXML
    public void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void holdWalls(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        showRollResult(roll, "Holding castle walls!");
        if (roll >= 10) {
            switchScene(event, "Chapter2/ChapterTwoScene");
        } else {
            switchScene(event, "Chapter3/GameOverScene");
        }
    }

    @FXML
    private void flankEnemy(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        if (roll >= 5) {
            switchScene(event, "Chapter1/SneakAttackScene");
        } else {
            switchScene(event, "Chapter3/GameOverScene");
        }
        showRollResult(roll, "Flanking enemy!");
    }

    private void showRollResult(int roll, String action) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Roll Result");
        alert.setHeaderText(action);
        alert.showAndWait();
    }
}
