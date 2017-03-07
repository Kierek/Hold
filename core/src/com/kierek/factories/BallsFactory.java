package com.kierek.factories;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.kierek.game.GameWorld;
import com.kierek.gameObjects.Ball;

import java.util.ArrayList;
import java.util.Random;

public class BallsFactory {

//    private static final String TAG = "BallsFactory";

    private static final int MAX_RADIUS = 45;

    private Random ran;
    private ArrayList<Ball> balls;

    public BallsFactory() {
        ran = new Random();
    }

    public ArrayList<Ball> createBalls(int amount) {
        balls = new ArrayList<Ball>();

        for (int i = 0; i < amount; i++) {
            balls.add(createNewBall());
        }

        return balls;
    }

    private Ball createNewBall() {
        Circle c = new Circle();

        float newX, newY;
        int targetRadius = ran.nextInt(MAX_RADIUS - 15) + 15;

        if (balls.size() > 0) {

            ArrayList<Ball> toCheck = new ArrayList<Ball>();

            while (true) {

                toCheck.clear();
                for (Ball wB : balls) {
                    toCheck.add(wB);
                }

                newX = ran.nextInt(GameWorld.WORLD_WIDTH - (targetRadius * 2)) + targetRadius;
                newY = ran.nextInt(GameWorld.WORLD_HEIGHT - (targetRadius * 2)) + targetRadius;

                c.set(newX, newY, targetRadius);

                for (int i = toCheck.size() - 1; i >= 0; i--) {
                    Ball b = toCheck.get(i);

                    if (!Intersector.overlaps(c, b.getTargetBounds())) {
                        toCheck.remove(b);
                    }

                }

                if (toCheck.isEmpty()) {
                    break;
                }

                targetRadius = ran.nextInt(MAX_RADIUS - 15) + 15;

            }

        } else {
            newX = ran.nextInt(GameWorld.WORLD_WIDTH - (targetRadius * 2)) + targetRadius;
            newY = ran.nextInt(GameWorld.WORLD_HEIGHT - (targetRadius * 2)) + targetRadius;

            c.set(newX, newY, targetRadius);
        }

        return new Ball(c);
    }

}
