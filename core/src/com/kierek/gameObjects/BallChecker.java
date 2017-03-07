package com.kierek.gameObjects;

import com.badlogic.gdx.math.Circle;

public class BallChecker {

    private static final String TAG = "BallChecker";

    private static final int INTERPOLATION_SPEED = 20;

    private int touchPointer;
    private Circle bounds;
    private boolean isExpanding;

    public BallChecker(float xPos, float yPos, int pointer) {
        bounds = new Circle(xPos, yPos, .1f);
        touchPointer = pointer;
        isExpanding = true;
    }

    public void update(float deltaTime) {
        if (isExpanding) {
            bounds.radius += deltaTime * INTERPOLATION_SPEED;
        }
    }

    public Circle getBounds() {
        return bounds;
    }

    public int getTouchPointer() {
        return touchPointer;
    }

    public void setExpanding(boolean bool) {
        if (!bool)
            touchPointer = 999;

        isExpanding = bool;

    }

    public boolean isStopped() {
        return !isExpanding;
    }
}
