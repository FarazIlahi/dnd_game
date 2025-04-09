package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChapterThreeController extends BaseController implements GameMechanics {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void goToFinalDefend(ActionEvent event) throws IOException {
        switchScene(event, "FinalDefendScene");
    }

    @FXML
    private void goToEnemyLeader(ActionEvent event) throws IOException {
        switchScene(event, "EnemyLeaderScene");
    }
}
