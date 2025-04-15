package com.example.dandd_game.Characters;

import com.example.dandd_game.Position;


public abstract class Character{
    private int hp;
    private int max_hp;
    private int def;
    private int basic_attack;
    private int range;
    private String name;
    private String iD;
    private Position position;


    public Character(int hp, int def, int basic_attack, int range, String name, Position position){
        this.hp = hp;
        this.max_hp = hp;
        this.def = def;
        this.basic_attack = basic_attack;
        this.range = range;
        this.name = name;
        this.iD = name;
        this.position = position;
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
    public String getName() {
        return name;
    }
    public String getID(){return this.iD;}
    public Position getPosition(){
        return this.position;
    }
    public String hpToString(){
        return this.hp + "/" + this.max_hp;
    }
    public abstract String specialToSrting();




    public void setBasic_attack(int basic_attack) {
        this.basic_attack = basic_attack;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public void setRange(int range){
        this.range = range;
    }
    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPosition(Position position) {
        this.position = position;
    }




    public abstract void specialMove();
}
