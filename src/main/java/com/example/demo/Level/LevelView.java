package com.example.demo.Level;

import com.example.demo.Image.HeartDisplay;
import javafx.scene.Group;

/**
 * The LevelView class is responsible for managing the UI elements of a specific level in the game,
 * specifically focusing on the display and management of the player's health (represented by hearts).
 * It provides methods for displaying and updating the heart display as the player's health changes.
 *
 * Functionality:
 * <ul>
 *     <li>Displays the heart icons at the top left corner of the screen to represent the player's health.</li>
 *     <li>Updates the heart display to reflect changes in the player's remaining health (removes hearts as health decreases).</li>
 * </ul>
 *
 * The heart display is managed through the {@link HeartDisplay} class, which handles the visual
 * representation of the player's health.
 *
 * Constructor:
 * <ul>
 *     <li>{@link #LevelView(Group, int)}: Initializes the level view with a given root and the number of hearts to display.</li>
 * </ul>
 *
 * Methods:
 * <ul>
 *     <li>{@link #showHeartDisplay()}: Displays the heart container on the screen.</li>
 *     <li>{@link #removeHearts(int)}: Removes hearts from the display based on the remaining health of the player.</li>
 * </ul>
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private final Group root;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a LevelView instance for a specific level with a given root container and number of hearts to display.
	 *
	 * @param root the root container to which UI elements will be added.
	 * @param heartsToDisplay the initial number of hearts (player's health) to display.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}

	/**
	 * Displays the heart container on the screen by adding it to the root container.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Updates the heart display by removing hearts based on the player's remaining health.
	 * This method removes hearts until the heart display reflects the correct number of remaining hearts.
	 *
	 * @param heartsRemaining the number of hearts that should remain in the display.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}
