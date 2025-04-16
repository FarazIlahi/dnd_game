package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class King extends Character {
    private int special;
    private final int maxSpecial;

    public King(){
        super(12, 8, 20,3, "King", new Position(8,8));
        this.special = 10;
        this.maxSpecial = this.special;
    }

    public int getSpecial(){
        return this.special;
    }
    public void updateSpecial(int x){
        this.special += x;
    }



    @Override
    public void specialMove(){
        updateSpecial(-5);
        //does double the damage
        //Must be wihtin range
    }

    @Override
    public String specialToSrting() {
        return this.special + "/" + this.maxSpecial;
    }

}
