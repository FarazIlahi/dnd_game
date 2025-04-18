package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Goblin extends Character {
    public Goblin(){
        super(15,8, 4, 4, "Goblin", new Position(10,10));
    }

    public Goblin(int hp, int def, int atk, int range, String name, Position position) {
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
