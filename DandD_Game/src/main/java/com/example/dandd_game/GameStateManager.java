package com.example.dandd_game;

import com.example.dandd_game.Characters.*;
import com.example.dandd_game.Characters.Character;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GameStateManager {
    private static GameStateManager instance;
    private GameStateManager() {}
    private int playerCount;
    private String difficulty;
    private String campaignName;
    private King king;
    private Knight knight;
    private Cleric cleric;
    private Mage mage;
    private Goblin goblin;
    private Orc orc;
    private Sorcerer sorcerer;

    private Character currentCharacter;
    private ArrayList<Character> party = new ArrayList<Character>();
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private ArrayList<Character> turnOrder = new ArrayList<Character>();

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

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

    public void setCurrentCharacter(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }
    public Character getCurrentCharacter() {
        return currentCharacter;
    }
    public void createKing(){
        this.king = new King();
    }
    public King getKing(){
        return this.king;
    }
    public void createKnight(){
        this.knight = new Knight();
    }
    public Knight getKnight(){
        return this.knight;
    }
    public void createCleric(){
        this.cleric = new Cleric();
    }
    public Cleric getCleric(){
        return this.cleric;
    }
    public void createMage(){
        this.mage = new Mage();
    }
    public Mage getMage(){
        return this.mage;
    }
    public void createGoblin(){
        this.goblin = new Goblin();
    }
    public Goblin getGoblin(){
        return this.goblin;
    }
    public void createOrc(){
        this.orc = new Orc();
    }
    public Orc getOrc(){
        return this.orc;
    }
    public void createSorcerer(){
        this.sorcerer = new Sorcerer();
    }
    public Sorcerer getSorcerer(){
        return this.sorcerer;
    }
    public void addToParty(Character character){
        this.party.add(character);
    }
    public void removeFromParty(Character character){
        this.party.remove(character);
    }
    public ArrayList<Character> getParty(){
        return this.party;
    }
    public void addToEnemys(Character character){
        this.enemies.add(character);
    }
    public void removeFromEnemys(Character character){
        this.enemies.remove(character);
    }
    public ArrayList<Character> getEnemies(){
        return this.enemies;
    }
    public void addToTurn(Character character){
        this.turnOrder.add(character);
    }
    public void removeFromTurnOrder(Character character){
        this.turnOrder.remove(character);
    }
    public ArrayList<Character> getTurnOrder(){
        return this.turnOrder;
    }
    public void clearTurnOrder(){
        this.turnOrder.clear();
    }
    private Set<String> achievements = new LinkedHashSet<>();

    public void unlockAchievement(String achievement) {
        achievements.add(achievement);
    }
    public List<String> getAchievements() {
        return new ArrayList<>(achievements);
    }
}
