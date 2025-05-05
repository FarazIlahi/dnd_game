package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Imp extends Character {

    public Imp() {
        super(10, 9, 6, 3, "Imp", new Position(2, 4), 0, 0);
    }

    public Imp(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position, special, cost);
    }

    @Override
    public int specialMove() {
        return 0;
    }

    @Override
    public int typeMatchup(Character Target) {
        return 0;
    }
}

