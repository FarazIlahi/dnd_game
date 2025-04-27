package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Knight extends Character {

    public Knight(){
        super(40, 18, 6, 2, "Knight", new Position(11,8), 25, 5);
    }

    public Knight(int hp, int def, int atk, int range, String name, Position position, int special, int cost) {
        super(hp, def, atk, range, name, position,special, cost);
    }

    @Override
    public int specialMove(){
        //blocks attack for an ally for half the dmg
        //Must be wihtin rangew
        return this.getDef();
    }
}
