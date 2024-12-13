package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.Projectile;

/**
 * Represents a projectile fired by the EnemyPlane in the game.
 * This class defines the behavior and properties of the EnemyPlane's projectile.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructor to initialize an EnemyProjectile with the specified initial X and Y positions.
	 * It sets the image properties and position of the projectile.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the EnemyProjectile by moving it horizontally.
	 * The projectile moves at a constant horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the EnemyProjectile actor by calling the method to update its position.
	 * This method is called during each game update cycle to move the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
