package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.Projectile;

/**
 * Represents a projectile fired by the Boss in the game.
 * This class defines the behavior and properties of the Boss's fireball projectile.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -17;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructor to initialize a BossProjectile object with the specified initial Y position.
	 * It sets the initial position and image properties of the projectile.
	 *
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the Boss's projectile by moving it horizontally.
	 * The projectile moves in the negative horizontal direction (left).
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the BossProjectile actor by calling the method to update its position.
	 * This method is called during each game update cycle to move the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
