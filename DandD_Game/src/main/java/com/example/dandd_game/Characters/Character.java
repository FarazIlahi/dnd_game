package com.example.dandd_game.Characters;

import com.example.dandd_game.GameStateManager;
import com.example.dandd_game.Position;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public abstract class Character{
    private int hp;
    private int max_hp;
    private int def;
    private int basic_attack;
    private int range;
    private String name;
    private String iD;
    private Position position;
    private int special;
    private int max_special;
    private int specialCost;

    private ImageView profile;
    private Label nameLabel;
    private ProgressBar hpBar;
    private ProgressBar specialBar;
    private Label hpInfo;
    private Label specialInfo;
    private ArrayList<Button> buttons = new ArrayList<Button>();

    private boolean highlighted = false;
    private boolean isDead = false;
    protected GameStateManager gameState = GameStateManager.getInstance();

    public Character(int hp, int def, int basic_attack, int range, String name, Position position, int special, int specialCost){
        this.hp = hp;
        this.max_hp = hp;
        this.def = def;
        this.basic_attack = basic_attack;
        this.range = range;
        this.name = name;
        this.iD = name;
        this.position = position;
        this.isDead = false;
        this.special = special;
        this.max_special = special;
        this.specialCost = specialCost;
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
    public int getSpecial(){
        return this.special;
    }

    public int getSpecialCost() {
        return specialCost;
    }
    public int getMax_special() {
        return max_special;
    }


    public String hpToString(){
        return this.hp + "/" + this.max_hp;
    }

    public ImageView getProfile() {
        return profile;
    }

    public boolean getHighlighted(){
        return this.highlighted;
    }
    public boolean getIsDead(){
        return this.isDead;
    }

    public Label getNameLabel(){
        return this.nameLabel;
    }
    public ProgressBar getHpBar() {
        return hpBar;
    }
    public ProgressBar getSpecialBar() {
        return specialBar;
    }
    public Label getHpInfo() {
        return hpInfo;
    }
    public Label getSpecialInfo() {
        return specialInfo;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public void setBasic_attack(int basic_attack) {
        this.basic_attack = basic_attack;
    }
    public void setHp(int hp) {
        this.hp = hp;
        if(this.hp <= 0){
            this.isDead = true;
            this.hp = 0;
        }
        else if (this.hp > this.max_hp) {
            this.hp = this.max_hp;
        }
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
    public void setProfile(ImageView profile) {
        this.profile = profile;
    }
    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }
    public void setHpBar(ProgressBar hpBar) {
        this.hpBar = hpBar;
    }
    public void setSpecialBar(ProgressBar specialBar) {
        this.specialBar = specialBar;
    }
    public void setHpInfo(Label hpInfo) {
        this.hpInfo = hpInfo;
    }
    public void setSpecialInfo(Label specialInfo) {
        this.specialInfo = specialInfo;
    }

    public void addButton(Button button) {
        this.buttons.add(button);
    }
    public void resetButtons(){
        gameState.resetList(buttons);
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void updateSpecial(){
        this.special -= this.specialCost;
        if(this.special <= 0){
            this.special = 0;
        }
        else if (this.special > this.max_special) {
         this.special = max_special;
        }
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public abstract int specialMove();


    public String specialToSrting() {
        return this.special + "/" + this.max_special;
    }

    public abstract int typeMatchup(Character Target);
}
