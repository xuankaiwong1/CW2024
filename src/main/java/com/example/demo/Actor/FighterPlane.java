package com.example.demo.Actor;

/**
 * Abstract class representing a fighter plane in the game.
 * It extends ActiveActorDestructible to include health management and projectile firing functionality.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * Constructor to initialize a FighterPlane with specified parameters.
	 * @param imageName The name of the image to represent the fighter plane.
	 * @param imageHeight The height of the fighter plane image.
	 * @param initialXPos The initial X position of the fighter plane.
	 * @param initialYPos The initial Y position of the fighter plane.
	 * @param health The initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method for firing a projectile. Implementations define how the plane fires.
	 * @return A new ActiveActorDestructible projectile, or null if no projectile is fired.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces health by 1 and checks if the plane's health has reached zero.
	 * If health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position for a projectile based on the plane's current position and an offset.
	 * @param xPositionOffset The X position offset for the projectile.
	 * @return The calculated X position for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position for a projectile based on the plane's current position and an offset.
	 * @param yPositionOffset The Y position offset for the projectile.
	 * @return The calculated Y position for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the fighter plane's health has reached zero.
	 * @return true if health is zero, false otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 * @return The current health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
}
