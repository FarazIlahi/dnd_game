package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Sorcerer extends Character {
    public Sorcerer(){
        super(35,10, 9, 5, "Sorcerer", new Position(16,3));
    }

    public Sorcerer(int hp, int def, int atk, int range, String name, Position position) {
        super(hp, def, atk, range, name, position);
    }

    @Override
    public void specialMove() {

    }

    @Override
    public String specialToSrting() {

        return null;
    }
}
