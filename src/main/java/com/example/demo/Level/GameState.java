package com.example.demo.Level;

/**
 * The GameState class is a singleton that tracks the completion status of the game levels.
 * It ensures that the state of the levels is consistent across different parts of the game.
 * The class is used to check and set whether levels have been completed.
 */
public class GameState {
    private static GameState instance;
    private boolean levelOneCompleted;
    private boolean levelTwoCompleted;

    /**
     * Private constructor to prevent instantiation outside of the class.
     * Initializes the completion status of both levels to false.
     */
    private GameState() {
        levelOneCompleted = false;
        levelTwoCompleted = false;
    }

    /**
     * Gets the singleton instance of the GameState.
     * If the instance does not exist, it is created.
     *
     * @return The single instance of GameState.
     */
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    /**
     * Checks if level one has been completed.
     *
     * @return true if level one is completed, false otherwise.
     */
    public boolean isLevelOneCompleted() {
        return levelOneCompleted;
    }

    /**
     * Sets the completion status of level one.
     *
     * @param levelOneCompleted true if level one is completed, false otherwise.
     */
    public void setLevelOneCompleted(boolean levelOneCompleted) {
        this.levelOneCompleted = levelOneCompleted;
    }

    /**
     * Checks if level two has been completed.
     *
     * @return true if level two is completed, false otherwise.
     */
    public boolean isLevelTwoCompleted() {
        return levelTwoCompleted;
    }

    /**
     * Sets the completion status of level two.
     *
     * @param levelTwoCompleted true if level two is completed, false otherwise.
     */
    public void setLevelTwoCompleted(boolean levelTwoCompleted) {
        this.levelTwoCompleted = levelTwoCompleted;
    }
}
