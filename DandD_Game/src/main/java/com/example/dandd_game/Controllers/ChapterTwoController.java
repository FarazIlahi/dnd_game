package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ChapterTwoController extends BaseController implements GameMechanics {

    @FXML
    private AnchorPane rootPane;

    @FXML
    public void initialize() {
        super.init(rootPane);
    }

    @FXML
    private void continueChapter(ActionEvent event) throws IOException {
        System.out.println("continuing"); // tesing line
        // switchScene(event, ""); *Switch to next scene*
    }

}
