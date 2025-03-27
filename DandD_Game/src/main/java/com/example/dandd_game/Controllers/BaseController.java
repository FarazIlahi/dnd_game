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
    private static AnchorPane currentroot;

    private static AnchorPane settings;
    private StackPane stackPane = new StackPane();

    private static boolean is_on_settings;

    public void setSettings(AnchorPane root){
        settings = root;
    }
    public void disableSettings(){
        settings.setVisible(false);
    }
    public void enableSettings(){
        settings.setVisible(true);
    }

    public boolean isIs_on_settings(){
        return is_on_settings;
    }

    public void setCurrentroot(AnchorPane root){
        currentroot = root;
    }
    public AnchorPane getCurrentroot(){
        return currentroot;
    }

    public void createStackPane() throws IOException {
        ImageView snapshot = new ImageView(getCurrentroot().snapshot(null, null));

        BoxBlur blur = new BoxBlur(10,10,3);
        snapshot.setEffect(blur);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("OptionMenu.fxml"));
        Parent popupSettings = loader.load();
        Scale scale = new Scale(0.5, 0.5);
        popupSettings.getTransforms().add(scale);
        StackPane.setAlignment(popupSettings, Pos.CENTER);
        stackPane.getChildren().addAll(snapshot, popupSettings);

    }
    public StackPane getStackPane() {
        return stackPane;
    }


    protected void init(AnchorPane root) {
        if(!is_on_settings){
            setCurrentroot(root);
        }
        System.out.println(getCurrentroot());
        getCurrentroot().sceneProperty().addListener((obs, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    try {
                        System.out.println(is_on_settings);
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
            if(is_on_settings){
                is_on_settings = false;
                System.out.println("3");
                closePopupScene();

            }
            else {
                is_on_settings = true;
                System.out.println("4");
                openPopupScene();

            }
        }
    }
    private void openPopupScene() throws IOException {
        createStackPane();
        System.out.println("here");
        getCurrentroot().getChildren().add(getStackPane());

    }
    private void closePopupScene(){
        getCurrentroot().getChildren().remove(getStackPane());

    }
}
