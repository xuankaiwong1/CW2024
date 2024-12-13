package com.example.demo.Level;

import com.example.demo.Image.ShieldImage;
import javafx.scene.Group;

/**
 * The LevelViewLevelThree class extends the {@link LevelView} class to include additional UI elements
 * specific to Level Three, such as a shield image. It manages the display of health (hearts) and the shield icon.
 *
 * Functionality:
 * <ul>
 *     <li>Displays the shield icon on the screen in addition to the heart icons, representing the player's shield.</li>
 *     <li>Inherits the heart display functionality from {@link LevelView} to show the player's health.</li>
 * </ul>
 *
 * Constructor:
 * <ul>
 *     <li>{@link #LevelViewLevelThree(Group, int)}: Initializes the level view for Level Three, with a given root container and the number of hearts to display. It also adds the shield icon.</li>
 * </ul>
 *
 * Methods:
 * <ul>
 *     <li>{@link #addImagesToRoot()}: Adds the shield image to the root container for display.</li>
 * </ul>
 */
public class LevelViewLevelThree extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelViewLevelThree instance for Level Three with a given root container and number of hearts to display.
	 * This constructor also initializes and adds the shield image to the view.
	 *
	 * @param root the root container to which UI elements will be added.
	 * @param heartsToDisplay the initial number of hearts (player's health) to display.
	 */
	public LevelViewLevelThree(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root container for display.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

}
