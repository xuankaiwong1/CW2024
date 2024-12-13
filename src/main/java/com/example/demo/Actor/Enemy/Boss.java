package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import com.example.demo.Image.ShieldImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Boss enemy in the game, extending the FighterPlane class.
 * This class controls the boss's movement, shield activation, and projectile firing.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "boss.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .05;
	private static final double BOSS_SHIELD_PROBABILITY = .01;
	private static final int IMAGE_HEIGHT = 300;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -75;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 200;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private final ShieldImage shieldImage; // Declare shieldImage

	/**
	 * Constructor to initialize the Boss object with the necessary attributes.
	 * It sets the initial position, health, and move pattern, as well as initializes the shield.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION); // Initialize shieldImage
	}

	/**
	 * Updates the position of the Boss based on the move pattern.
	 * Ensures the Boss stays within the vertical bounds and adjusts its shield position.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		shieldImage.setLayoutY(currentPosition);
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the Boss actor by calling the methods to update its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the Boss if the fire rate condition is met.
	 *
	 * @return A new BossProjectile if the Boss fires, otherwise null.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles the damage taken by the Boss.
	 * The Boss can only take damage if it is not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the move pattern of the Boss. The pattern consists of up, down, and no movement, shuffled randomly.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the Boss. If the shield is active, it will deactivate after a set number of frames.
	 * If the shield is not active, it checks whether it should be activated.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			if (framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD) {
				deactivateShield();
			}
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}
	}

	/**
	 * Gets the next move in the Boss's movement pattern. The move can be up, down, or stationary.
	 *
	 * @return The next vertical movement of the Boss.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines whether the Boss fires a projectile based on the fire rate probability.
	 *
	 * @return True if the Boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial position of the projectile when the Boss fires.
	 *
	 * @return The Y-position at which the projectile is fired.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines whether the shield should be activated based on a random probability.
	 *
	 * @return True if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Activates the Boss's shield and makes it visible.
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the Boss's shield and hides it.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	/**
	 * Gets the ShieldImage associated with the Boss.
	 *
	 * @return The ShieldImage object.
	 */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}
}
