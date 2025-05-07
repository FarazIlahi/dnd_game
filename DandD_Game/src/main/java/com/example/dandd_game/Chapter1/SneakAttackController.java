package com.example.dandd_game.Chapter1;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SneakAttackController extends BaseController implements GameMechanics {

    @FXML
    private void attackFromBehind(ActionEvent event) throws IOException {
        GameStateManager gsm = GameStateManager.getInstance();

        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());
        gsm.createOrc();
        gsm.addToEnemys(gsm.getOrc());
        gsm.setNextScene("Chapter2/ChapterTwoScene");
        switchScene(event, "Combat");

    }

}
