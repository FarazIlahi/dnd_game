package com.example.dandd_game;

public class GameReport {
    private int playersSurvived;
    private int enemiesKilled;
    private int totalDamageDealt;
    private int rollsCompleted;
    private boolean gameCompleted;
    private int randomEventsTriggered;

    public GameReport(int playersSurvived, int enemiesKilled, int totalDamageDealt, int rollsCompleted, boolean gameCompleted, int randomEventsTriggered) {
        this.playersSurvived = playersSurvived;
        this.enemiesKilled = enemiesKilled;
        this.totalDamageDealt = totalDamageDealt;
        this.rollsCompleted = rollsCompleted;
        this.gameCompleted = gameCompleted;
        this.randomEventsTriggered = randomEventsTriggered;

    }

    public int getPlayersSurvived() {return playersSurvived;}
    public int getEnemiesKilled() {return enemiesKilled;}
    public int getTotalDamageDealt() {return totalDamageDealt;}
    public int getRollsCompleted() {return rollsCompleted;}
    public boolean isGameCompleted() {return gameCompleted;}
    public int getRandomEventsTriggered() {return randomEventsTriggered;}

}
