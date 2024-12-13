package com.example.demo.Actor.User;

import com.example.demo.Actor.Projectile;

/**
 * Represents a projectile fired by the UserPlane in the game.
 * The UserProjectile moves horizontally across the screen and updates its position each frame.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 65;
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructor to initialize the UserProjectile with the specified initial position.
	 * Sets the image name, height, and position of the projectile.
	 *
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the UserProjectile, moving it horizontally to the right.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the UserProjectile by calling the method to update its position.
	 * This method is invoked every frame to move the projectile.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
