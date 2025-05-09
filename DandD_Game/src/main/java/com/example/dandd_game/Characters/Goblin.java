package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Goblin extends Character {
    public Goblin(){
        super(15,8, 4, 1, "Goblin", new Position(3,3), 0, 0);
    }

    public Goblin(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position,special, cost);
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
