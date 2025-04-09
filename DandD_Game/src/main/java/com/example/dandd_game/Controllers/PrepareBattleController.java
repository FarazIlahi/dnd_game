package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class PrepareBattleController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void goToStrategy(ActionEvent event) throws IOException {
        switchScene(event, "BattleStrategyScene");
    }
}
