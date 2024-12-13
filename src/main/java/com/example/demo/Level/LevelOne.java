package com.example.demo.Level;

import com.example.demo.Image.GameOverImage;
import com.example.demo.Image.WinImage;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Enemy.EnemyPlane;
import com.example.demo.Screen.LevelSelection;
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
 * Represents the first level in the game.
 * This class is responsible for managing the game logic, spawning enemies,
 * handling the player's progress, and displaying the win or loss screen.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.png";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 25;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	private static final int PLAYER_INITIAL_HEALTH = 3;

	private final double screenHeight;
	private final double screenWidth;

	/**
	 * Constructs a LevelOne object with the specified screen dimensions, stage, and media player.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
	 * @param stage the JavaFX stage
	 * @param mediaPlayer the media player for background music
	 */
	public LevelOne(double screenHeight, double screenWidth, Stage stage, MediaPlayer mediaPlayer) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage, mediaPlayer);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	/**
	 * Checks if the game is over by checking the player's destruction status
	 * or whether the player has reached the target number of kills.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			winGame();
		}
	}

	/**
	 * Initializes the player's units and adds them to the game root.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units based on a predefined spawn probability.
	 * It ensures the total number of enemies doesn't exceed the limit.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(screenWidth, newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Creates and returns the view for the level, including UI elements for health.
	 *
	 * @return a LevelView object for the level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the player has achieved the required number of kills to advance to the next level.
	 *
	 * @return true if the player has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Handles the actions that occur when the player wins the level.
	 * It updates the game state and displays the win screen.
	 */
	@Override
	protected void winGame() {
		super.winGame();
		GameState.getInstance().setLevelOneCompleted(true);
		displayWinScreen(screenHeight, screenWidth);
		levelComplete(); // Notify that the level is complete
	}

	/**
	 * Handles the actions that occur when the player loses the level.
	 * It displays the game over screen.
	 */
	@Override
	protected void loseGame() {
		super.loseGame();
		displayGameOverScreen(screenHeight, screenWidth);
	}

	/**
	 * Displays the win screen with a win image, overlay, and navigation buttons.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
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
		Button nextLevelButton = createStyledButton("Next Level ♪", _ -> startNextLevel());

		// Layout for buttons
		HBox buttonLayout = new HBox(20, mainMenuButton, nextLevelButton);
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
	 * Displays the game over screen with a game over image, overlay, and navigation buttons.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth the width of the screen
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
	 * Returns to the main menu by transitioning to the LevelSelection screen.
	 */
	private void returnToMainMenu() {
		LevelSelection levelSelection = new LevelSelection(stage, mediaPlayer);
		levelSelection.show();
	}

	/**
	 * Starts the next level, transitioning to LevelTwo.
	 */
	private void startNextLevel() {
		LevelTwo levelTwo = new LevelTwo(screenHeight, screenWidth, stage, mediaPlayer);
		Scene scene = levelTwo.initializeScene();
		Stage stage = (Stage) getRoot().getScene().getWindow();
		stage.setScene(scene);
		levelTwo.startGame();
	}

	/**
	 * Restarts the current game by re-initializing LevelOne.
	 */
	private void restartGame() {
		LevelOne levelOne = new LevelOne(screenHeight, screenWidth, stage, mediaPlayer);
		Scene scene = levelOne.initializeScene();
		Stage stage = (Stage) getRoot().getScene().getWindow();
		stage.setScene(scene);
		levelOne.startGame();
	}

	/**
	 * Creates a styled button with the given text and event handler.
	 *
	 * @param text the text to display on the button
	 * @param eventHandler the event handler to handle button clicks
	 * @return the styled button
	 */
	private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
		Button button = new Button(text);
		styleButton(button);
		button.setOnAction(eventHandler);
		return button;
	}

	/**
	 * Styles the button with specific size and colors, and adds hover effects.
	 *
	 * @param button the button to be styled
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
