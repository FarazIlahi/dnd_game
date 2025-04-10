package com.example.dandd_game.Chapter3;

import com.example.dandd_game.Controllers.BaseController;
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
        switchScene(event, "Chapter3/FinalDefendScene");
    }

    @FXML
    private void goToEnemyLeader(ActionEvent event) throws IOException {
        switchScene(event, "Chapter3/EnemyLeaderScene");
    }
}
