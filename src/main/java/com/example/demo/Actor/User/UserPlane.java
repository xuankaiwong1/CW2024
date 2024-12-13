package com.example.demo.Actor.User;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Represents the user-controlled plane in the game.
 * The UserPlane can move, fire projectiles, and take damage,
 * with added functionality for invincibility and kill tracking.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 695.0;
	private static final double X_LEFT_BOUND = 0.0;
	private static final double X_RIGHT_BOUND = 1200.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50; // Reduced size
	private static final int VELOCITY = 12;
	private static final int PROJECTILE_X_POSITION_OFFSET = 120;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -20;
	private static final int BLINK_INTERVAL = 250;
	private static final int BLINK_DURATION = 2000;
	private static final int TOTAL_BLINKS = 4;

	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private boolean isInvincible;

	/**
	 * Constructor to initialize the UserPlane with a given health.
	 * Sets the initial position, health, and firing behavior of the plane.
	 *
	 * @param initialHealth The initial health of the UserPlane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
		isInvincible = false;
		initializeFiringTimeline();
	}

	/**
	 * Initializes the firing timeline, which fires projectiles periodically.
	 * The projectile is fired every 200 milliseconds.
	 */
	private void initializeFiringTimeline() {
		Timeline firingTimeline = new Timeline(new KeyFrame(Duration.millis(200), _ -> fireProjectile()));
		firingTimeline.setCycleCount(Timeline.INDEFINITE);
	}

	/**
	 * Updates the UserPlane's position, ensuring it stays within the predefined screen bounds.
	 * The plane can move both vertically and horizontally.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			moveVertically(VELOCITY * verticalVelocityMultiplier);
			moveHorizontally(VELOCITY * horizontalVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			double newXPosition = getLayoutX() + getTranslateX();

			// Constrain the Y position within bounds
			if (newYPosition < Y_UPPER_BOUND) {
				setTranslateY(Y_UPPER_BOUND - getLayoutY());
			} else if (newYPosition > Y_LOWER_BOUND) {
				setTranslateY(Y_LOWER_BOUND - getLayoutY());
			}

			// Constrain the X position within bounds
			if (newXPosition < X_LEFT_BOUND) {
				setTranslateX(X_LEFT_BOUND - getLayoutX());
			} else if (newXPosition > X_RIGHT_BOUND) {
				setTranslateX(X_RIGHT_BOUND - getLayoutX());
			}
		}
	}

	/**
	 * Updates the UserPlane actor by calling the method to update its position.
	 * This method is invoked every frame to move the plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the UserPlane, offsetting its position based on predefined offsets.
	 *
	 * @return A new UserProjectile object if fired.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the plane is currently moving based on vertical or horizontal movement.
	 *
	 * @return true if the plane is moving, false otherwise.
	 */
	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the plane upwards by setting the vertical velocity multiplier to -1.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane downwards by setting the vertical velocity multiplier to 1.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Moves the plane to the left by setting the horizontal velocity multiplier to -1.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the plane to the right by setting the horizontal velocity multiplier to 1.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops vertical movement by setting the vertical velocity multiplier to 0.
	 */
	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Stops horizontal movement by setting the horizontal velocity multiplier to 0.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Returns the current number of kills made by the UserPlane.
	 *
	 * @return The number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the UserPlane by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Handles taking damage. If the plane is not invincible, the health is decreased,
	 * and the plane enters an invincible state with a blinking effect.
	 */
	@Override
	public void takeDamage() {
		if (!isInvincible) {
			super.takeDamage();
			if (getHealth() > 0) {
				startBlinking();
				setActorCollisionEnabled(false, BLINK_DURATION);
			}
		}
	}

	/**
	 * Starts the blinking effect for the UserPlane, making it invincible for a short time.
	 * The plane will blink several times, and its collision detection is temporarily disabled.
	 */
	private void startBlinking() {
		isInvincible = true;
		Timeline blinkTimeline = new Timeline();
		for (int i = 0; i < TOTAL_BLINKS * 2; i++) {
			blinkTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * BLINK_INTERVAL), _ -> setVisible(!isVisible())));
		}
		blinkTimeline.setOnFinished(_ -> {
			setVisible(true);
			isInvincible = false;
		});
		blinkTimeline.play();
	}

	/**
	 * Enables or disables the collision detection for the UserPlane for a specified duration.
	 *
	 * @param enabled true to enable collision, false to disable.
	 * @param durationMs The duration in milliseconds to disable collision if disabled.
	 */
	public void setActorCollisionEnabled(boolean enabled, int durationMs) {
		getCollisionComponent().SetActorCollisionEnable();
		if (!enabled) {
			new Timeline(new KeyFrame(Duration.millis(durationMs), _ -> getCollisionComponent().SetActorCollisionEnable())).play();
		}
	}

	/**
	 * Returns the collision component for the UserPlane.
	 *
	 * @return The collision component of the plane.
	 */
	private CollisionComponent getCollisionComponent() {
		return new CollisionComponent();
	}

	/**
	 * A simple internal class to manage collision enabling and disabling.
	 */
	private static class CollisionComponent {
		void SetActorCollisionEnable() {
			// Implement collision enabling/disabling logic
		}
	}
}
