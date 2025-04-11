package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import com.example.dandd_game.LocalImages;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TitleScreenController extends BaseController {
    @FXML
    private ImageView king;
    @FXML
    private ImageView knight;
    @FXML
    private ImageView cleric;
    @FXML
    private ImageView mage;

    private GameStateManager gameState = GameStateManager.getInstance();
    private LocalImages localImages = LocalImages.getInstance();
    @FXML
    private void initialize(){
        localImages.setKingURL(king.getImage().getUrl());
        localImages.setKnightURL(knight.getImage().getUrl());
        localImages.setClericURL(cleric.getImage().getUrl());
        localImages.setMageURL(mage.getImage().getUrl());
    }

    @FXML
    private void goNext(MouseEvent event) throws IOException {
        switchScene(event,"login");
    }

    @FXML
    private void tutorial(ActionEvent event) throws IOException {gameState.setCurrentCharacter(gameState.getKing());
        gameState.createKing();
        gameState.setCurrentCharacter(gameState.getKing());
        gameState.addToParty(gameState.getCurrentCharacter());
        gameState.createKnight();
        gameState.addToParty(gameState.getKnight());
        gameState.createCleric();
        gameState.addToParty(gameState.getCleric());
        gameState.createMage();
        gameState.addToParty(gameState.getMage());
        switchScene(event,"Combat");
    }
}
