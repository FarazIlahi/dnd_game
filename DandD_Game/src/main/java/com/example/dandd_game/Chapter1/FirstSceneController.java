package com.example.dandd_game.Chapter1;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.LocalImages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.InputStream;

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
        if (GameStateManager.getInstance().unlockAchievement("You have started the game!")) {;
            GameStateManager.getInstance().queueAchievementPopup("You have started the game!");
        }playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        switchScene(event, "Chapter1/SecondScene");
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
