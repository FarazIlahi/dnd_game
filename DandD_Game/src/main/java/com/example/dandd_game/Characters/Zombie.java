package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Zombie extends Character {

    public Zombie() {
        super(25, 8, 5, 2, "Zombie", new Position(7, 4), 0, 0);
    }

    public Zombie(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
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