package com.example.dandd_game.Chapter2;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class UncoverThreatController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void continueToThreatChoice(ActionEvent event) throws IOException {
        switchScene(event, "Chapter2/ThreatDecisionScene");
    }
}
