package com.example.dandd_game;

public class GameStateManager {
    private static GameStateManager instance;

    private int playerCount;
    private String difficulty;
    private String campaignName;

    private Character p1;
    private Character p2;
    private Character p3;
    private Character p4;

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public String getDifficulty(){
        return this.difficulty;
    }
    public String getCampaignName(){
        return this.campaignName;
    }

    private GameStateManager() {}

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

}
