package com.example.dandd_game.Characters;

import javafx.scene.image.Image;

public class Knight extends Character {

    public Knight(){
        super(40, 18, 12, 3, "Knight");
    }
    @Override
    public void specialMove(){
        //blocks attack for an ally for half the dmg
        //Must be wihtin rangew
    }

}
