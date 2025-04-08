package com.example.dandd_game;


import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.lang.Math;

public interface GameMechanics {

    public default int rollDice(int i){
        return (int)(Math.random() * 20) + 1;

    }
    public default void highlight(Node e){
        Scale scale = new Scale(1.25, 1.25);
        e.getTransforms().add(scale);
        e.setEffect(new DropShadow(30, Color.BLACK));
    }
    public default void unhighlight(Node e){
        Scale scale = new Scale(.8, .8);
        e.getTransforms().add(scale);
        e.setEffect(null);
    }
    public default void disableImage(ImageView image){
        image.setOpacity(.5);
        image.setDisable(true);
    }


}
