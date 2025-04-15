package com.example.dandd_game.Chapter3;

import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EnemyLeaderController extends BaseController implements GameMechanics {
    @FXML
    private AnchorPane rootPane;

    // creating a player to test a taking damage method
    private Character player; // this is just for testing it can be deleted

    @FXML
    private void initialize() {
        super.init(rootPane);
        // example character also for testing, can be deleted
        player = new Character(21, 5 ,10,2, "Knight", new Position(1,0)) {
            @Override
            public void specialToSrting() {

            }

            @Override
            public void specialMove() {
                System.out.println("Big hit");
            }
        }; // end of character data
    }

    @FXML
    private void duelLeader(ActionEvent event) throws IOException {
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Duel With Enemy Leader");

        if (roll >= 10) {
            alert.setHeaderText("Victory!");
            alert.setContentText("You defeated the enemy leader!");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            alert.setHeaderText("Defeat!");
            alert.setContentText("You were defeated by the enemy leader.");
            alert.showAndWait();
            switchScene(event, "Chapter3/GameOverScene");
        }
    }

    @FXML
    private void destroySupplies(ActionEvent event) throws IOException {
        Alert sabotageAlert = new Alert(Alert.AlertType.INFORMATION);
        sabotageAlert.setTitle("Sabotage");
        sabotageAlert.setHeaderText("You destroy the enemy's supplies!");
        sabotageAlert.setContentText("The enemy is weakened and will be easier to defeat.");
        sabotageAlert.showAndWait();

        int roll1 = rollDice(20);
        int roll2 = rollDice(20);

        Alert battleAlert = new Alert(Alert.AlertType.INFORMATION);
        battleAlert.setTitle("Final Battle");

        if (roll1 >= 10 && roll2 >= 5) {
            battleAlert.setHeaderText("Victory!");
            battleAlert.setContentText("You defeated the enemy leader!");
            battleAlert.showAndWait();
            switchScene(event, "Chapter3/GameWinScene");
        } else {
            player.setHp(player.getHp() - 10);
            battleAlert.setHeaderText("Defeat!");
            battleAlert.setContentText("You rolled " + roll1 + " and " + roll2 + ". You took 10 damage.\nCurrent HP: " + player.getHp());
            battleAlert.showAndWait();

            Alert hpCheck = new Alert(Alert.AlertType.INFORMATION);
            // game ends if hp is less than 10
            if (player.getHp() > 10) {
                hpCheck.setHeaderText("Wounded but surivived!");
                hpCheck.setContentText("You are hurt, but you still win the fight");
                hpCheck.showAndWait();
                switchScene(event, "Chapter3/GameWinScene");
            } else {
                hpCheck.setHeaderText("Too heavily wounded!");
                hpCheck.setContentText("You are too wounded to continue the fight");
                hpCheck.showAndWait();
                switchScene(event, "Chapter3/GameOverScene");
            }
        }
    }
}
