package com.example.demo.Level;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import com.example.demo.Actor.User.UserPlane;
import com.example.demo.Screen.SettingsScreen;
import com.example.demo.Screen.MainMenu;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * The abstract parent class for managing a game level, including user and enemy actions,
 * projectiles, and collisions. It defines the game loop, handles input, updates actors, and
 * manages the game state, such as pausing and resuming the game. The level background, scene,
 * and actors (both friendly and enemy) are also handled within this class.
 *
 * <p>The class provides the basic structure for a game level, with methods to initialize
 * the game scene, start the game, update the actors, handle collisions, and fire projectiles.</p>
 *
 * <p>Subclasses must implement methods to define the specific behavior of the level, including
 * spawning enemy units, checking for game over conditions, and creating a level view.</p>
 *
 * @see ActiveActorDestructible
 * @see UserPlane
 * @see FighterPlane
 * @see LevelView
 */
public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	protected final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private static final long PROJECTILE_COOLDOWN = 120;

	private int currentNumberOfEnemies;
	private final LevelView levelView;

	private final Set<KeyCode> activeKeys = new HashSet<>();
	private long lastFiredProjectile = 0;
	private boolean isGamePaused;

	protected final Stage stage;
	protected final MediaPlayer mediaPlayer;

	// Pause menu components
	private VBox pauseMenu;
	private Rectangle overlay;
	private StackPane pausePane;

	private Runnable onLevelComplete;

	/**
	 * Constructs a LevelParent object to initialize the level with the specified parameters.
	 * This includes setting up the background, scene, actors (user, friendly units, enemy units),
	 * and game loop. The pause menu and game state are also initialized.
	 *
	 * @param backgroundImageName The name of the background image to be displayed for the level.
	 * @param screenHeight        The height of the game screen.
	 * @param screenWidth         The width of the game screen.
	 * @param playerInitialHealth The initial health of the player.
	 * @param stage               The primary stage for displaying the game scene.
	 * @param mediaPlayer         The media player for playing sound or music during the game.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth,
					   int playerInitialHealth, Stage stage, MediaPlayer mediaPlayer) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		this.stage = stage;
		this.mediaPlayer = mediaPlayer;
		initializeTimeline();
		friendlyUnits.add(user);

		initializePauseMenu();
	}

	/**
	 * Abstract method to initialize the friendly units (e.g., the player's units).
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to check if the game is over, based on the current game state.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units for the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate and return the level view (e.g., the UI components for the level).
	 *
	 * @return The LevelView instance for the current level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene for the level by setting up the background and friendly units.
	 *
	 * @return The Scene object representing the game level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by playing the timeline and setting the game as not paused.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
		isGamePaused = false;
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), _ -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			activeKeys.add(kc);

			if (kc == KeyCode.ESCAPE) pauseGame();
		});
		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			activeKeys.remove(kc);
			if (kc == KeyCode.W || kc == KeyCode.S) user.stopVertical();
			if (kc == KeyCode.A || kc == KeyCode.D) user.stopHorizontal();
		});
		root.getChildren().add(background);
	}

	/**
	 * Handles key press events to move the user plane and fire projectiles.
	 */
	private void handleKeyPress() {
		if (activeKeys.contains(KeyCode.W)) user.moveUp();
		if (activeKeys.contains(KeyCode.S)) user.moveDown();
		if (activeKeys.contains(KeyCode.A)) user.moveLeft();
		if (activeKeys.contains(KeyCode.D)) user.moveRight();
		if (activeKeys.contains(KeyCode.SPACE)) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastFiredProjectile > PROJECTILE_COOLDOWN) {
				fireProjectile();
				lastFiredProjectile = currentTime;
			}
		}
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between two sets of actors (e.g., friendly units and enemy units).
	 * If any two actors intersect, they both take damage.
	 *
	 * @param actors1 The first list of actors (e.g., user projectiles).
	 * @param actors2 The second list of actors (e.g., enemy units).
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	/**
	 * Checks if any enemy unit has penetrated the player's defenses. If so, the player takes damage
	 * and the enemy unit is destroyed.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Updates the level view, such as removing hearts when the player's health decreases.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count based on how many enemies have been destroyed since the last update.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Determines if the given enemy has penetrated the player's defenses (e.g., crossed the screen width).
	 *
	 * @param enemy The enemy unit to check.
	 * @return True if the enemy has crossed the screen's edge; otherwise, false.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Ends the game and stops the game loop, indicating the player has won.
	 */
	protected void winGame() {
		timeline.stop();
	}

	/**
	 * Ends the game and stops the game loop, indicating the player has lost.
	 */
	protected void loseGame() {
		timeline.stop();
	}

	/**
	 * Returns the user plane (the player's main unit).
	 *
	 * @return The user's plane.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Returns the root group that holds all visual elements of the level.
	 *
	 * @return The root group of the level.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Returns the current number of active enemy units in the level.
	 *
	 * @return The number of active enemy units.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds a new enemy unit to the level.
	 *
	 * @param enemy The enemy unit to be added.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Returns the maximum Y position for enemy units on the screen.
	 *
	 * @return The maximum Y position for enemies.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Returns the width of the game screen.
	 *
	 * @return The width of the screen.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user plane has been destroyed.
	 *
	 * @return True if the user plane is destroyed; otherwise, false.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the current number of active enemy units in the level.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Resumes the game from the settings menu, if paused.
	 */
	public void resumeGameFromSettings() {
		isGamePaused = false;
		timeline.play();
	}

	/**
	 * Restarts the game by stopping the timeline, hiding the pause menu, and reinitializing the current level.
	 */
	private void restartGame() {
		timeline.stop();
		hidePauseMenu();

		try {
			// Get the current level class
			Class<? extends LevelParent> currentLevelClass = getCurrentLevelClass();
			// Create a new instance of the current level
			LevelParent currentLevel = currentLevelClass
					.getConstructor(double.class, double.class, Stage.class, MediaPlayer.class)
					.newInstance(screenHeight, screenWidth, stage, mediaPlayer);
			// Initialize and start the current level
			Scene scene = currentLevel.initializeScene();
			stage.setScene(scene);
			currentLevel.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the class of the current level.
	 *
	 * @return The class of the current level.
	 */
	protected Class<? extends LevelParent> getCurrentLevelClass() {
		return this.getClass();
	}

	/**
	 * Quits the game and closes the application.
	 */
	private void quitGame() {
		System.exit(0);
	}

	/**
	 * Displays the settings screen for adjusting game options.
	 */
	private void showSettings() {
		Scene currentScene = stage.getScene(); // Get the current scene
		SettingsScreen settingsScreen = new SettingsScreen(stage, mediaPlayer, currentScene, this); // Pass the current scene and this instance
		settingsScreen.show();
	}

	/**
	 * Returns to the main menu from the current level.
	 */
	private void returnToMainMenu() {
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);
	}

	/**
	 * Initializes the pause menu with buttons for resuming the game, restarting the game,
	 * adjusting settings, returning to the main menu, and quitting the game.
	 */
	private void initializePauseMenu() {
		// Create a semi-transparent black overlay and bind its size to the scene
		overlay = new Rectangle();
		overlay.setFill(Color.rgb(0, 0, 0, 0.5));
		overlay.setVisible(false);
		overlay.widthProperty().bind(scene.widthProperty());
		overlay.heightProperty().bind(scene.heightProperty());

		// Create pause menu buttons
		Button resumeButton = createStyledButton("Resume", _ -> {
			resumeGameFromSettings();
			hidePauseMenu();
		});
		Button restartButton = createStyledButton("Restart", _ -> {
			restartGame();
			hidePauseMenu();
		});
		Button settingsButton = createStyledButton("Settings", _ -> {
			showSettings();
			hidePauseMenu();
		});
		Button mainMenuButton = createStyledButton("Main Menu", _ -> {
			returnToMainMenu();
			hidePauseMenu();
		});
		Button quitButton = createStyledButton("Quit", _ -> {
			quitGame();
		});

		// Layout buttons
		pauseMenu = new VBox(20, resumeButton, restartButton, settingsButton, mainMenuButton, quitButton);
		pauseMenu.setAlignment(Pos.CENTER);
		pauseMenu.setVisible(false);

		// Create a stack pane to overlay the buttons
		pausePane = new StackPane();
		pausePane.getChildren().addAll(overlay, pauseMenu);
		StackPane.setAlignment(pauseMenu, Pos.CENTER);

		root.getChildren().add(pausePane);
	}

	/**
	 * Shows the pause menu overlay, allowing the player to resume or restart the game.
	 */
	private void showPauseMenu() {
		overlay.setVisible(true);
		pauseMenu.setVisible(true);
		pausePane.toFront();
		pauseMenu.requestFocus();
	}

	/**
	 * Hides the pause menu overlay and buttons.
	 */
	private void hidePauseMenu() {
		overlay.setVisible(false);
		pauseMenu.setVisible(false);
	}

	/**
	 * Creates a styled button with the specified text and action event.
	 *
	 * @param text         The text to display on the button.
	 * @param eventHandler The action event to trigger when the button is pressed.
	 * @return The styled button.
	 */
	private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
		Button button = new Button(text);
		button.setPrefSize(200, 50);
		button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
		button.setOnAction(eventHandler);
		button.setOnMouseEntered(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #ff69b4; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
		button.setOnMouseExited(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
		return button;
	}

	/**
	 * Pauses or unpauses the game depending on the current state.
	 * If the game is paused, it will resume; if the game is running, it will pause.
	 */
	public void pauseGame() {
		if (!isGamePaused) {
			timeline.pause();
			isGamePaused = true;
			showPauseMenu();
		} else {
			timeline.play();
			isGamePaused = false;
			hidePauseMenu();
		}
	}

	/**
	 * Sets the callback to be executed when the level is completed.
	 *
	 * @param onLevelComplete The callback to execute when the level is complete.
	 */
	public void setOnLevelComplete(Runnable onLevelComplete) {
		this.onLevelComplete = onLevelComplete;
	}

	/**
	 * Notifies that the level is complete and triggers the onLevelComplete callback.
	 */
	protected void levelComplete() {
		if (onLevelComplete != null) {
			onLevelComplete.run();
		}
	}

	/**
	 * The game loop that updates the level's state, including spawning enemies, updating actors,
	 * handling collisions, and checking for game-over conditions.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleKeyPress();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}
}