package com.example.demo.Actor.Enemy;

import com.example.demo.Actor.Projectile;

public class EliteProjectile extends Projectile {

    private static final String IMAGE_NAME = "elitefire.png";
    private static final int IMAGE_HEIGHT = 35;
    private static final int HORIZONTAL_VELOCITY = -10;

    public EliteProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }


}
