package com.example.dandd_game;
import com.example.dandd_game.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.effect.BoxBlur;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;


import java.io.IOException;
import java.lang.Math;

public interface GameMechanics {

    public default int rollDice(){
        return (int)(Math.random() * 20) + 1;

    }


}
