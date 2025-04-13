package com.example.dandd_game;

import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import java.io.IOException;
import java.lang.Math;

public interface GameMechanics {
    GameStateManager gameState = GameStateManager.getInstance();

    default void setListener(Pane root, ThrowingRunnable method, String key){
        root.sceneProperty().addListener((obs, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if(event.getCode().toString().equals(key)) {
                        try {
                            method.run();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    default int rollDice(int i){
        return (int)(Math.random() * 20) + 1;
    }
    default double spin(ImageView image) throws InterruptedException {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(image);
        rotateTransition.setCycleCount(4);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setDuration(Duration.seconds(.35));
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.play();
        highlight(image);
        return rotateTransition.getDuration().toSeconds() * rotateTransition.getCycleCount();
    }
    default void pauseMethod(Double seconds,Runnable method) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> {
            method.run();
        });
        pause.play();
    }
    default void highlight(Node e){
        if(e.getEffect() == null){
            Scale scale = new Scale(1.25, 1.25);
            e.getTransforms().add(scale);
            e.setEffect(new DropShadow(30, Color.BLACK));
        }

    }
    default void unhighlight(Node e){
        if(!(e.getEffect() == null)){
            Scale scale = new Scale(.8, .8);
            e.getTransforms().add(scale);
            e.setEffect(null);
        }
    }
    default void disableNode(Node node){
        node.setOpacity(.5);
        node.setDisable(true);
    }
    default void unDisableNode(Node node){
        node.setOpacity(1);
        node.setDisable(false);
    }

}