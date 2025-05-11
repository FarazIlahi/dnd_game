package com.example.dandd_game.Chapter1;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SneakAttackController extends BaseController implements GameMechanics {

    @FXML
    private void attackFromBehind(ActionEvent event) throws IOException {
        GameStateManager gsm = GameStateManager.getInstance();
        GameStateManager.getInstance().resetAllCharacterPositions();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());
        gsm.createOrc();
        gsm.addToEnemys(gsm.getOrc());
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        gsm.setNextScene("Chapter2/ChapterTwoScene");
        switchScene("Combat");

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
