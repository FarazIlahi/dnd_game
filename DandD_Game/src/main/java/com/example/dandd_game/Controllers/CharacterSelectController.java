package com.example.dandd_game.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class CharacterSelectController {
    @FXML
    private ComboBox numPlayers;
    @FXML
    public void initialize() {
        numPlayers.getItems().addAll("1", "2", "3", "4");
        numPlayers.setValue("1");
    }

    
}
