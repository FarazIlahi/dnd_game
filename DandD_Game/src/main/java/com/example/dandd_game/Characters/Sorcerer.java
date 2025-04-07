package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class Sorcerer extends Character {
    public Sorcerer(){
        super(new Image("com/example/dandd_game/images/pixelWitch.png"), 18, 10, 16, 30);
    }
    @Override
    public void specialMove(){
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
    }
}
