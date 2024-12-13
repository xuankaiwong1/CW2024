package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;

/**
 * Represents an ElitePlane enemy in the game, extending the FighterPlane class.
 * The ElitePlane moves horizontally and can fire projectiles at a specified fire rate.
 */
public class ElitePlane extends FighterPlane {

    private static final String IMAGE_NAME = "eliteplane.png";
    private static final int IMAGE_HEIGHT = 275;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 8;
    private static final double FIRE_RATE = .03;

    /**
     * Constructor to initialize the ElitePlane object with the specified initial X and Y positions.
     * The initial health and image properties of the ElitePlane are set in this constructor.
     *
     * @param initialXPos The initial X position of the ElitePlane.
     * @param initialYPos The initial Y position of the ElitePlane.
     */
    public ElitePlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the ElitePlane by moving it horizontally.
     * The ElitePlane moves at a constant horizontal velocity.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Fires a projectile from the ElitePlane if the fire rate condition is met.
     * The projectile's position is calculated based on the ElitePlane's current position.
     *
     * @return An EliteProjectile if the ElitePlane fires, otherwise null.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EliteProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Updates the ElitePlane actor by calling the method to update its position.
     * This method is invoked each frame to move the plane.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}
