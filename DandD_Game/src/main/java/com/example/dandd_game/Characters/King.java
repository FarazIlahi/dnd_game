package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class King extends Character {

    public King(){
        super(12, 8, 10,3, "King", new Position(8,8), 10, 5);
    }

    public King(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position, special, cost);
    }
    @Override
    public int specialMove(){
        return getBasic_attack() * 2;
    }

}
