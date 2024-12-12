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
	private int verticalVelocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private Timeline firingTimeline;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
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
			this.moveVertically(VELOCITY * verticalVelocityMultiplier);
			this.moveHorizontally(VELOCITY * horizontalVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			double newXPosition = getLayoutX() + getTranslateX();
			if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
			if (newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
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
}