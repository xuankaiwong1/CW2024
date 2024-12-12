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
	private LevelView levelView;

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

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Stage stage, MediaPlayer mediaPlayer) {
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

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
		isGamePaused = false;
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
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

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
	}

	protected void loseGame() {
		timeline.stop();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected double getScreenHeight() {
		return screenHeight;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	// Method to resume the game
	public void resumeGameFromSettings() {
		isGamePaused = false;
		timeline.play();
	}

	// Method to restart the game
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
	// Method to get the current level class
	protected Class<? extends LevelParent> getCurrentLevelClass() {
		return this.getClass();
	}

	// Method to quit the game
	private void quitGame() {
		System.exit(0); // This will close the application
	}

	// Method to show settings screen
	private void showSettings() {
		Scene currentScene = stage.getScene(); // Get the current scene
		SettingsScreen settingsScreen = new SettingsScreen(stage, mediaPlayer, currentScene, this); // Pass the current scene and this instance
		settingsScreen.show();
	}

	// Method to return to main menu
	private void returnToMainMenu() {
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);
	}

	// Pause menu initialization
	private void initializePauseMenu() {
		// Create a semi-transparent black overlay and bind its size to the scene
		overlay = new Rectangle();
		overlay.setFill(Color.rgb(0, 0, 0, 0.5));
		overlay.setVisible(false);
		// Bind overlay size to scene size
		overlay.widthProperty().bind(scene.widthProperty());
		overlay.heightProperty().bind(scene.heightProperty());

		// Create pause menu buttons
		Button resumeButton = createStyledButton("Continue", e -> {
			resumeGameFromSettings();
			hidePauseMenu();
		});
		Button restartButton = createStyledButton("Restart", e -> {
			restartGame();
			hidePauseMenu();
		});
		Button settingsButton = createStyledButton("Settings", e -> {
			showSettings();
			hidePauseMenu();
		});
		Button mainMenuButton = createStyledButton("Main Menu", e -> {
			returnToMainMenu();
			hidePauseMenu();
		});
		Button quitButton = createStyledButton("Quit", e -> {
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

	// Method to show the pause menu
	private void showPauseMenu() {
		overlay.setVisible(true);
		pauseMenu.setVisible(true);
		pausePane.toFront(); // Ensure the entire pausePane is in front
		pauseMenu.requestFocus();
	}

	// Method to hide the pause menu
	private void hidePauseMenu() {
		overlay.setVisible(false);
		pauseMenu.setVisible(false);
	}

	// Method to create a styled button
	private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
		Button button = new Button(text);
		button.setPrefSize(200, 50);
		button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
		button.setOnAction(eventHandler);
		button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #ff69b4; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
		button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
				"-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
		return button;
	}

	// Method to pause the game
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

	// Method to set the level complete callback
	public void setOnLevelComplete(Runnable onLevelComplete) {
		this.onLevelComplete = onLevelComplete;
	}

	// Method to notify that the level is complete
	protected void levelComplete() {
		if (onLevelComplete != null) {
			onLevelComplete.run();
		}
	}

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