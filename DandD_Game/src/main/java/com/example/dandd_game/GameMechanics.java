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

    boolean is_on_settings = false;
    public default int rolld20(){
        return (int)(Math.random() * 20) + 1;

    }

    default void switchScene(ActionEvent event, String new_scene) throws IOException {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        Stage primaryStage = (Stage) scene.getWindow();

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(new_scene + ".fxml"));
        Parent root = loader.load();
        Scene newscene = new Scene(root);
        primaryStage.setScene(newscene);
    }

    default void handleKeyPress(KeyEvent event, AnchorPane rootpane) throws IOException {
        if (event.getCode().toString().equals("ESCAPE")) {
            if(is_on_settings){

            }
            else{

                openPopupScene(rootpane);
            }

        }
    }

    default void openPopupScene(AnchorPane rootpane) throws IOException {
        StackPane newRoot = new StackPane();
        ImageView snapshhot = new ImageView(rootpane.snapshot(null, null));

        BoxBlur blur = new BoxBlur(10,10,3);
        snapshhot.setEffect(blur);

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("OptionMenu.fxml"));
        Parent popupSettings = loader.load();



        newRoot.getChildren().addAll(snapshhot, popupSettings);

        StackPane.setAlignment(popupSettings, Pos.CENTER);

        rootpane.getChildren().add(newRoot);

    }

}
