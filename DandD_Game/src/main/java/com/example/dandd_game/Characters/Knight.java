package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class Knight extends Character {

    public Knight(){
        super(new Image("com/example/dandd_game/images/pixelKnight.png"), 40, 18, 12, 5);
    }
    @Override
    public void specialMove(){
        //blocks attack for an ally for half the dmg
        //Must be wihtin rangew
    }

}
