package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class PlayerCountController extends BaseController implements GameMechanics {
    @FXML
    private ComboBox<Integer> numPlayers;
    @FXML
    private ComboBox<String> difficulty;
    @FXML
    private Pane rootPane;
    @FXML
    private Button go_btn;

    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    private void initialize(){
        super.init(rootPane);
        numPlayers.getItems().addAll(1, 2, 3, 4);
        difficulty.getItems().addAll("Easy", "Normal", "Hard");
        addListener();
        disableNode(go_btn);
    }
    public void addListener(){
        numPlayers.valueProperty().addListener((observable, oldValue, newValue) -> {
            canMoveOn();
        });
        difficulty.valueProperty().addListener((observable, oldValue, newValue) -> {
            canMoveOn();
        });
    }
    public void canMoveOn(){
        if((numPlayers.getValue() != null) && (difficulty.getValue() != null)){
            unDisableNode(go_btn);
        }
    }

    @FXML
    public void goToCharSelect(ActionEvent event) throws IOException {
        gameState.setPlayerCount(numPlayers.getValue());
        gameState.setDifficulty(difficulty.getValue());
        switchScene(event, "CharacterSelect");
    }

}
