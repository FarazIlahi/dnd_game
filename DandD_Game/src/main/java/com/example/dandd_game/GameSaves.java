package com.example.dandd_game;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
// import all character classes
import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.Characters.Cleric;
import com.example.dandd_game.Characters.Goblin;
import com.example.dandd_game.Characters.King;
import com.example.dandd_game.Characters.Knight;
import com.example.dandd_game.Characters.Mage;
import com.example.dandd_game.Characters.Orc;
import com.example.dandd_game.Characters.Sorcerer;
import com.example.dandd_game.Position;
import com.google.cloud.firestore.SetOptions;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;



import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameSaves {

    public static void saveAchievements() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String email = GameStateManager.getInstance().getCurrentUserEmail();
        List<String> achievements = GameStateManager.getInstance().getAchievements();

        Map<String, Object> data = new HashMap<>();
        data.put("achievements", achievements);
        db.collection("saves").document(email).set(data, SetOptions.merge()).get();
    }

    public static void saveGame(int slot) throws ExecutionException, InterruptedException {
        GameStateManager gsm = GameStateManager.getInstance();
        String userEmail = gsm.getCurrentUserEmail();
        String currentScene = gsm.getCurrentScene();

        Map<String, Object> saveData = getGameStateForFirebase(currentScene);
        Firestore db = FirestoreClient.getFirestore();
        db.collection("saves").document(userEmail).collection("slots").document("slot" +slot).set(saveData).get();
        System.out.println("Game Saved " + currentScene +" email " + userEmail + "in slot " + slot);
    }
    public static void saveGame() throws ExecutionException, InterruptedException {
        saveGame(getSelectedSlot());
    }


    public static void loadGame(int slot)throws ExecutionException, InterruptedException {
        GameStateManager gsm = GameStateManager.getInstance();
        String userEmail = gsm.getCurrentUserEmail();
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot snapshot = db.collection("saves").document(userEmail).collection("slots").document("slot" + slot).get().get();

        if (snapshot.exists()) {
            Map<String, Object> saveData = snapshot.getData();
            if (saveData != null) {
                loadIntoGameState(saveData);
                System.out.println("Game Loaded from slot " + slot);
            }
        } else {
            System.out.println("No save data in slot " + slot);
        }
    }

    public static void loadGame() throws ExecutionException, InterruptedException {
        loadGame(getSelectedSlot());
    }

    private static Map<String, Object> getGameStateForFirebase(String currentScene) {
        GameStateManager gsm = GameStateManager.getInstance();
        Map<String, Object> data = new HashMap<>();

        data.put("playerCount", gsm.getPlayerCount());
        data.put("difficulty", gsm.getDifficulty());
        data.put("campaignName", gsm.getCampaignName());
        data.put("currentScene", currentScene);
        data.put("currentCharacter", gsm.getCurrentCharacter() != null ? gsm.getCurrentCharacter().getName() : null);
        data.put("party", serializeCharacterList(gsm.getParty()));
        data.put("enemies", serializeCharacterList(gsm.getEnemies()));


        System.out.println("Saving turn Order:");
        for (Character c : gsm.getTurnOrder()) {
            System.out.println(c.getName());
        }

        List<String> turnOrderNames = new ArrayList<>();
        for (Character c : gsm.getTurnOrder()) {
            turnOrderNames.add(c.getName());
        }
        data.put("turnOrder", turnOrderNames);
        return data;
    }

    private static List<Map<String, Object>> serializeCharacterList(List<Character> list) {
        List<Map<String, Object>> serialized = new ArrayList<>();
        for (Character c : list) {
            Map<String, Object> charData = new HashMap<>();
            charData.put("type", c.getID());
            charData.put("name", c.getName());
            charData.put("hp", c.getHp());
            charData.put("basic_attack", c.getBasic_attack());
            charData.put("defense", c.getDef());
            charData.put("range", c.getRange());
            charData.put("special", c.getSpecial());
            charData.put("cost", c.getSpecialCost());
            charData.put("x", c.getPosition().getX());
            charData.put("y", c.getPosition().getY());
            serialized.add(charData);
        }
        return serialized;
    }

    private static Character createCharacterByType(String type, String name, int hp, int attack, int defense, int range, int special, int cost, int x, int y) {
        Position defaultPosition = new Position(x, y);

        return switch (type) {
            case "King" -> new King(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Knight" -> new Knight(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Cleric" -> new Cleric(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Mage" -> new Mage(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Goblin" -> new Goblin(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Orc" -> new Orc(hp, defense, attack, range, name, defaultPosition, special, cost);
            case "Sorcerer" -> new Sorcerer(hp, defense, attack, range, name, defaultPosition, special, cost);
            default -> null;
        };

    }

    private static int safeGetInt(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return 0;
        }
    }

    private static int selectedSlot = 1;

    public static void setSelectedSlot(int slot) {
        selectedSlot = slot;
    }

    public static int getSelectedSlot() {
        return selectedSlot;
    }

    private static void loadCharacterList(List<Map<String, Object>> rawList, List<Character> targetList) {
        GameStateManager gsm = GameStateManager.getInstance();
        for (Map<String, Object> charData : rawList) {
            String type = (String) charData.getOrDefault("type", "Unknown");
            String name = (String) charData.getOrDefault("name", "Unnamed");
            int hp = safeGetInt(charData, "hp");
            int attack = safeGetInt(charData, "basic_attack");
            int defense = safeGetInt(charData, "def");
            int range = safeGetInt(charData, "range");
            int special = safeGetInt(charData, "special");
            int cost = safeGetInt(charData, "cost");
            int x = safeGetInt(charData, "x");
            int y = safeGetInt(charData, "y");

            System.out.println("Loading character: " + type + " " + name);

            Character c = createCharacterByType(type, name, hp, attack, defense, range, special, cost, x, y);
            if (c != null) {
                String imageKey = type;
                Image img = LocalImages.getInstance().getImage(imageKey);
                if (img == null) {
                    System.out.println("Image not found for: " + imageKey);
                }
                ImageView profile = new ImageView(img);
                profile.setFitWidth(40);
                profile.setFitHeight(40);
                c.setProfile(profile);
                targetList.add(c);

                switch (type) {
                    case "King" -> gsm.setKing((King) c);
                    case "Knight" -> gsm.setKnight((Knight) c);
                    case "Cleric" -> gsm.setCleric((Cleric) c);
                    case "Mage" -> gsm.setMage((Mage) c);
                }

                if (type.equals("Goblin") || type.equals("Orc") || type.equals("Sorcerer")) {
                    gsm.getEnemies().add(c);
                }
            }
            else {
                System.out.println("Failed to create character: " + type + " " + name);
            }
        }
    }



    private static void loadIntoGameState(Map<String, Object> data) {
        GameStateManager gsm = GameStateManager.getInstance();
        gsm.resetInstance();
        // load main game info
        gsm.setPlayerCount(((Long) data.get("playerCount")).intValue());
        gsm.setDifficulty((String) data.get("difficulty"));
        gsm.setCampaignName((String) data.get("campaignName"));
        gsm.resetMoveCount();
        // load achievements
        List<String> achievements = (List<String>) data.getOrDefault("achievements", new ArrayList<>());
        gsm.getAchievements().addAll(achievements);

        // load characters
        List<Map<String, Object>> partyData = (List<Map<String, Object>>) data.get("party");
        if (partyData != null) loadCharacterList(partyData, gsm.getParty());

        List<Map<String, Object>> enemiesData = (List<Map<String, Object>>) data.get("enemies");
        if (enemiesData != null) loadCharacterList(enemiesData, gsm.getEnemies());

        List<String> turnOrderNames = (List<String>) data.get("turnOrder");
        if (turnOrderNames != null) {
            List<Character> correctOrder = new ArrayList<>();
            for (String name : turnOrderNames) {
                Character found = null;
                for (Character partyChar : gsm.getParty()) {
                    if (partyChar.getName().equals(name)) {
                        found = partyChar;
                        break;
                    }
                }
                if (found == null) {
                    for (Character enemyChar : gsm.getEnemies()) {
                        if (enemyChar.getName().equals(name)) {
                            found = enemyChar;
                            break;
                        }
                    }
                }
                if (found != null) {
                    correctOrder.add(found);
                } else {
                    System.out.println("Failed to find character in turn order: " + name);
                }
            }
            gsm.getTurnOrder().clear();
            gsm.getTurnOrder().addAll(correctOrder);
        }


        if (gsm.getTurnOrder().isEmpty()) {
            System.out.println("Warning: Turn order failed to load correctly.");
        }


        String currentCharName = (String) data.get("currentCharacter");
        for (Character c : gsm.getTurnOrder()) {
            if (c.getName().equals(currentCharName)) {
                gsm.setCurrentCharacter(c);
                break;
            }
        }

        String currentScene = (String) data.get("currentScene");
        gsm.setCurrentScene(currentScene);
    }


}
