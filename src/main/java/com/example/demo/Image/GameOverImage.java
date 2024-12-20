package com.example.demo.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int HEIGHT = 600; // Adjust the height as needed
	private static final int WIDTH = 700; // Adjust the width as needed

	/**
	 * Constructs a GameOverImage object to display the "Game Over" image.
	 * This image is displayed at a specified position on the screen.
	 *
	 * @param xPosition The X position of the image on the screen.
	 * @param yPosition The Y position of the image on the screen.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		setFitHeight(HEIGHT);
		setFitWidth(WIDTH);
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
