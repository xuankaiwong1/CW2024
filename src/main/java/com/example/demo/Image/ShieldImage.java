package com.example.demo.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 75;

	/**
	 * Constructs a ShieldImage object that represents a shield icon in the game.
	 * The shield is initially set to be invisible and positioned at the specified coordinates.
	 *
	 * @param xPosition The X position of the shield image.
	 * @param yPosition The Y position of the shield image.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);  // Initially, the shield is invisible.
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible, indicating that the shield is active.
	 */
	public void showShield() {
		this.setVisible(true);
		System.out.println("The shield is now visible!");
	}

	/**
	 * Hides the shield, making it invisible.
	 */
	public void hideShield() {
		this.setVisible(false);
		System.out.println("The shield is now hidden!");
	}
}
