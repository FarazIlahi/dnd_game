package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class OptionMenuController extends BaseController implements GameMechanics {
    @FXML
    Slider audioSlider;
    @FXML
    Label audioNum;
    @FXML
    Pane rootPane;
    @FXML
    Button back;

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
        System.out.println("YOLOOO");
        setSettings(rootPane);
        //disableSettings();


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
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        switchScene(event, "GameLoads");
    }



}
