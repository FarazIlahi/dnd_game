package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Knight extends Character {

    public Knight(){
        super(40, 18, 12, 3, "Knight", new Position(2,2));
    }
    @Override
    public void specialMove(){
        //blocks attack for an ally for half the dmg
        //Must be wihtin rangew
    }
    @Override
    public void specialToSrting() {

    }
}
