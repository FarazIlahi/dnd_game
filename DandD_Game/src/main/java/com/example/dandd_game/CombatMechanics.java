package com.example.dandd_game;

import com.example.dandd_game.Characters.Character;

import java.util.ArrayList;
import java.util.Collections;

public interface CombatMechanics {
    GameStateManager gameState = GameStateManager.getInstance();

    default void shuffleTurnOrder(){
        ArrayList<Character> list = gameState.getTurnOrder();
        Collections.shuffle(list);
        if(gameState.getEnemies().contains(list.getFirst())){
            shuffleTurnOrder();
        }
    }

    default void doTurn(){
        if(gameState.getEnemies().contains(gameState.getTurnOrder().getFirst())){
            System.out.println("enemy turn here");
            gameState.nextTurn();
        }
        else {

        }
    }


}
