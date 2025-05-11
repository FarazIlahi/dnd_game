package com.example.dandd_game.Chapter1;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.Characters.Orc;
import com.example.dandd_game.Controllers.BaseController;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SiegeSceneController extends BaseController implements GameMechanics {

    @FXML
    private Pane rootPane;
    @FXML
    private ImageView dice;
    @FXML
    private Label siegeText;

    @FXML
    public void initialize() {
        super.init(rootPane);
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
    }

    @FXML
    private void holdWalls(ActionEvent event) throws IOException {
        GameStateManager gsm = GameStateManager.getInstance();
        GameStateManager.getInstance().resetAllCharacterPositions();
        if(GameStateManager.getInstance().unlockAchievement("You chose to hold the castle walls!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to hold the castle walls!");
        }

        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());
        gsm.createOrc();
        gsm.getOrc().setName("Numhug the Orc");
        gsm.addToEnemys(gsm.getOrc());
        gsm.createZombie();
        gsm.getZombie().setName("Larry the Zombie");
        gsm.addToEnemys(gsm.getZombie());
        gsm.setNextScene("Chapter2/ChapterTwoScene");
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        switchScene("Combat");
    }

    @FXML
    private void flankEnemy() throws InterruptedException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        Double spinDuration = spin(dice);
        unhighlight(dice);
        pauseMethodThrowing(spinDuration, this::flankLogic);
    }
    public void flankLogic() throws IOException {
        int roll = rollDice(20);
        GameStateManager gsm = GameStateManager.getInstance();
        GameStateManager.getInstance().resetAllCharacterPositions();
        if (GameStateManager.getInstance().unlockAchievement("You chose to flank the enemy!")) {
            GameStateManager.getInstance().queueAchievementPopup("You chose to flank the enemy!");
        }
        if (roll >= 5) {
            switchScene("Chapter1/SneakAttackScene");
        } else {
            gsm.resetEnemies();
            gsm.resetList(gsm.getTurnOrder());
            gsm.createGoblin();
            gsm.addToEnemys(gsm.getGoblin());
            gsm.createOrc();
            gsm.addToEnemys(gsm.getOrc());
            gsm.setNextScene("Chapter1/SneakAttackScene");
            switchScene("Combat");
        }
    }


    private void showRollResult(int roll, String action) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Roll Result");
        alert.setHeaderText(action);
        alert.show();
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
