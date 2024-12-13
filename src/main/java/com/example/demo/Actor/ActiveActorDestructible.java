package com.example.demo.Actor;

/**
 * Abstract class representing a destructible active actor in the game.
 * Extends {@link ActiveActor} and implements {@link Destructible} to provide destruction behavior.
 * This class should be extended by any active actor that can be destroyed (e.g., enemies or objects).
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * Constructor to initialize a destructible active actor with the specified image, position, and size.
	 *
	 * @param imageName    The name of the image file for the actor.
	 * @param imageHeight  The height of the actor image.
	 * @param initialXPos  The initial X position of the actor.
	 * @param initialYPos  The initial Y position of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Abstract method for updating the position of the active actor.
	 * This method should be implemented by subclasses to define how the actor moves or changes over time.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Abstract method for updating the actor's state, such as its behavior or actions.
	 * This method should be implemented by subclasses.
	 */
	public abstract void updateActor();

	/**
	 * Abstract method for taking damage. This should be implemented by subclasses
	 * to define the behavior when the actor is damaged.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor, marking it as destroyed and disabling its further actions.
	 */
	@Override
	public void destroy() {
		setDestroyed();
	}

	/**
	 * Marks the actor as destroyed.
	 */
	protected void setDestroyed() {
		this.isDestroyed = true;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
