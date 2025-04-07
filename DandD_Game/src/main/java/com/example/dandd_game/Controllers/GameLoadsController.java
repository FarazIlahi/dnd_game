package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.io.IOException;


public class GameLoadsController extends BaseController implements GameMechanics {

    @FXML
    Button nvmButton;
    @FXML
    Button load1;
    @FXML
    Button load2;
    @FXML
    Button load3;
    @FXML
    Button load4;
    @FXML
    Pane rootPane;
    private boolean creatingFile;
    @FXML
    private void initialize() {
        super.init(rootPane);
        creatingFile = false;
    }
    @FXML
    public void createNewFile(ActionEvent event) throws IOException{
        rootPane.setEffect(new Glow(.25));
        creatingFile = true;
        nvmButton.setDisable(false);
        nvmButton.setOpacity(1);
    }
    @FXML
    public void dontCreateNewFile(ActionEvent event){
        rootPane.setEffect(null);
        creatingFile = false;
        nvmButton.setDisable(true);
        nvmButton.setOpacity(0);
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

    public void highlight(Button button){
        Scale scale = new Scale(1.25, 1.25);
        button.getTransforms().add(scale);
        button.setEffect(new DropShadow(30, Color.BLACK));
    }
    public void unhighlight(Button button){
        Scale scale = new Scale(.8, .8);
        button.getTransforms().add(scale);
        button.setEffect(null);
    }


    public void onLoadClicked(ActionEvent event) throws IOException {
        if(creatingFile){
            switchScene(event,"CharacterSelect");
        }
        else{
            Button clickedButton = (Button) event.getSource();
            //switchScene("");
        }
    }
}