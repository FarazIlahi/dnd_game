package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class GameLoadsController implements GameMechanics {

    @FXML
    Button go_button;
    @FXML
    AnchorPane rootPane;
    @FXML
    private void initialize() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    try {
                        handleKeyPress(event, rootPane);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }




    public void goToGame(ActionEvent event) throws IOException {
        //switchScene(event, "OptionMenu");
    }
}