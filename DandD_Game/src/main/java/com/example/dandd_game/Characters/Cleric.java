package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Cleric extends Character {

    public  Cleric(){
        super(22, 12, 5, 5, "Cleric", new Position(8,11), 20, 10);

    }
    public Cleric(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position, special, cost);

    }
    @Override
    public int specialMove(){
        updateSpecial();
        //heals all for 15hp(Could change)
        //Must be wihtin range
        return -15;
    }
}
