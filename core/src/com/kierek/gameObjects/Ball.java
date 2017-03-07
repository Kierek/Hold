package com.kierek.gameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class Ball {

    private static final String TAG = "Ball";

    private static final int INTERPOLATION_SPEED = 20;

    private Circle bounds;

    private float targetRadius;
    private float elapsed = 0f;

    public Ball(Circle c) {
        bounds = new Circle(c.x, c.y, 0);

        targetRadius = c.radius;
    }

    public Circle getBounds() {
        return bounds;
    }

    public Circle getTargetBounds() {
        return new Circle(bounds.x, bounds.y, targetRadius);
    }

    public void expand(float deltaTime) {
        elapsed += deltaTime;

        bounds.radius = MathUtils.clamp(elapsed * INTERPOLATION_SPEED, bounds.radius, targetRadius);
    }

    public boolean isExpanded() {
        return bounds.radius >= targetRadius;
    }
}
