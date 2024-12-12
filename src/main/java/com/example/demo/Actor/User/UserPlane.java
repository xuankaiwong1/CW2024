package com.example.demo.Actor.User;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
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
	private Timeline firingTimeline;
	private boolean isInvincible;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
		isInvincible = false;
		initializeFiringTimeline();
	}

	private void initializeFiringTimeline() {
		firingTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> fireProjectile()));
		firingTimeline.setCycleCount(Timeline.INDEFINITE);
	}

	public void startFiring() {
		firingTimeline.play();
	}

	public void stopFiring() {
		firingTimeline.stop();
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();
			moveVertically(VELOCITY * verticalVelocityMultiplier);
			moveHorizontally(VELOCITY * horizontalVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			double newXPosition = getLayoutX() + getTranslateX();
			if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
				setTranslateY(initialTranslateY);
			}
			if (newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND) {
				setTranslateX(initialTranslateX);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return verticalVelocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopVertical() {
		verticalVelocityMultiplier = 0;
	}

	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

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

	private void startBlinking() {
		isInvincible = true;
		Timeline blinkTimeline = new Timeline();
		for (int i = 0; i < TOTAL_BLINKS * 2; i++) {
			blinkTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * BLINK_INTERVAL), e -> setVisible(!isVisible())));
		}
		blinkTimeline.setOnFinished(e -> {
			setVisible(true);
			isInvincible = false;
		});
		blinkTimeline.play();
	}

	public void setActorCollisionEnabled(boolean enabled, int durationMs) {
		getCollisionComponent().SetActorCollisionEnable(enabled);
		if (!enabled) {
			new Timeline(new KeyFrame(Duration.millis(durationMs), event -> getCollisionComponent().SetActorCollisionEnable(true))).play();
		}
	}

	private CollisionComponent getCollisionComponent() {
		return new CollisionComponent();
	}

	private static class CollisionComponent {
		void SetActorCollisionEnable(boolean enabled) {
			// Implement collision enabling/disabling logic
		}
	}
}