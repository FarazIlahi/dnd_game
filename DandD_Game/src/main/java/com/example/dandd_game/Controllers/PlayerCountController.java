package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;


public class PlayerCountController extends BaseController{
    @FXML
    private ComboBox<Integer> numPlayers;
    @FXML
    private ComboBox<String> difficulty;
    @FXML
    private Pane rootPane;

    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    private void initialize(){
        super.init(rootPane);
        numPlayers.getItems().addAll(1, 2, 3, 4);
        numPlayers.setValue(1);
        difficulty.getItems().addAll("Easy", "Normal", "Hard");
        difficulty.setValue("Normal");
    }

    @FXML
    public void goToCharSelect(ActionEvent event) {
        gameState.setPlayerCount(numPlayers.getValue());
        gameState.setDifficulty(difficulty.getValue());
    }

}
