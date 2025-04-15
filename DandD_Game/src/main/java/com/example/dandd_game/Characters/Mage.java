package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Mage extends Character {

    private int mana;
    private final int maxMana;

    public Mage(){
        super(18, 10, 16, 12, "Mage", new Position(11,11));
        this.mana = 36;
        this.maxMana = this.mana;
    }

    public int getMana(){
        return this.mana;
    }
    public void updateMana(int x){
        this.mana += x;
    }
    @Override
    public void specialMove(){
        updateMana(-12);
        //AOE attack doing 15 ATk(Could change)
        //Must be within 15 range units
    }
    @Override
    public String specialToSrting() {
        return this.mana + "/" + this.maxMana;
    }
}
