package com.example.dandd_game.Chapter1;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FirstSceneController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private Button continueButton;
    @FXML
    private void initialize() {
        super.init(rootPane);
    }
    @FXML
    private void goToGame(ActionEvent event) throws IOException {
        switchScene(event, "Chapter1/SecondScene");
    }
}
