package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FirstSceneController extends  BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private Button continueButton;
    private GameStateManager gameState = GameStateManager.getInstance();
    @FXML
    private void initialize() {
        super.init(rootPane);
    }
    @FXML
    private void goToGame(ActionEvent event) throws IOException {
        switchScene(event, "SecondScene"); // GameScene would be the eventual gameplay screen added
    }
}
