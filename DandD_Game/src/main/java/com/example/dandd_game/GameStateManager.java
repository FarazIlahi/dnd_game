package com.example.dandd_game;

import com.example.dandd_game.Characters.*;
import com.example.dandd_game.Characters.Character;
import com.google.cloud.firestore.Firestore;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import java.util.HashMap;
import java.util.Map;

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
    private Goblin goblin;
    private Orc orc;
    private Imp imp;
    private Skeleton skeleton;
    private Zombie zombie;
    private Sorcerer sorcerer;
    private Character currentCharacter;
    private int moveCount = 5;
    private String currentScene;
    private String currentUserEmail;
    private int currentSlot;
    private String previousScene;
    private String nextScene;
    private ArrayList<Character> party = new ArrayList<Character>();
    private ArrayList<Character> enemies = new ArrayList<Character>();
    private ArrayList<Character> turnOrder = new ArrayList<Character>();
    private String upKey = "W";
    private String downKey = "S";
    private String leftKey = "A";
    private String rightKey = "D";

    private AudioInputStream soundFXInput;

    private int totalDamageDone = 0;
    private int enemiesKilled = 0;
    private int rollCompleted = 0;
    private int randomEventTriggered = 0;
    private boolean gameCompleted = false;


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
        goblin = null;
        orc = null;
        sorcerer = null;
        imp = null;
        skeleton = null;
        zombie = null;
        currentCharacter = null;
        moveCount = 5;
        resetList(party);
        resetList(enemies);
        resetList(turnOrder);
        upKey = "W";
        downKey = "S";
        leftKey = "A";
        rightKey = "D";

        totalDamageDone = 0;
        enemiesKilled = 0;
        rollCompleted = 0;
        randomEventTriggered = 0;
        gameCompleted = false;
    }

    public void resetList(ArrayList<?> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
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
        double multi = getDifficultyMultiplier();
        Goblin base = new Goblin();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.goblin = new Goblin(hp, def, atk, range, "Goblin", new Position(2, 4), 0, 0);
    }
    public Goblin getGoblin(){
        return this.goblin;
    }
    public void createOrc(){
        double multi = getDifficultyMultiplier();
        Orc base = new Orc();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.orc = new Orc(hp, def, atk, range, "Orc", new Position(2, 4), 0, 0);
    }
    public Orc getOrc(){
        return this.orc;
    }
    public void createSorcerer(){
        double multi = getDifficultyMultiplier();
        Sorcerer base = new Sorcerer();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.sorcerer = new Sorcerer(hp, def, atk, range, "Sorcerer", new Position(2, 4), 0, 0);
    }
    public Sorcerer getSorcerer(){
        return this.sorcerer;
    }
    public void createImp(){
        double multi = getDifficultyMultiplier();
        Imp base = new Imp();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.imp = new Imp(hp, def, atk, range, "Imp", new Position(2, 4), 0, 0);
    }
    public Imp getImp(){
        return this.imp;
    }
    public void createSkeleton(){
        double multi = getDifficultyMultiplier();
        Skeleton base = new Skeleton();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.skeleton = new Skeleton(hp, def, atk, range, "Skeleton", new Position(2, 4), 0, 0);
    }
    public Skeleton getSkeleton(){
        return this.skeleton;
    }
    public void createZombie(){
        double multi = getDifficultyMultiplier();
        Zombie base = new Zombie();
        int hp = (int)(base.getHp() * multi);
        int def = (int)(base.getDef() * multi);
        int atk = (int)(base.getBasic_attack() * multi);
        int range = base.getRange();
        this.zombie = new Zombie(hp, def, atk, range, "Zombie", new Position(2, 4), 0, 0);
    }
    public Zombie getZombie(){
        return this.zombie;
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
    public void playSoundFX(String sfxFile) {
        try {
            soundFXInput = AudioSystem.getAudioInputStream(new File(sfxFile));
            Clip soundFX = AudioSystem.getClip();
            soundFX.open(soundFXInput);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: File not supported");
        } catch (LineUnavailableException e) {
            System.out.println("Error: File unavailable");
        } catch (IOException e) {
            System.out.println("Error: File not found");
        }
    }

    private Set<String> achievements = new LinkedHashSet<>();

    public boolean unlockAchievement(String achievement) {
        boolean newAchievement = achievements.add(achievement);
        if (newAchievement) {
            try {
                GameSaves.saveAchievements();
            } catch (Exception e) {
                System.out.println("achievement failed save");
            }
        }
        return newAchievement;
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


    // Game Report Stat Tracking
    public void addDamage(int damage) {totalDamageDone += damage; }
    public void incrementEnemiesKilled() {enemiesKilled ++; }
    public void incrementRollsCompleted(){rollCompleted++; }
    public void incrementRandomEventsTriggered(){randomEventTriggered++; }
    public void setGameMechanics(boolean completed) {this.gameCompleted = completed; }

    public int getPlayerSurvived(){
        int count = 0;
        for (Character c : this.party) {
            if(c.getHp() > 0) count++;
        }
        return count;
    }

    public void EndGameReport(){
        Firestore db = FirebaseConfig.initialize();
        GameReport report = new GameReport(
          getPlayerSurvived(),
          enemiesKilled,
          totalDamageDone,
          rollCompleted,
          gameCompleted,
          randomEventTriggered
        );

        db.collection("GameReport")
                .add(report)
                .addListener(() -> System.out.println("Report uploaded"), Runnable::run);
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

    public String getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(String previousScene) {
        this.previousScene = previousScene;
    }

    public void resetEnemies() {
        resetList(enemies);
    }

    public void setNextScene(String nextScene) {
        this.nextScene = nextScene;
    }

    public String getNextScene() {
        return nextScene;
    }

    public void setKing(King king) {
        this.king = king;
    }

    public void setKnight(Knight knight) {
        this.knight = knight;
    }

    public void setCleric(Cleric cleric) {
        this.cleric = cleric;
    }

    public void setMage(Mage mage) {
        this.mage = mage;
    }

    public void setUpKey(String upKey) {
        this.upKey = upKey;
    }
    public String getUpKey() {
        return upKey;
    }

    public void setDownKey(String downKey) {
        this.downKey = downKey;
    }
    public String getDownKey() {
        return downKey;
    }

    public void setLeftKey(String leftKey) {
        this.leftKey = leftKey;
    }
    public String getLeftKey() {
        return leftKey;
    }

    public void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }
    public String getRightKey() {
        return rightKey;
    }
    public List<Character> getAllCharacters() {
        Set<Character> all = new LinkedHashSet<>();
        all.addAll(party);
        all.addAll(enemies);
        all.addAll(turnOrder);
        return new ArrayList<>(all);
    }
    public void resetAllCharacterPositions() {
        for (Character c : getAllCharacters()) {
            c.setPosition(null);
        }
    }

    public Map<String, String> getKeybindsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("up", upKey);
        map.put("down", downKey);
        map.put("left", leftKey);
        map.put("right", rightKey);
        return map;
    }

    public void setKeybindsFromMap(Map<String, String> map) {
        if (map.containsKey("up")) setUpKey(map.get("up"));
        if (map.containsKey("down")) setDownKey(map.get("down"));
        if (map.containsKey("left")) setLeftKey(map.get("left"));
        if (map.containsKey("right")) setRightKey(map.get("right"));
    }
    public double getDifficultyMultiplier() {
        switch(difficulty) {
            case "Easy":
                return 0.5;
            case "Hard":
                return 1.5;
            default:
                return 1.0;
        }
    }

}
