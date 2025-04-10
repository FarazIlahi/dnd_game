package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameOverController extends BaseController implements GameMechanics{

    @FXML
    private Pane rootPane;
    @FXML
    private Button menuButton;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void goToMenu(ActionEvent event) throws IOException {
        switchScene(event, "GameLoads"); // returns to game laod scene
    }
}
