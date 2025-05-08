package com.example.dandd_game;

import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import java.io.IOException;
import java.lang.Math;
import java.net.URL;

public interface GameMechanics {
    GameStateManager gameState = GameStateManager.getInstance();

    default int rollDice(int i){
        return (int)(Math.random() * i) + 1;
    }
    default double spin(ImageView image) throws InterruptedException {
        playSoundFX("/com/example/dandd_game/soundFX/diceRoll.mp3");
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.35), image);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(4);
        rotate.setInterpolator(Interpolator.LINEAR);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.35), image);
        scale.setFromX(1.0);
        scale.setToX(1.2);
        scale.setFromY(1.0);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(4);

        ParallelTransition spinAndPulse = new ParallelTransition(rotate, scale);
        spinAndPulse.play();
        highlight(image);
        return rotate.getDuration().toSeconds() * rotate.getCycleCount();
    }
    default void playSoundFX(String fx) {
        URL soundURL = getClass().getResource(fx);
        if (soundURL != null) {
            Media sound = new Media(soundURL.toExternalForm());
            MediaPlayer player = new MediaPlayer(sound);
            player.play();
        } else {
            System.err.println("❌ Could not find diceRoll.mp3");
        }
    }
    default void pauseMethod(Double seconds,Runnable method) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> {
            method.run();
        });
        pause.play();
    }
    default void pauseMethodThrowing(Double seconds,ThrowingRunnable method) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> {
            try {
                method.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        pause.play();
    }
    default void highlight(Node e){
        if (e.getEffect() == null) {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), e);
            st.setToX(1.2);
            st.setToY(1.2);
            st.play();
            e.setEffect(new DropShadow(30, Color.BLACK));
        }
    }
    default void unhighlight(Node e){
        if (e.getEffect() != null) {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), e);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
            e.setEffect(null);
        }
    }
    default void disableNode(Node node){
        node.setOpacity(.5);
        node.setDisable(true);
    }
    default void enableNode(Node node){
        node.setOpacity(1);
        node.setDisable(false);
    }
    default void playAttackSoundFX(String soundFXFile){
        gameState.playSoundFX(soundFXFile);
    }

}