package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class StatRollController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private TextArea info_area;
    private GameStateManager gameState = GameStateManager.getInstance();

    @FXML
    private void initialize(){
        super.init(rootPane);
        String name = gameState.getCurrentCharacter().getName();
        int hp = gameState.getCurrentCharacter().getHp();
        int def = gameState.getCurrentCharacter().getDef();
        int basic_atk = gameState.getCurrentCharacter().getBasic_attack();
        int range = gameState.getCurrentCharacter().getRange();
        info_area.setText("\t\t" + name + "'s Stats" +
                "\n\nHP: " + hp +
                "\nDef: " + def +
                "\nBasic Attack: " + basic_atk +
                "\nRange: " + range);
    }

    @FXML
    private void rollStat(){
        System.out.println(gameState.getCurrentCharacter().toString());
    }
}
