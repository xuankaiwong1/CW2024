package com.example.demo.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Constructs a WinImage object that represents the "You Win!" image in the game.
	 * The image is initially set to be invisible and positioned at the specified coordinates.
	 *
	 * @param xPosition The X position of the "You Win!" image.
	 * @param yPosition The Y position of the "You Win!" image.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);  // Initially, the "You Win!" image is invisible.
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Makes the "You Win!" image visible, indicating that the player has won the game.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}
