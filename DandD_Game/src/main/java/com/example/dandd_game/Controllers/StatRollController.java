package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.*;

public class StatRollController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private TextArea info_area;
    @FXML
    private Label taskLabel;
    @FXML
    private TextArea rollInfo;

    @FXML
    private Button roll_btn;
    @FXML
    private Button back_btn;

    private GameStateManager gameState = GameStateManager.getInstance();
    private ArrayList<String> order = new ArrayList<>(4);
    private String currentStat;
    private Character workingCharacter = gameState.getCurrentCharacter();

    @FXML
    private void initialize(){
        super.init(rootPane);
        updateInfo();
        displayRollInfo();
        currentStat = "HP";
        setStatOrder();
        updateTaskLabel();
    }



    public void updateInfo(){
        String name = workingCharacter.getName();
        int hp = workingCharacter.getHp();
        int def = workingCharacter.getDef();
        int basic_atk = workingCharacter.getBasic_attack();
        int range = workingCharacter.getRange();
        info_area.setText("\t" + name + "'s Stats" +
                "\n\nHP: " + hp +
                "\nDef: " + def +
                "\nBasic Attack: " + basic_atk +
                "\nRange: " + range);
    }
    public void displayRollInfo(){
        rollInfo.setText(
                "Roll\t\t\t|\t  Stat Change\n" +
                "1\t\t\t|\t\t-5\n" +
                "2-4\t\t\t|\t\t-3\n" +
                "5-7\t\t\t|\t\t-2\n" +
                "8-10\t\t\t|\t\t-1\n" +
                "11-13\t\t|\t\t+1\n" +
                "14-16\t\t|\t\t+2\n" +
                "17-19\t\t|\t\t+3\n" +
                "20\t\t\t|\t\t+5\n");
    }
    public void updateTaskLabel(){
        if(!((order.get(order.indexOf(currentStat))).equals("End"))){
            taskLabel.setText("Roll for " + currentStat);
        }

    }
    public void setStatOrder(){
        order.add("HP");
        order.add("Def");
        order.add("Basic Attack");
        order.add("Range");
        order.add("End");

    }
    public void updateCurrentStat(){

        if(!((order.get(order.indexOf(currentStat) + 1)).equals(null))){
            currentStat = order.get(order.indexOf(currentStat) + 1);
        }

    }
    public void updateCharacterStat(){
        int roll = rollDice(20);
        int statChange = convertRollToStat(roll);
        System.out.println(roll);
        System.out.println(statChange + "\n\n");
        switch (currentStat){
            case "HP":
                workingCharacter.setHp(workingCharacter.getHp() + statChange);
                break;
            case "Def":
                workingCharacter.setDef(workingCharacter.getDef() + statChange);
                break;
            case "Basic Attack":
                workingCharacter.setBasic_attack(workingCharacter.getBasic_attack() + statChange);
                break;
            case "Range":
                workingCharacter.setRange(workingCharacter.getRange() + statChange);
                break;

        }
    }
    public int convertRollToStat(int roll){
        int stat;
        if(roll == 1){
            stat = -5;
        }
        else if (roll >= 2 && roll <= 4){
            stat = -3;
        }
        else if (roll >= 5 && roll <= 7){
            stat = -2;
        }
        else if (roll >= 8 && roll <= 10){
            stat = -1;
        }
        else if (roll >= 11 && roll <= 13){
            stat = +1;
        }
        else if (roll >= 14 && roll <= 16){
            stat = +2;
        }
        else if (roll >= 17 && roll <= 19){
            stat = +3;
        }
        else if(roll == 20){
            stat = +5;
        }
        else {
            stat = 0;
        }
        return stat;
    }

    @FXML
    private void rollStat(){
        if ((order.get(order.indexOf(currentStat) + 1)).equals("End")){
            roll_btn.setDisable(true);
            back_btn.setDisable(false);
        }
        updateCharacterStat();
        updateInfo();
        updateCurrentStat();
        updateTaskLabel();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException{
        switchScene(event, "CharacterSelect");
    }
}
