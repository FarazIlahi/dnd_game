package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;

public class Cleric extends Character {
    private int mana;
    private final int maxMana;
    public  Cleric(){
        super(22, 12, 5, 5, "Cleric", new Position(8,11));
        this.mana = 20;
        this.maxMana = this.mana;
    }

    public Cleric(int hp, int def, int atk, int range, String name, Position position) {
        super(hp, def, atk, range, name, position);
        this.mana = 20;
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
        updateMana(-10);
        //heals all for 15hp(Could change)
        //Must be wihtin range
    }
    @Override
    public String specialToSrting() {
        return this.mana + "/" + this.maxMana;
    }
}
