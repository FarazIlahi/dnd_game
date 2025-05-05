package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Skeleton extends Character {

    public Skeleton() {
        super(16, 10, 5, 2, "Skeleton", new Position(6, 3), 0, 0);
    }

    public Skeleton(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
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
