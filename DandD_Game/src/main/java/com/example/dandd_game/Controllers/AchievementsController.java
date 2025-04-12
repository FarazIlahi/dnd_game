package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class AchievementsController extends BaseController{

    @FXML
    private ListView<String> achievementList;


    public void refreshAchievements() {
        ObservableList<String> achievements = FXCollections.observableArrayList(GameStateManager.getInstance().getAchievements());
        achievementList.setItems(achievements);
    }

    @FXML
    public void backToGameLoads (ActionEvent event) throws IOException {
        switchScene(event, "gameLoads");
    }

}
