package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class King extends Character {
    public King(){
        super(new Image("com/example/dandd_game/images/pixelKing.png"),12, 8, 20,10);
    }

    @Override
    public void specialMove(){
        //does double the damage
        //Must be wihtin range
    }



}
