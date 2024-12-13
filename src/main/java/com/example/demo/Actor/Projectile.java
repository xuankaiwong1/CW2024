package com.example.demo.Actor;

/**
 * Abstract class representing a projectile in the game.
 * Extends ActiveActorDestructible to include destruction and damage functionality.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructor to initialize a projectile with the given parameters.
	 * @param imageName The name of the image to represent the projectile.
	 * @param imageHeight The height of the projectile image.
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles damage taken by the projectile. When a projectile takes damage, it is destroyed.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the position of the projectile.
	 * Implementations should define how the projectile moves.
	 */
	@Override
	public abstract void updatePosition();
}
