package com.example.demo.Level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import com.example.demo.Actor.User.UserPlane;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import com.example.demo.Screen.SettingsScreen;
import com.example.demo.Screen.MainMenu;

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

	public void pauseGame() {
		if (!isGamePaused) {
			timeline.pause();
			isGamePaused = true;
		}
		else {
			timeline.play();
			isGamePaused = false;
		}
	}

	public void pauseMenu() {

	}

	public void goToNextLevel(String levelName) {
		// Notify observers about level change
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
		if (activeKeys.contains(KeyCode.SPACE))
		{
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastFiredProjectile> PROJECTILE_COOLDOWN) {
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
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed)
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
	private void resumeGame() {
		timeline.play();
	}

	// Method to restart the game (implementation depends on your game logic)
	private void restartGame() {
		// Restart game logic here
		// For example, you might want to reset the game state and start the game again
		timeline.stop();
		// Reset game state (e.g., clear enemies, reset player health, etc.)
		// Reinitialize the game
		initializeScene();
		startGame();
	}

	// Method to quit the game (implementation depends on your game logic)
	private void quitGame() {
		// Quit game logic here
		// For example, you might want to close the application or go back to the main menu
		System.exit(0); // This will close the application
	}

	// Method to show settings screen
	private void showSettings() {
		SettingsScreen settingsScreen = new SettingsScreen(stage, mediaPlayer);
		settingsScreen.show();
	}

	// Method to return to main menu
	private void returnToMainMenu() {
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(stage);
	}
}