package com.example.dandd_game.Characters;
public abstract class Character{ ;
    private int hp;
    private int max_hp;
    private int def;
    private int basic_attack;
    private int range;


    public Character(int hp, int def, int basic_attack, int range){
        this.hp = hp;
        this.max_hp = hp;
        this.def = def;
        this.basic_attack = basic_attack;
        this.range = range;
    }
    public int getHp(){
        return this.hp;
    }
    public int getMaxHp(){
        return this.max_hp;
    }
    public int getDef(){
        return this.def;
    }
    public int getBasic_attack(){
        return this.basic_attack;
    }
    public int getRange(){
        return this.range;
    }
    public abstract void specialMove();
}
