package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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
            playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
            canMoveOn();
        });
        difficulty.valueProperty().addListener((observable, oldValue, newValue) -> {
            playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
            canMoveOn();
        });
    }
    public void canMoveOn(){
        if((numPlayers.getValue() != null) && (difficulty.getValue() != null)){
            FadeTransition ft = new FadeTransition(Duration.millis(300), go_btn);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            enableNode(go_btn);
        }
    }

    @FXML
    public void goToCharSelect(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        gameState.setPlayerCount(numPlayers.getValue());
        gameState.setDifficulty(difficulty.getValue());
        switchScene("CharacterSelect");
    }
    @FXML
    public void hovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        highlight(clickedButton);
    }
    @FXML
    public void unHovered(MouseEvent event){
        Button clickedButton = (Button) event.getSource();
        unhighlight(clickedButton);
    }
}
