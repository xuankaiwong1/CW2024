package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;

/**
 * Represents an EnemyPlane in the game, extending the FighterPlane class.
 * The EnemyPlane moves horizontally and has a chance to fire projectiles at a specified fire rate.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 4;
	private static final double FIRE_RATE = .02;

	/**
	 * Constructor to initialize the EnemyPlane object with the specified initial X and Y positions.
	 * It sets the initial health and image properties of the EnemyPlane.
	 *
	 * @param initialXPos The initial X position of the EnemyPlane.
	 * @param initialYPos The initial Y position of the EnemyPlane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the EnemyPlane by moving it horizontally.
	 * The EnemyPlane moves at a constant horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the EnemyPlane if the fire rate condition is met.
	 * The projectile's position is calculated based on the EnemyPlane's current position.
	 *
	 * @return An EnemyProjectile if the EnemyPlane fires, otherwise null.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the EnemyPlane actor by calling the method to update its position.
	 * This method is invoked each frame to move the plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
