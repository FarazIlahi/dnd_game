package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.io.IOException;

public class CharacterSelectController extends BaseController implements GameMechanics {
    @FXML
    private Pane rootPane;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView king;
    @FXML
    private ImageView knight;
    @FXML
    private ImageView cleric;
    @FXML
    private ImageView mage;
    private GameStateManager gameState = GameStateManager.getInstance();


    @FXML
    private void initialize(){
        super.init(rootPane);
        infoLabel.setText("Player 1, choose your character.");
        if(gameState.getPlayerCount() == 1){
            only1Player();
        }
    }

    public void only1Player(){

    }

    @FXML
    private void dosmth(ActionEvent event){

    }
    @FXML
    private void goToStatRoll(MouseEvent  event) throws IOException {
        ImageView clickedImage = (ImageView) event.getSource();
        switch (clickedImage.getId()){
            case "king":
                gameState.createKing();
                gameState.setCurrentCharacter(gameState.getKing());
                break;
            case "knight":
                gameState.createKnight();
                gameState.setCurrentCharacter(gameState.getKnight());
                break;
            case "cleric":
                gameState.createCleric();
                gameState.setCurrentCharacter(gameState.getCleric());
                break;
            case "mage":
                gameState.createMage();
                gameState.setCurrentCharacter(gameState.getMage());
                break;
        }
        switchScene(event,"StatRoll");
    }
    @FXML
    public void hovered(MouseEvent event){
        ImageView clickedImage = (ImageView) event.getSource();
        highlight(clickedImage);
    }
    @FXML
    public void unHovered(MouseEvent event){
        ImageView clickedImage = (ImageView) event.getSource();
        unhighlight(clickedImage);
    }

}
