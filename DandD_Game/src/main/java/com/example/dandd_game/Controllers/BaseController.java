package com.example.dandd_game.Controllers;

import com.example.dandd_game.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;

import java.io.IOException;

public class BaseController {
    private AnchorPane currentroot;
    private StackPane stackPane;
    private boolean is_on_settings = true;

    public void setCurrentroot(AnchorPane root){
        currentroot = root;
    }
    public AnchorPane getCurrentroot(){
        return currentroot;
    }

    public void createStackPane() throws IOException {
        stackPane = new StackPane();
        ImageView snapshot = new ImageView(getCurrentroot().snapshot(null, null));

        BoxBlur blur = new BoxBlur(10,10,3);
        snapshot.setEffect(blur);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("OptionMenu.fxml"));
        Parent popupSettings = loader.load();
        Scale scale = new Scale(0.5, 0.5);
        popupSettings.getTransforms().add(scale);


        stackPane.getChildren().addAll(snapshot, popupSettings);
        StackPane.setAlignment(popupSettings, Pos.CENTER);
    }
    public StackPane getStackPane() {
        return stackPane;
    }

    public void flip_is_on_settings(){
        if (is_on_settings){
            System.out.println("1");
            is_on_settings = false;
        }
        else {
            System.out.println("2");
            is_on_settings = true;
            System.out.println(is_on_settings);
        }
    }

    protected void init(AnchorPane root) {
        setCurrentroot(root);
        getCurrentroot().sceneProperty().addListener((obs, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    try {
                        handleKeyPress(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
    private void handleKeyPress(KeyEvent event) throws IOException {
        if (event.getCode().toString().equals("ESCAPE")) {
            System.out.println(is_on_settings + "HEREEE");
            if(is_on_settings){
                System.out.println("3");
                closePopupScene();
            }
            else {
                System.out.println("4");
                openPopupScene();
            }
        }
    }
    private void openPopupScene() throws IOException {
        flip_is_on_settings();
        System.out.println("here");
        createStackPane();
        getCurrentroot().getChildren().add(getStackPane());
    }
    private void closePopupScene(){
        System.out.println("I AM RUNING");
        flip_is_on_settings();
        getCurrentroot().getChildren().remove(getStackPane());

    }
}
