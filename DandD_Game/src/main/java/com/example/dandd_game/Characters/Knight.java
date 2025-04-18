package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Knight extends Character {
    private int special;
    private final int maxSpecial;

    public Knight(){
        super(40, 18, 12, 2, "Knight", new Position(11,8));
        this.special = 25;
        this.maxSpecial = this.special;
    }

    public Knight(int hp, int def, int atk, int range, String name, Position position) {
        super(hp, def, atk, range, name, position);
        this.special = 25;
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
        //blocks attack for an ally for half the dmg
        //Must be wihtin rangew
    }

    @Override
    public String specialToSrting() {
        return this.special + "/" + this.maxSpecial;
    }
}
