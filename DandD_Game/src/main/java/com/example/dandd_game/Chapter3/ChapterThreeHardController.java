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
        gsm.createZombie();
        gsm.createImp();
        gsm.createSkeleton();

        gsm.getSorcerer().setName("The Sorcerer");
        gsm.getOrc().setName("Orc Leader");
        gsm.getGoblin().setName("Goblin Leader");
        gsm.addToEnemys(gsm.getSorcerer());
        gsm.addToEnemys(gsm.getOrc());
        gsm.addToEnemys(gsm.getGoblin());
        gsm.addToEnemys(gsm.getSkeleton());
        gsm.addToEnemys(gsm.getZombie());
        gsm.addToEnemys(gsm.getImp());

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
        gsm.createZombie();
        gsm.createImp();
        gsm.createGoblin();
        gsm.createSkeleton();
        gsm.getSorcerer().setName("The Sorcerer");
        gsm.getOrc().setName("Orc Leader");


        gsm.addToEnemys(gsm.getSorcerer());
        gsm.addToEnemys(gsm.getOrc());
        gsm.setNextScene("Chapter3/GameWinScene");
        switchScene(event, "Combat");
    }
}
