package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Orc extends Character {
    public Orc(){
        super(30,14, 7, 2, "Orc", new Position(5,3), 0, 0);
    }

    public Orc(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position,special, cost);
    }

    @Override
    public int specialMove() {
        return 0;
    }
}
