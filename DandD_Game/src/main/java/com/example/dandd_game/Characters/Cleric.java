package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class Cleric extends Character {
    public  Cleric(){
        super(22, 12, 10, 15, "Cleric");
    }
    @Override
    public void specialMove(){
        //heals all for 15hp(Could change)
        //Must be wihtin range
    }
}
