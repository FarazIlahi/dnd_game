package com.example.dandd_game.Chapter1;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Characters.Goblin;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class InfiltrateController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;
    @FXML
    private ImageView dice;

    @FXML
    private void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void proceedInfiltration(ActionEvent event) throws IOException, InterruptedException {
        Double spinDuration =  spin(dice);
        unhighlight(dice);
        pauseMethodThrowing(spinDuration, this::showAlert);
    }
    private void showAlert() throws IOException {
        int roll= rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Infiltration");
        alert.setHeaderText("You infiltrate the enemy lines and find a weak spot.");

        if (roll >=10) {
            alert.setContentText("You rolled a " + roll + "\nSuccess! You infiltrate the enemy lines and took advantage.");
            alert.show();

            switchScene("Chapter2/ChapterTwoScene");
        }
        else {
            alert.setContentText("You rolled a " + roll + "\nFailed! You are caught.");
            alert.show();

            GameStateManager gsm = GameStateManager.getInstance();
            gsm.resetEnemies();
            gsm.resetList(gsm.getTurnOrder());
            gsm.createGoblin();
            gsm.createGoblin();
            gsm.getGoblin().setName("Goblin Scout");
            Goblin goblinB = new Goblin();
            goblinB.setName("Goblin Fighter");
            gsm.addToEnemys(gsm.getGoblin());
            gsm.addToEnemys(goblinB);
            gsm.setNextScene("Chapter2/ChapterTwoScene");
            switchScene("Combat");
        }
    }
}
