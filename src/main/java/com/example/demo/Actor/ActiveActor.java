package com.example.demo.Actor;

import javafx.scene.image.*;
import java.util.Objects;

/**
 * Represents an active actor in the game, such as a character or an enemy,
 * which can be displayed on the screen and moved horizontally or vertically.
 * This class extends {@link ImageView} to display images and provides basic movement functionality.
 */
public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructor to initialize an active actor with the specified image, position, and size.
	 * The image is loaded from the specified resource path, and the actor is positioned
	 * at the given coordinates with the specified height.
	 *
	 * @param imageName    The name of the image file for the actor.
	 * @param imageHeight  The height of the actor image.
	 * @param initialXPos  The initial X position of the actor.
	 * @param initialYPos  The initial Y position of the actor.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Load the image and set the actor's initial position and size
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method for updating the position of the active actor.
	 * This method should be implemented by subclasses to define how the actor moves or changes over time.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 *
	 * @param horizontalMove The distance to move the actor along the X axis.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 *
	 * @param verticalMove The distance to move the actor along the Y axis.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
