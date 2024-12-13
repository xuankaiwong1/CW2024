package com.example.demo.Level;

import com.example.demo.Image.GameOverImage;
import com.example.demo.Image.WinImage;
import com.example.demo.Actor.Enemy.Boss;
import com.example.demo.Screen.MainMenu; // Add this import statement
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.media.MediaPlayer;

/**
 * Represents Level Three of the game, extending the LevelParent class.
 * This level introduces a boss enemy and implements the logic for winning and losing the game.
 */
public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;

    private final double screenHeight;
    private final double screenWidth;

    /**
     * Constructor for LevelThree. Initializes the level with specified screen size, stage, and media player.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     * @param stage The JavaFX stage for displaying the scene.
     * @param mediaPlayer The media player for background music.
     */
    public LevelThree(double screenHeight, double screenWidth, Stage stage, MediaPlayer mediaPlayer) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage, mediaPlayer);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        boss = new Boss();
    }

    /**
     * Initializes the friendly units, adding the user (player) to the root node.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over. If the player is destroyed, the game is lost.
     * If the boss is destroyed, the game is won.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Spawns the enemy units. If there are no enemies, the boss is added to the level.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            getRoot().getChildren().add(boss.getShieldImage());
        }
    }

    /**
     * Instantiates and returns the level view for Level Three.
     *
     * @return A LevelView object for Level Three.
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Handles winning the game by displaying the win screen and completing the level.
     */
    @Override
    protected void winGame() {
        super.winGame();
        displayWinScreen(screenHeight, screenWidth);
        levelComplete(); // Notify that the level is complete
    }

    /**
     * Handles losing the game by displaying the game over screen.
     */
    @Override
    protected void loseGame() {
        super.loseGame();
        displayGameOverScreen(screenHeight, screenWidth);
    }

    /**
     * Displays the win screen with a win image and a button to return to the main menu.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    private void displayWinScreen(double screenHeight, double screenWidth) {
        // Create a dark overlay
        Rectangle overlay = new Rectangle(screenWidth, screenHeight);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.7);

        // Create a "You Win" image
        WinImage winImage = new WinImage(screenWidth / 2 - 300, screenHeight / 2 - 250);
        winImage.showWinImage();

        // Create buttons
        Button mainMenuButton = createStyledButton("Return to Main Menu", _ -> returnToMainMenu());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, mainMenuButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a VBox to hold the win image and buttons
        VBox layout = new VBox(-50, winImage, buttonLayout);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a stack pane to overlay the win image and buttons
        StackPane winPane = new StackPane();
        winPane.getChildren().addAll(overlay, layout);
        StackPane.setAlignment(layout, javafx.geometry.Pos.CENTER);

        getRoot().getChildren().add(winPane);
    }

    /**
     * Displays the game over screen with a "Game Over" image and buttons to return to the main menu or restart the game.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    private void displayGameOverScreen(double screenHeight, double screenWidth) {
        // Create a dark overlay
        Rectangle overlay = new Rectangle(screenWidth, screenHeight);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.7);

        // Create a "Game Over" image
        GameOverImage gameOverImage = new GameOverImage(screenWidth / 2 - 350, screenHeight / 2 - 300);

        // Create buttons
        Button mainMenuButton = createStyledButton("Return to Main Menu", _ -> returnToMainMenu());
        Button restartButton = createStyledButton("Restart Game ♪", _ -> restartGame());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, mainMenuButton, restartButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a VBox to hold the game over image and buttons
        VBox layout = new VBox(-50, gameOverImage, buttonLayout);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a stack pane to overlay the game over image and buttons
        StackPane gameOverPane = new StackPane();
        gameOverPane.getChildren().addAll(overlay, layout);
        StackPane.setAlignment(layout, javafx.geometry.Pos.CENTER);

        getRoot().getChildren().add(gameOverPane);
    }

    /**
     * Returns to the main menu by starting the MainMenu screen.
     */
    private void returnToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(stage);
    }

    /**
     * Restarts the game by creating a new instance of LevelThree and setting the scene.
     */
    private void restartGame() {
        LevelThree levelThree = new LevelThree(screenHeight, screenWidth, stage, mediaPlayer);
        Scene scene = levelThree.initializeScene();
        stage.setScene(scene);
        levelThree.startGame();
    }

    /**
     * Creates and styles a button with the given text and event handler.
     *
     * @param text The text to display on the button.
     * @param eventHandler The event handler to attach to the button.
     * @return A styled Button object.
     */
    private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        styleButton(button);
        button.setOnAction(eventHandler);
        return button;
    }

    /**
     * Applies style to a button, including size, color, and hover effects.
     *
     * @param button The button to style.
     */
    private void styleButton(Button button) {
        button.setPrefSize(200, 50);
        button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #ff69b4; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }
}
