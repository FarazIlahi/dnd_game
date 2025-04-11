package com.example.dandd_game.Characters;

import com.example.dandd_game.LocalImages;
import javafx.scene.image.Image;

public abstract class Character{
    private int hp;
    private int max_hp;
    private int def;
    private int basic_attack;
    private int range;
    private String name;
    private String iD;

    private LocalImages localImages = LocalImages.getInstance();


    public Character(int hp, int def, int basic_attack, int range, String name){
        this.hp = hp;
        this.max_hp = hp;
        this.def = def;
        this.basic_attack = basic_attack;
        this.range = range;
        this.name = name;
        this.iD = name;
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
//    public void setProfile(String profileURL) {
//        this.profile = new Image(profileURL);
//    }
    public void setName(String name){
        this.name = name;
    }



    public abstract void specialMove();

    @Override
    public String toString(){
        return ("" + range);
    }
}
