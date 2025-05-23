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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FinalDefendController extends BaseController implements GameMechanics {
    @FXML
    public Pane rootPane;
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
    private void holdMainGate() throws InterruptedException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        Double spinDuration = spin(dice);
        unhighlight(dice);
        pauseMethodThrowing(spinDuration, this::holdMainGateLogic);
    }

    public void holdMainGateLogic() throws IOException{
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hold Main Gate");
        GameStateManager gsm = GameStateManager.getInstance();
        GameStateManager.getInstance().resetAllCharacterPositions();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());

        if (roll >= 10) {
            alert.setHeaderText("Success!");
            alert.setContentText("You held off the enemy It's just the Sorcerer now!");
            alert.show();

            gsm.createSorcerer();
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene("Combat");
        } else {
            alert.setHeaderText("Partial Failure!");
            alert.setContentText("The enemy broke through. You must have one final fight!");
            alert.show();

            gsm.createOrc();
            gsm.createGoblin();
            gsm.createGoblin();
            gsm.createSorcerer();

            gsm.getOrc().setName("Sorcerer's Orc");
            Goblin g1 = new Goblin();
            g1.setName("Goblin Berserker");
            Goblin g2 = new Goblin();
            g2.setName("Sorcerer's Goblin");
            gsm.addToEnemys(gsm.getOrc());
            gsm.addToEnemys(g1);
            gsm.addToEnemys(g2);
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene("Combat");
        }
    }
    @FXML
    private void setTrap() throws InterruptedException {
        playSoundFX("/com/example/dandd_game/soundFX/buttonClick.mp3", .75);
        Double spinDuration = spin(dice);
        unhighlight(dice);
        pauseMethodThrowing(spinDuration, this::setTrapLogic);
    }
    public void setTrapLogic() throws IOException{
        int roll = rollDice(20);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Set Trap");

        GameStateManager gsm = GameStateManager.getInstance();
        GameStateManager.getInstance().resetAllCharacterPositions();
        gsm.resetEnemies();
        gsm.resetList(gsm.getTurnOrder());

        if (roll >= 8) {
            alert.setHeaderText("Perfect Ambush!");
            alert.setContentText("The trap destroys the enemy forces. You are victorious!");
            alert.show();
            gsm.createSorcerer();
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene("Combat");
        } else {
            alert.setHeaderText("Trap Failed!");
            alert.setContentText("You were caught. You must fight them now!");
            alert.show();

            gsm.createOrc();
            gsm.createImp();
            gsm.createSorcerer();

            gsm.getOrc().setName("Sorcerer's Orc");
            gsm.getImp().setName("Sorcerer's Imp");
            gsm.getSorcerer().setName("The Sorcerer");
            gsm.addToEnemys(gsm.getOrc());
            gsm.addToEnemys(gsm.getImp());
            gsm.addToEnemys(gsm.getSorcerer());
            gsm.setNextScene("Chapter3/GameWinScene");
            switchScene("Combat");
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
