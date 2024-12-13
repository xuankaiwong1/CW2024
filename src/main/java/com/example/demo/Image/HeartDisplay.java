package com.example.demo.Image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay object to display a series of heart images
	 * representing the player's health. The hearts are shown in a horizontal layout
	 * at the specified position.
	 *
	 * @param xPosition The X position of the container that holds the hearts.
	 * @param yPosition The Y position of the container that holds the hearts.
	 * @param heartsToDisplay The number of hearts to display in the container.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container (HBox) that holds the heart images.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the heart images and adds them to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes the first heart from the display, typically when the player loses health.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().removeFirst();
	}

	/**
	 * Returns the container (HBox) holding the heart images.
	 *
	 * @return The container with heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}
