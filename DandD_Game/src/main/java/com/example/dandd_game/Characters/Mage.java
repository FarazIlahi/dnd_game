package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class Mage extends Character {
    public Mage(){
        super(18, 10, 16, 30, "Mage");
    }
    @Override
    public void specialMove(){
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
    }
}
