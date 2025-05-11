package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
        super.stopMusic();
        super.setMusic("/com/example/dandd_game/sounds/gameOver.wav");
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void goToMenu(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        if (GameStateManager.getInstance().unlockAchievement("You have Lost!")) {
            GameStateManager.getInstance().queueAchievementPopup("You have Lost!");
        }
        switchScene("GameLoads"); // returns to game laod scene
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
