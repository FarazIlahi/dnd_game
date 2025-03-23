package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class OptionMenuController extends BaseController implements GameMechanics {
    @FXML
    Slider audioSlider;
    @FXML
    Text audioNum;
    @FXML
    AnchorPane rootPane;
    @FXML
    private void initialize() {
        super.init(rootPane);
    }



}
