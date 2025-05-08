package com.example.dandd_game.Controllers;

import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private ImageView dice;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea statChange_area;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Button back_btn;


    private GameStateManager gameState = GameStateManager.getInstance();
    private ArrayList<String> order = new ArrayList<>(3);
    private String currentStat;
    private Character workingCharacter = gameState.getCurrentCharacter();
    private boolean isSpinning = false;
    private double spinDuration;
    private final List<String> defaultNames = Arrays.asList(
            "Thalor", "Elyndra", "Branok", "Zalara", "Kael", "Nymeria", "Orin", "Luthien", "Draven", "Seraphine",
            "Faelar", "Calista", "Jareth", "Mireille", "Tavion", "Rowan", "Isolde", "Alaric", "Ysolde", "Fenric"
    );


    @FXML
    private void initialize(){
        super.init(rootPane);
        updateInfo();
        displayRollInfo();
        currentStat = "HP";
        nameErrorLabel.setVisible(false);
        setStatOrder();
        updateTaskLabel();
    }

    @FXML
    private void generateRandomName(ActionEvent event){
        String randomName;
        int safetyCounter = 0;
        do {
            randomName = defaultNames.get(new Random().nextInt(defaultNames.size()));
            safetyCounter++;
        } while (GameStateManager.getInstance().nameExists(randomName) && safetyCounter < 100);
        nameField.setText(randomName);
        nameCheck(false);

    }

    public void setIsSpinningFalse(){
        isSpinning = false;
    }


    public void updateInfo(){
        String name = workingCharacter.getName();
        if(name.length() == 0){
            name = workingCharacter.getID();
        }
        int hp = workingCharacter.getHp();
        int def = workingCharacter.getDef();
        int basic_atk = workingCharacter.getBasic_attack();
        int range = workingCharacter.getRange();
        info_area.setText("\t" + name + "'s Stats" +
                "\n\nHP: " + hp +
                "\nDef: " + def +
                "\nBasic Attack: " + basic_atk +
                "\nRange: " + range + "\tWill not change");
        info_area.setStyle("-fx-font-size: 30px;");
    }
    public void displayRollInfo(){
        rollInfo.setText(
                "Roll\t\t\t|\t  Stat Change\n" +
                "1\t\t\t|\t\t-5\n" +
                "2-4\t\t\t|\t\t-3\n" +
                "5-7\t\t\t|\t\t-2\n" +
                "8-10\t\t|\t\t-1\n" +
                "11-13\t\t|\t\t+1\n" +
                "14-16\t\t|\t\t+2\n" +
                "17-19\t\t|\t\t+3\n" +
                "20\t\t\t|\t\t+5\n");
    }
    public void updateTaskLabel(){
        if(!((order.get(order.indexOf(currentStat))).equals("End"))){
            taskLabel.setText("Roll for " + currentStat);
        }
        else {
            taskLabel.setText("");
        }

    }
    public void setStatOrder(){
        order.add("HP");
        order.add("Def");
        order.add("Basic Attack");
        order.add("End");
    }
    public void updateCurrentStat(){
        if((order.get(order.indexOf(currentStat) + 1)) != null){
            currentStat = order.get(order.indexOf(currentStat) + 1);
        }

    }
    public void updateCharacterStat(){
        int roll = rollDice(20);
        int statChange = convertRollToStat(roll);
        switch (currentStat){
            case "HP":
                workingCharacter.setMax_hp(workingCharacter.getHp() + statChange);
                workingCharacter.setHp(workingCharacter.getHp() + statChange);
                upDateStatChange("HP", statChange, roll);
                break;
            case "Def":
                workingCharacter.setDef(workingCharacter.getDef() + statChange);
                upDateStatChange("Def", statChange, roll);
                break;
            case "Basic Attack":
                workingCharacter.setBasic_attack(workingCharacter.getBasic_attack() + statChange);
                upDateStatChange("Basic Attack", statChange, roll);
                break;
        }
    }
    public void upDateStatChange(String stat, int change, int roll){
        if(change > 0){
            statChange_area.setText("You rolled a " + roll + "\nYou gained " + change + " points for " + stat);
        }
        else{
            statChange_area.setText("You rolled a " + roll + "\nYou lost " + change + " points for " + stat);
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
    public void nameCheck(boolean intentionallyClicked){
        String name = nameField.getText().trim();
        if (name.length() > 12 ) {
            nameErrorLabel.setVisible(true);
            playSoundFX("/com/example/dandd_game/soundFX/error.mp3", 1);
            setNameTooLong();
        } else if ((name.length() == 0) && (intentionallyClicked)) {
            nameErrorLabel.setVisible(true);
            playSoundFX("/com/example/dandd_game/soundFX/error.mp3", 1);
            setNoName();
        } else if (GameStateManager.getInstance().nameExists(name)) {
            nameErrorLabel.setVisible(true);
            playSoundFX("/com/example/dandd_game/soundFX/error.mp3", 1);
            setNameDuplicate();
        } else {
            nameErrorLabel.setVisible(false);
            workingCharacter.setName(name);
        }
    }
    public void setNameTooLong(){
        nameErrorLabel.setText("Name is too long");
    }
    public void setNoName(){
        nameErrorLabel.setText("Enter a valid name");
    }

    public void updateRoll() throws InterruptedException {
        isSpinning = true;
        spinDuration = spin(dice);
        unhighlight(dice);
        pauseMethod(spinDuration, this::upDateAll);
        pauseMethod(spinDuration, this::setIsSpinningFalse);
    }

    public void upDateAll(){
        updateCharacterStat();
        updateInfo();
        updateCurrentStat();
        updateTaskLabel();
    }
    public void disableLastRoll(){
        back_btn.setDisable(false);
        disableNode(dice);
    }

    public void setNameDuplicate(){
        nameErrorLabel.setText("Name already exists");
    }

    @FXML
    private void rollStat() throws InterruptedException {
        nameCheck(false);
        if((nameErrorLabel.isVisible() == false) && !isSpinning){
            if ((order.get(order.indexOf(currentStat) + 1)).equals("End")){
                pauseMethod(spinDuration, this::disableLastRoll);
            }
            updateRoll();
        }
    }

    @FXML
    private void backgroundClicked(){
        nameCheck(false);
    }
    @FXML
    private void setName(ActionEvent event){
        nameCheck(false);
    }
    @FXML
    public void hovered(MouseEvent event){
        if(event.getSource() instanceof Button clickedNode){
            highlight(clickedNode);
        }
        else if (!isSpinning){
            ImageView clickedImage = (ImageView) event.getSource();
            highlight(clickedImage);
            isSpinning = false;
        }

    }
    @FXML
    public void unHovered(MouseEvent event){
        if(event.getSource() instanceof Button clickedNode){
            unhighlight(clickedNode);
        }
        else if(!isSpinning){
            ImageView clickedImage = (ImageView) event.getSource();
            unhighlight(clickedImage);
            isSpinning = false;
        }

    }
    @FXML
    private void goBack(ActionEvent event) throws IOException{
        nameCheck(true);
        if(nameErrorLabel.isVisible() == false){
            switchScene(event, "CharacterSelect");
        }

    }
}
