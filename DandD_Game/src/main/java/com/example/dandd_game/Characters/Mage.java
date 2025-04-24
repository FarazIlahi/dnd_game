package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Mage extends Character {


    public Mage(){
        super(18, 10, 8, 6, "Mage", new Position(11,11), 36, 12);
    }
    public Mage(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position, special, cost);
    }
    @Override
    public int specialMove(){
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
        return 7;
    }
}
