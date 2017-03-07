package com.kierek.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kierek.factories.BallsFactory;
import com.kierek.factories.CheckersFactory;
import com.kierek.gameObjects.Ball;
import com.kierek.gameObjects.BallChecker;

import java.util.ArrayList;

public class GameWorld {

    public static final int WORLD_WIDTH = 225;
    public static final int WORLD_HEIGHT = 400;

    private static final int STARTING_AMOUNT = 3;
    private static final String TAG = "GameWorld";

    private float[] scoresArray;

    private BallsFactory ballsFactory;
    private CheckersFactory checkersFactory;

    private ArrayList<Ball> balls;
    private ArrayList<BallChecker> ballCheckers;

    private WorldState currentState;

    enum WorldState {
        ROLLING_BALLS, WAITING_FOR_INPUT, CALCULATING_SCORE
    }

    private int iteration;

    public GameWorld() {

        checkersFactory = new CheckersFactory();
        ballsFactory = new BallsFactory();
        balls = ballsFactory.createBalls(STARTING_AMOUNT + iteration);

        ballCheckers = new ArrayList<BallChecker>();

        currentState = WorldState.ROLLING_BALLS;
    }

    public void update(float delta) {

        switch (currentState) {
            case ROLLING_BALLS:
                for (Ball ball : balls) {
                    if (!ball.isExpanded()) {
                        ball.expand(delta);
                        // one ball at a time
                        return;
                    }
                }
                currentState = WorldState.WAITING_FOR_INPUT;
                break;
            case WAITING_FOR_INPUT:
                for (int i = 0; i < ballCheckers.size(); i++) {
                    BallChecker checker = ballCheckers.get(i);
                    checker.update(delta);

                    if (i == balls.size() - 1) {
                        if (checker.isStopped()) {
                            currentState = WorldState.CALCULATING_SCORE;
                        }
                    }
                }
                break;
            case CALCULATING_SCORE:
                if (scoresArray == null) {
                    scoresArray = new float[balls.size()];

                    for (int i = 0; i < scoresArray.length; i++) {
                        scoresArray[i] = circleOverlapFraction(balls.get(i).getBounds(), ballCheckers.get(i).getBounds()) * 100;
                    }

                    printScores();
                }
                break;
        }
    }

    void createChecker(float xPos, float yPos, int pointer) {
        ballCheckers.add(checkersFactory.createBallChecker(xPos, yPos, pointer));
    }

    void nextRound() {
        balls.clear();
        ballCheckers.clear();
        scoresArray = null;

        iteration += 1;

        if (iteration == 30) {
            iteration = 0;
        }

        balls = ballsFactory.createBalls(STARTING_AMOUNT + iteration);
        currentState = WorldState.ROLLING_BALLS;
    }

    private void printScores() {
        Gdx.app.log(TAG, "Round " + (iteration + 1));

        for (int i = 0; i < scoresArray.length; i++) {
            Gdx.app.log(TAG, "ball " + i + ": " + scoresArray[i] + "%");
        }
    }

    private float circleOverlapFraction(Circle c1, Circle c2) {
        Vector2 point1 = new Vector2(c1.x, c1.y);
        Vector2 point2 = new Vector2(c2.x, c2.y);
        float distance;
        distance = point1.dst(point2);

        return circleOverlapFraction(distance, c1.radius, c2.radius);
    }

    private float circleOverlapFraction(float originsDistance, float radius1, float radius2) {

        float r1, r2;
        if (radius1 >= radius2) {
            r1 = radius1;
            r2 = radius2;
        } else {
            r1 = radius2;
            r2 = radius1;
        }

        if (originsDistance >= r1 + r2) {
            return 0;
        } else if (r1 >= originsDistance + r2) {

            float biggerArea, smallerArea, coverage;

            biggerArea = (float) (Math.PI * r1 * r1);
            smallerArea = (float) (Math.PI * r2 * r2);

            coverage = smallerArea / biggerArea;

            return coverage;

        } else {
            float x1, x2, y, a1, a2, overlap_area, total_area;

            x1 = (originsDistance * originsDistance - r2 * r2 + r1 * r1) / (2 * originsDistance);
            x2 = Math.abs(originsDistance - x1);
            y = (float) Math.sqrt(r1 * r1 - (x1 * x1));
            a1 = (float) ((r1 * r1) * Math.acos(x1 / r1) - x1 * y);
            a2 = (float) ((r2 * r2) * Math.acos(x2 / r2) - x2 * y);

            if (x1 > originsDistance) {
                a2 = (float) ((Math.PI * r2 * r2) - a2);
            }

            overlap_area = a1 + a2;

            total_area = (float) (Math.PI * (r1 * r1 + r2 * r2) - overlap_area);

            return overlap_area / total_area;
        }
    }

    ArrayList<Ball> getBalls() {
        return balls;
    }

    ArrayList<BallChecker> getCheckers() {
        return ballCheckers;
    }

    WorldState getCurrentState() {
        return currentState;
    }
}
