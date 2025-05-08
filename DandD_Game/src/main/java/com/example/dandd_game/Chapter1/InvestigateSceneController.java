package com.example.dandd_game.Chapter1;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

import java.io.IOException;

public class InvestigateSceneController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;

    @FXML
    private Label investigateText;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void infiltrate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infiltration");
        alert.setHeaderText("You sneak around the army behind enemy lines");
        alert.setContentText("You gather intel and prepare your next move");
        alert.showAndWait();
        if (GameStateManager.getInstance().unlockAchievement("You infiltrated the enemy lines!")) {
            GameStateManager.getInstance().queueAchievementPopup("You infiltrated the enemy lines!");
        }
        switchScene(event, "Chapter1/InfiltrateScene");
    }

    @FXML
    private void attackSorcerer(ActionEvent event) throws IOException {
        GameStateManager gsm = GameStateManager.getInstance();
        if (GameStateManager.getInstance().unlockAchievement("You chose to attack The Sorcerer early!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to attack The Sorcerer early!");
        }
        gsm.resetEnemies();
        gsm.createOrc();
        gsm.createGoblin();
        gsm.createImp();
        gsm.createZombie();
        gsm.createSorcerer();
        gsm.addToEnemys(gsm.getOrc());
        gsm.addToEnemys(gsm.getGoblin());
        gsm.addToEnemys(gsm.getSorcerer());
        gsm.resetList(gsm.getTurnOrder());


        gsm.setNextScene("Chapter3/GameWinScene");
        switchScene(event, "Combat");
    }
}
