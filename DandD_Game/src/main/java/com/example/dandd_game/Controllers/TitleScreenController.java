package com.example.dandd_game.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import com.example.dandd_game.LocalImages;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class TitleScreenController extends BaseController {
    @FXML
    private Pane root;
    @FXML
    private ImageView king;
    @FXML
    private ImageView knight;
    @FXML
    private ImageView cleric;
    @FXML
    private ImageView mage;
    @FXML
    private ImageView sorcerer;


    private LocalImages localImages = LocalImages.getInstance();
    @FXML
    private void initialize(){
        localImages.setKingURL(king.getImage().getUrl());
        localImages.setKnightURL(knight.getImage().getUrl());
        localImages.setClericURL(cleric.getImage().getUrl());
        localImages.setMageURL(mage.getImage().getUrl());
        localImages.setSorcererURL(sorcerer.getImage().getUrl());
    }

    @FXML
    private void goNext(MouseEvent event) throws IOException {
        switchScene(event,"login");
    }
}
