package com.example.dandd_game;

public class Position {
    private int x;
    private int y;
    private Position lastPosition;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setLastPosition(Position pos) {
        this.lastPosition = pos;
    }
    public Position getLastPosition() {
        return lastPosition;
    }
}
