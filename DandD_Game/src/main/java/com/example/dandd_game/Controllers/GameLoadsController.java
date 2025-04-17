package com.example.dandd_game.Controllers;

import com.example.dandd_game.AchievementPopup;
import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
        String achievement = GameStateManager.getInstance().getPendingAchievement();
        if (achievement != null) {
            AchievementPopup.show(rootPane, "Achievement unlocked: " + achievement);
        }
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
    @FXML
    private void openAchievements(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dandd_game/AchievementsScene.fxml"));
        Parent root = loader.load();

        AchievementsController controller = loader.getController();
        controller.refreshAchievements();
        rootPane.getScene().setRoot(root);
    }


    public void onLoadClicked(ActionEvent event) throws IOException {
        if(creatingFile){
            switchScene(event,"playerCount");
        }
        else{
            Button clickedButton = (Button) event.getSource();
            //switchScene("");
        }
    }

    @FXML
    private void tutorial(ActionEvent event) throws IOException {
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

        //gameState.createGoblin();
        //gameState.createOrc();
        gameState.createSorcerer();
        //gameState.addToEnemys(gameState.getGoblin());
        //gameState.addToEnemys(gameState.getOrc());
        gameState.addToEnemys(gameState.getSorcerer());


        switchScene(event,"Combat");
    }
}