package com.example.demo.Level;

import com.example.demo.Image.GameOverImage;
import com.example.demo.Image.WinImage;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Enemy.EnemyPlane;
import com.example.demo.Actor.Enemy.ElitePlane;
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
 * The LevelTwo class represents the second level of the game, inheriting from {@link LevelParent}.
 * This level involves enemy units (both regular and elite planes), tracking the player's kills, and
 * checking for win/loss conditions. It also provides functionality for showing win and game over screens
 * upon the completion of the level.
 *
 * Level Two specifics:
 * <ul>
 *     <li>Total Enemies: 5</li>
 *     <li>Kills required to advance: 27</li>
 *     <li>Enemy spawn probability: 20%</li>
 *     <li>Player initial health: 4</li>
 * </ul>
 *
 * The class handles:
 * <ul>
 *     <li>Spawning enemies: Includes both regular {@link EnemyPlane} and elite {@link ElitePlane} units.</li>
 *     <li>Checking game over conditions: The game is lost if the player is destroyed, or won if the kill target is reached.</li>
 *     <li>Displaying UI for win/loss screens: Shows corresponding win or game over images with options to either restart or return to the main menu.</li>
 * </ul>
 *
 * Methods include:
 * <ul>
 *     <li>{@link #checkIfGameOver()} - Checks if the player has won or lost the level.</li>
 *     <li>{@link #initializeFriendlyUnits()} - Adds the player's plane to the screen.</li>
 *     <li>{@link #spawnEnemyUnits()} - Spawns enemy units at random intervals based on the spawn probability.</li>
 *     <li>{@link #winGame()} - Displays the win screen if the player meets the kill target.</li>
 *     <li>{@link #loseGame()} - Displays the game over screen if the player is destroyed.</li>
 *     <li>{@link #displayWinScreen(double, double)} - Creates and displays the win screen with options to continue or return to the main menu.</li>
 *     <li>{@link #displayGameOverScreen(double, double)} - Creates and displays the game over screen with options to restart or return to the main menu.</li>
 * </ul>
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.png";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 27;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	private static final int PLAYER_INITIAL_HEALTH = 4;

	private final double screenHeight;
	private final double screenWidth;

	/**
	 * Constructs a new LevelTwo instance with the specified screen height, screen width, stage, and media player.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth the width of the game screen.
	 * @param stage the current game stage.
	 * @param mediaPlayer the media player to handle audio.
	 */
	public LevelTwo(double screenHeight, double screenWidth, Stage stage, MediaPlayer mediaPlayer) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage, mediaPlayer);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	/**
	 * Checks if the game is over. The game is over if the player is destroyed or if the player has reached
	 * the required number of kills to advance to the next level.
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
	 * Initializes the friendly units (i.e., the player's plane) in the game.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units at random positions on the screen. The type of enemy is determined randomly,
	 * with a 40% chance of spawning an elite plane and a 60% chance of spawning a regular enemy plane.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy;

				if (Math.random() < 0.4) {
					newEnemy = new ElitePlane(getScreenWidth(), newEnemyInitialYPosition);
				} else {
					newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				}

				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates the view for this level, setting the player's initial health.
	 *
	 * @return the {@link LevelView} for this level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the player has reached the target number of kills needed to advance to the next level.
	 *
	 * @return true if the player has killed the required number of enemies, otherwise false.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	/**
	 * Handles the win scenario. Sets the level as completed and displays the win screen.
	 */
	@Override
	protected void winGame() {
		super.winGame();
		GameState.getInstance().setLevelTwoCompleted(true);
		displayWinScreen(screenHeight, screenWidth);
		levelComplete(); // Notify that the level is complete
	}

	/**
	 * Handles the loss scenario and displays the game over screen.
	 */
	@Override
	protected void loseGame() {
		super.loseGame();
		displayGameOverScreen(screenHeight, screenWidth);
	}

	/**
	 * Displays the win screen with a dark overlay, a win image, and buttons for returning to the main menu
	 * or advancing to the next level.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
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
	 * Displays the game over screen with a dark overlay, a game over image, and buttons for restarting the game
	 * or returning to the main menu.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
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
	 * Returns the player to the main menu.
	 */
	private void returnToMainMenu() {
		LevelSelection levelSelection = new LevelSelection(stage, mediaPlayer);
		levelSelection.show();
	}

	/**
	 * Starts the next level of the game, transitioning to {@link LevelThree}.
	 */
	private void startNextLevel() {
		LevelThree levelThree = new LevelThree(screenHeight, screenWidth, stage, mediaPlayer);
		Scene scene = levelThree.initializeScene();
		Stage stage = (Stage) getRoot().getScene().getWindow();
		stage.setScene(scene);
		levelThree.startGame();
	}

	/**
	 * Restarts the current level (Level Two).
	 */
	private void restartGame() {
		LevelTwo levelTwo = new LevelTwo(screenHeight, screenWidth, stage, mediaPlayer);
		Scene scene = levelTwo.initializeScene();
		Stage stage = (Stage) getRoot().getScene().getWindow();
		stage.setScene(scene);
		levelTwo.startGame();
	}

	/**
	 * Creates a styled button with the specified text and event handler.
	 *
	 * @param text the text to display on the button.
	 * @param eventHandler the event handler to attach to the button.
	 * @return the created styled button.
	 */
	private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
		Button button = new Button(text);
		styleButton(button);
		button.setOnAction(eventHandler);
		return button;
	}

	/**
	 * Styles the given button with custom styles.
	 *
	 * @param button the button to style.
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
