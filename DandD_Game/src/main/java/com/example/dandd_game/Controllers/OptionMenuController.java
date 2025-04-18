package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameSaves;
import com.example.dandd_game.GameStateManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class OptionMenuController extends BaseController implements GameMechanics {
    @FXML
    Slider audioSlider;
    @FXML
    Label audioNum;
    @FXML
    Pane rootPane;

    private int audio;
    private static boolean has_initialized = false;
    @FXML
    private void initialize() {
        super.init(rootPane);

        audioNum.textProperty().bind(audioSlider.valueProperty().asString("%.0f"));
        if(!has_initialized){
            one_time_initialize();
        }

    }

    private void one_time_initialize(){
        setSettings(rootPane);

        audioSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number oldValue, Number newValue) {
                        audio = newValue.intValue();
                        audioSlider.setValue(audio);
                    }
                }
        );
        has_initialized = true;
    }

    @FXML private void saveGame() {
        try {
            int slot = GameSaves.getSelectedSlot();
            GameSaves.saveGame(slot);
            GameSaves.saveAchievements();
            System.out.println("Game saved to slot " + slot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadGame() {
        try {
            GameSaves.loadGame();
            String scene = GameStateManager.getInstance().getCurrentScene();
            switchScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToMenu() {
        try {
            switchScene("GameLoads");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
