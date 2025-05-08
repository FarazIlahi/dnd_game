package com.example.dandd_game.Chapter3;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Characters.Goblin;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class EnemyLeaderController extends BaseController implements GameMechanics {
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
    private void duelLeader(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Duel With Enemy Leader");

        GameStateManager gsm = GameStateManager.getInstance();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());

        if (roll >= 10) {
            alert.setHeaderText("Victory!");
            alert.setContentText("Your army handles the sorcerers men, its just him now!");
            if (GameStateManager.getInstance().unlockAchievement("You defeated the enemy leader in a duel!")) {
                GameStateManager.getInstance().queueAchievementPopup("You defeated the enemy leader in a duel!");
            }
            alert.showAndWait();

            gsm.createSorcerer();
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene(event, "Combat");
        } else {
            alert.setHeaderText("Defeat!");
            alert.setContentText("You were defeated by the enemy leader.");
            if (GameStateManager.getInstance().unlockAchievement("You were defeated by the enemy leader in a duel!")) {
                GameStateManager.getInstance().queueAchievementPopup("You were defeated by the enemy leader in a duel!");
            }
            alert.showAndWait();
            gsm.createOrc();
            gsm.createGoblin();
            gsm.createSorcerer();
            gsm.createSkeleton();

            gsm.getOrc().setName("Sorcerer's Orc");
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.getSkeleton().setName("Sorcerer's Skeleton");
            gsm.addToEnemys(gsm.getOrc());
            gsm.addToEnemys(gsm.getGoblin());
            gsm.addToEnemys(gsm.getSkeleton());
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene(event, "Combat");
        }
    }

    @FXML
    private void destroySupplies(ActionEvent event) throws IOException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        Alert sabotageAlert = new Alert(Alert.AlertType.INFORMATION);
        sabotageAlert.setTitle("Sabotage");
        sabotageAlert.setHeaderText("You destroy the enemy's supplies!");
        sabotageAlert.setContentText("The enemy is weakened and will be easier to defeat.");
        if (GameStateManager.getInstance().unlockAchievement("You sabotaged the enemy's supplies!")) {
            GameStateManager.getInstance().queueAchievementPopup("You sabotaged the enemy's supplies!");
        }
        sabotageAlert.showAndWait();

        int roll1 = rollDice(20);
        Alert battleAlert = new Alert(Alert.AlertType.INFORMATION);
        battleAlert.setTitle("Final Battle");
        GameStateManager gsm = GameStateManager.getInstance();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());

        if (roll1 >= 12) {
            battleAlert.setHeaderText("Success!");
            battleAlert.setContentText("Only the Sorcerer survived. It's time to end the war!");
            battleAlert.showAndWait();

            gsm.createSorcerer();
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene(event, "Combat");
        } else {
            battleAlert.setHeaderText("Failure!");
            battleAlert.setContentText("The enemy is too strong. You must fight them now!");
            battleAlert.showAndWait();

            gsm.createOrc();
            gsm.createGoblin();
            gsm.createSorcerer();
            gsm.getOrc().setName("Sorcerer's Orc");
            gsm.getGoblin().setName("Sorcerer's Goblin");
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getOrc());
            gsm.addToEnemys(gsm.getGoblin());
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene(event, "Combat");
        }
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
