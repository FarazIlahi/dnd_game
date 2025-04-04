package com.example.dandd_game.Characters;

public class Sorcerer extends Character {
    public Sorcerer(){
        super(18, 10, 16, 30);
    }
    @Override
    public void specialMove(){
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
    }
}
