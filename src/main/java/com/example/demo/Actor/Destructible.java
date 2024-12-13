package com.example.demo.Actor;

/**
 * Interface representing objects that can be damaged and destroyed.
 * Any class that represents a destructible entity should implement this interface.
 */
public interface Destructible {

	/**
	 * Method to be called when the object takes damage.
	 * Implementations should define the effect of taking damage (e.g., reducing health).
	 */
	void takeDamage();

	/**
	 * Method to destroy the object.
	 * Implementations should define the actions to be taken when the object is destroyed (e.g., removal from the game).
	 */
	void destroy();
}
