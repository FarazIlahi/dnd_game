package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;
import javafx.scene.image.Image;

public class Cleric extends Character {
    public  Cleric(){
        super(22, 12, 10, 7, "Cleric", new Position(2,0));
    }
    @Override
    public void specialMove(){
        //heals all for 15hp(Could change)
        //Must be wihtin range
    }
}
