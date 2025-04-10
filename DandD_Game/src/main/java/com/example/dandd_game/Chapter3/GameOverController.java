package com.example.dandd_game.Chapter3;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameOverController extends BaseController implements GameMechanics{

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void goToMenu(ActionEvent event) throws IOException {
        switchScene(event, "GameLoads"); // returns to game laod scene
    }
}
