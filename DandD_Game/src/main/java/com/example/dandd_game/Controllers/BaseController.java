package com.example.dandd_game.Controllers;

import com.example.dandd_game.GameMechanics;
import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.KeyBindingManager;
import com.example.dandd_game.MainApplication;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class BaseController implements GameMechanics {
    private static Pane currentroot;

    private static Pane settings;
    private StackPane stackPane = new StackPane();

    private static boolean is_on_settings;

    public void setSettings(Pane root){
        settings = root;
    }

    public void setCurrentroot(Pane root){
        currentroot = root;
    }
    public Pane getCurrentroot(){
        return currentroot;
    }
    KeyBindingManager keyManager;

    public void createStackPane() throws IOException {
        ImageView snapshot = new ImageView(getCurrentroot().snapshot(null, null));
        BoxBlur blur = new BoxBlur(10,10,3);

        snapshot.setEffect(blur);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("OptionMenu.fxml"));
        Parent popupSettings = loader.load();
        StackPane.setAlignment(popupSettings, Pos.CENTER);
        stackPane.getChildren().addAll(snapshot, popupSettings);
    }

    public StackPane getStackPane() {
        return stackPane;
    }


    protected void init(Pane root) {
        if(!is_on_settings){
            setCurrentroot(root);
            keyManager = new KeyBindingManager(getCurrentroot());
        }
        keyManager = new KeyBindingManager(root);
        keyManager.addKeyBinding("ESCAPE", this::handleKeyPress);
    }
    private void handleKeyPress() throws IOException {
        if(is_on_settings){
            is_on_settings = false;
            closePopupScene();
        }
        else {
            is_on_settings = true;
            openPopupScene();
        }
    }
    private void openPopupScene() throws IOException {
        createStackPane();
        getCurrentroot().getChildren().add(getStackPane());

    }
    private void closePopupScene(){
        getCurrentroot().getChildren().remove(getStackPane());

    }
    public void switchScene(Event event, String new_scene) throws IOException {
        GameStateManager.getInstance().setCurrentScene(new_scene);
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        Stage primaryStage = (Stage) scene.getWindow();

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(new_scene + ".fxml"));
        Parent root = loader.load();
        Scene newscene = new Scene(root);
        primaryStage.setScene(newscene);
    }

    public void switchScene(String new_scene) throws IOException {
        GameStateManager.getInstance().setCurrentScene(new_scene);
        Stage stage = (Stage) currentroot.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(new_scene + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}