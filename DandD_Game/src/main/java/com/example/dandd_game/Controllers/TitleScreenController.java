package com.example.dandd_game.Controllers;

import javafx.event.ActionEvent;
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
    @FXML
    private ImageView goblin;
    @FXML
    private ImageView orc;
    @FXML
    private ImageView slash;
    @FXML
    private ImageView explosion;
    @FXML
    private ImageView heal;
    @FXML
    private ImageView xAttack;
    @FXML
    private ImageView shield;
    @FXML
    private ImageView modAttack;


    private LocalImages localImages = LocalImages.getInstance();
    @FXML
    private void initialize(){
        localImages.setKingURL(king.getImage().getUrl());
        localImages.setKnightURL(knight.getImage().getUrl());
        localImages.setClericURL(cleric.getImage().getUrl());
        localImages.setMageURL(mage.getImage().getUrl());
        localImages.setSorcererURL(sorcerer.getImage().getUrl());
        localImages.setGoblinURL(goblin.getImage().getUrl());
        localImages.setOrcURL(orc.getImage().getUrl());
        localImages.setSlashURL(slash.getImage().getUrl());
        localImages.setExplosionURL(explosion.getImage().getUrl());
        localImages.setHealURL(heal.getImage().getUrl());
        localImages.setxAttackURL(xAttack.getImage().getUrl());
        localImages.setShieldURL(shield.getImage().getUrl());
        localImages.setModAttackURL(modAttack.getImage().getUrl());
        super.setMusic("DandD_Game/src/main/resources/com/example/dandd_game/sounds/introMusic.wav");
    }

    @FXML
    private void goNext(MouseEvent event) throws IOException {
        switchScene(event,"login");
    }
    @FXML
    private void tutorial(ActionEvent event) throws IOException {
        super.init(root);
        gameState.resetInstance();
        gameState.setPlayerCount(4);
        gameState.createKing();
        gameState.createKnight();
        gameState.createCleric();
        gameState.createMage();
        gameState.addToParty(gameState.getKing());
        gameState.addToParty(gameState.getKnight());
        gameState.addToParty(gameState.getCleric());
        gameState.addToParty(gameState.getMage());

        gameState.createGoblin();

        gameState.addToEnemys(gameState.getGoblin());


        switchScene(event,"Combat");
    }
}
