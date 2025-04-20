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
    private Integer playerCount;
    private String difficulty;
    private String campaignName;
    private King king;
    private Knight knight;
    private Cleric cleric;
    private Mage mage;
    private ArrayList<Character> goblins = new ArrayList<Character>();
    private ArrayList<Character> orcs = new ArrayList<Character>();

    private Sorcerer sorcerer;
    private Character currentCharacter;
    private int moveCount = 5;
    private String currentScene;
    private String currentUserEmail;
    private int currentSlot;
    private ArrayList<Character> party = new ArrayList<Character>();
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private ArrayList<Character> turnOrder = new ArrayList<Character>();

    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
    public void resetInstance() {
        playerCount = null;
        difficulty = null;
        campaignName = null;
        king = null;
        knight = null;
        cleric = null;
        mage = null;
        resetList(goblins);
        resetList(orcs);
        sorcerer = null;
        currentCharacter = null;
        moveCount = 5;
        resetList(party);
        resetList(enemies);
        resetList(turnOrder);
    }
    public void resetList(ArrayList<Character> list){
        for(int i = list.size() - 1; i >= 0; i--){
            list.remove(i);
        }
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
    public void setCurrentScene(String scene) {
        this.currentScene = scene;
    }
    public String getCurrentScene() {
        return this.currentScene;
    }
    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
    }
    public String getCurrentUserEmail() {
        return this.currentUserEmail;
    }
    public void setCurrentSlot(int slot) {
        this.currentSlot = slot;
    }
    public int getCurrentSlot() {
        return currentSlot;
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
        goblins.add(new Goblin());
    }
    public ArrayList<Character> getGoblins(){
        return this.goblins;
    }
    public void createOrc(){
        orcs.add(new Orc());}

    public ArrayList<Character> getOrcs(){
        return this.orcs;
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
    public void nextTurn(){
        this.turnOrder.add(this.turnOrder.getFirst());
        this.turnOrder.remove(0);
        setCurrentCharacter(this.turnOrder.get(0));
        resetMoveCount();
    }
    private Set<String> achievements = new LinkedHashSet<>();

    public boolean unlockAchievement(String achievement) {
        return achievements.add(achievement);
    }
    public void setAchievements(List<String> achievementsList) {
        this.achievements.clear();
        this.achievements.addAll(achievementsList);
    }
    public List<String> getAchievements() {
        return new ArrayList<>(achievements);
    }
    private String pendingAchievement = null;
    public void queueAchievementPopup(String achievement) {
        pendingAchievement = achievement;
    }
    public String getPendingAchievement() {
        String temp = pendingAchievement;
        pendingAchievement = null;
        return temp;
    }
    public int getMoveCount() {
        return this.moveCount;
    }
    public void decreaseMoveCount(){
        this.moveCount--;
    }
    public void resetMoveCount(){
        this.moveCount = 5;
    }
    public boolean nameExists(String name) {
        for (Character c : party) {
            if (c == getCurrentCharacter()) continue;
            if (c.getName() != null && c.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }
}
