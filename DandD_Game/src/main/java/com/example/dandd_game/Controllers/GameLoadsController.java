package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class GameLoadsController extends BaseController implements GameMechanics {

    @FXML
    Button go_button;
    @FXML
    AnchorPane rootPane;
    @FXML
    private void initialize() {
        super.init(rootPane);
    }




    public void goToGame(ActionEvent event) throws IOException {
        //switchScene(event, "OptionMenu");
    }
}