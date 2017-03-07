package com.kierek.factories;

import com.kierek.gameObjects.BallChecker;

public class CheckersFactory {
    public BallChecker createBallChecker(float xPos, float yPos, int pointer) {
        return new BallChecker(xPos, yPos, pointer);
    }
}
