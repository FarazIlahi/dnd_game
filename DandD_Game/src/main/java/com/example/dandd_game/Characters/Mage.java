package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Mage extends Character {
    public Mage(){
        super(18, 10, 16, 12, "Mage", new Position(4,4));
    }
    @Override
    public void specialMove(){
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
    }
    @Override
    public void specialToSrting() {

    }
}
