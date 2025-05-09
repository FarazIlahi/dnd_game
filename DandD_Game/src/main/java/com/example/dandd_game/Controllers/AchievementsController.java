package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameStateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class AchievementsController extends BaseController{
    @FXML
    private Pane root;
    @FXML
    private ListView<String> achievementList;
    @FXML
    private void initialize() {
        achievementList.setFocusTraversable(false);
        super.init(root);
        refreshAchievements();
    }

    public void refreshAchievements() {
        List<String> achievements = GameStateManager.getInstance().getAchievements();
        ObservableList<String> observable = FXCollections.observableArrayList(achievements);
        achievementList.setItems(observable);
    }
    @FXML
    public void backToGameLoads (ActionEvent event) throws IOException {
        switchScene(event, "GameLoads");
    }
}
