package com.example.demo.Level;

public class GameState {
    private static GameState instance;
    private boolean levelOneCompleted;
    private boolean levelTwoCompleted;

    private GameState() {
        levelOneCompleted = false;
        levelTwoCompleted = false;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public boolean isLevelOneCompleted() {
        return levelOneCompleted;
    }

    public void setLevelOneCompleted(boolean levelOneCompleted) {
        this.levelOneCompleted = levelOneCompleted;
    }

    public boolean isLevelTwoCompleted() {
        return levelTwoCompleted;
    }

    public void setLevelTwoCompleted(boolean levelTwoCompleted) {
        this.levelTwoCompleted = levelTwoCompleted;
    }

    public void reset() {
        levelOneCompleted = false;
        levelTwoCompleted = false;
    }
}