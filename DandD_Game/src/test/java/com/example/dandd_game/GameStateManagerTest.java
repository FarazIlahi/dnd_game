package com.example.dandd_game;


import com.example.dandd_game.Characters.Character;
import com.example.dandd_game.Characters.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameStateManagerTest {
    private GameStateManager manager;

    @BeforeEach
    void setUp() {
        manager = GameStateManager.getInstance();
        manager.resetInstance();
    }

    @Test
    void testGetInstance() {
        GameStateManager anotherInstance = GameStateManager.getInstance();
        assertSame(manager, anotherInstance);
    }

    @Test
    void testSetAndGetPlayerCount() {
        manager.setPlayerCount(1);
        assertEquals(1, manager.getPlayerCount());
    }

    @Test
    void testSetAndGetDifficulty() {
        manager.setDifficulty("easy");
        assertEquals("easy", manager.getDifficulty());
    }

    @Test
    void testSetAndGetCampaignName() {
        manager.setCampaignName("test");
        assertEquals("test", manager.getCampaignName());
    }

    @Test
    void testAddToParty() {
        Character knight = new Knight();
        manager.addToParty(knight);
        assertTrue(manager.getParty().contains(knight));
    }
    @Test
    void testRemoveFromParty() {
        Character knight = new Knight();
        manager.addToParty(knight);
        manager.removeFromParty(knight);
        assertFalse(manager.getParty().contains(knight));
    }
}