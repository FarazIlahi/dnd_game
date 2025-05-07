package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Characters.Goblin;
import com.example.dandd_game.Characters.Orc;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChapterThreeHardController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void holdMainGate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hold the Main Gate!");
        alert.setHeaderText("The enemy forces storm the gate!");
        alert.setContentText("You must hold the main gate to stop the enemy.");
        alert.showAndWait();

        GameStateManager gsm = GameStateManager.getInstance();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());
        gsm.createSorcerer();
        gsm.createOrc();
        gsm.createGoblin();
        gsm.createOrc();
        gsm.createGoblin();

        gsm.getSorcerer().setName("The Sorcerer");
        gsm.getOrc().setName("Orc Leader");
        Orc orc2 = new Orc();
        orc2.setName("Orc Brute");
        Goblin gob1 = new Goblin();
        gob1.setName("Goblin Leader");
        Goblin gob2 = new Goblin();
        gob2.setName("Goblin Brute");
        gsm.addToEnemys(gsm.getSorcerer());
        gsm.addToEnemys(gsm.getOrc());
        gsm.addToEnemys(orc2);
        gsm.addToEnemys(gob1);
        gsm.addToEnemys(gob2);
        gsm.setNextScene("Chapter3/GameWinScene");
        switchScene(event, "Combat");
    }

    @FXML
    private void setTrap(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ambush failed!");
        alert.setHeaderText("Your trap failed!");
        alert.setContentText("You were caught. You must fight them now!");
        alert.showAndWait();

        GameStateManager gsm = GameStateManager.getInstance();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());

        gsm.createSorcerer();
        gsm.createOrc();
        gsm.getSorcerer().setName("The Sorcerer");
        gsm.getOrc().setName("Orc Leader");

        Goblin gob1 = new Goblin();
        gob1.setName("Goblin Leader");
        Goblin gob2 = new Goblin();
        gob2.setName("Goblin Brute");
        Goblin gob3 = new Goblin();
        gob3.setName("Goblin Attacker");
        Goblin gob4 = new Goblin();
        gob4.setName("Goblin Defender");

        gsm.addToEnemys(gsm.getSorcerer());
        gsm.addToEnemys(gsm.getOrc());
        gsm.addToEnemys(gob1);
        gsm.addToEnemys(gob2);
        gsm.addToEnemys(gob3);
        gsm.addToEnemys(gob4);
        gsm.setNextScene("Chapter3/GameWinScene");
        switchScene(event, "Combat");
    }
}
