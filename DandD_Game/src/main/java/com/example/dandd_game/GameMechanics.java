package com.example.dandd_game;


import java.lang.Math;

public interface GameMechanics {

    public default int rollDice(int i){
        return (int)(Math.random() * 20) + 1;

    }


}
