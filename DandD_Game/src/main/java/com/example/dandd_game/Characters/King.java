package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class King extends Character {

    public King(){
        super(12, 8, 99,3, "King", new Position(8,8), 10, 5);
    }

    public King(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position, special, cost);
    }
    @Override
    public int specialMove(){
        return getBasic_attack() * 2;
    }
    @Override
    public String getID() {
        return "King";
    }

    public int typeMatchup(Character Target) {
        int damageKing = gameState.getKing().getBasic_attack();
        int specialKing = gameState.getKing().specialMove();

        if ("Imp".equals(Target.getID())) {
            specialKing /= 2;
            damageKing /= 2;
        }
        return damageKing + specialKing;
    }
}
