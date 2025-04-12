package com.example.dandd_game;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws IOException;
}
