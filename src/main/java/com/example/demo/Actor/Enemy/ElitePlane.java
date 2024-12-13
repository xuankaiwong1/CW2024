package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;

public class ElitePlane extends FighterPlane {

    private static final String IMAGE_NAME = "eliteplane.png";
    private static final int IMAGE_HEIGHT = 275;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 8;
    private static final double FIRE_RATE = .03;

    public ElitePlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EliteProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
