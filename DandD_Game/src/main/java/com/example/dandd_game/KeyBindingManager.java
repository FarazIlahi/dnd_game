package com.example.dandd_game;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyBindingManager {
    private final Map<KeyCode, ThrowingRunnable> keyBindings = new HashMap<>();
    private final Pane root;

    public KeyBindingManager(Pane root) {
        this.root = root;

        root.setOnKeyPressed(this::handleKeyPress);
    }

    public void addKeyBinding(String key, ThrowingRunnable method) {
        KeyCode keyCode = KeyCode.valueOf(key.toUpperCase());
        keyBindings.put(keyCode, method);
    }

    public void removeKeyBinding(String key) {
        KeyCode keyCode = KeyCode.valueOf(key.toUpperCase());
        keyBindings.remove(keyCode);
    }

    public void clearAllBindings() {
        keyBindings.clear();
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (keyBindings.containsKey(code)) {
            try {
                keyBindings.get(code).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
