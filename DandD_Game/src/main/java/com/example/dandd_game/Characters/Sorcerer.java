package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Sorcerer extends Character {
    public Sorcerer(){
        super(35,10, 9, 6, "Sorcerer", new Position(16,3), 0, 0);
    }

    public Sorcerer(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position,special, cost);
    }

    @Override
    public int specialMove() {
        return 0;
    }
}
