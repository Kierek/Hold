package com.kierek.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kierek.gameObjects.Ball;
import com.kierek.gameObjects.BallChecker;

import java.util.ArrayList;

public class GameRenderer {

    private static final String TAG = "GameRenderer";

    private GameWorld world;

    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private FitViewport viewport;

    public GameRenderer(GameWorld world) {
        this.world = world;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT);
        viewport = new FitViewport(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, cam);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render() {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (world.getCurrentState() == GameWorld.WorldState.CALCULATING_SCORE) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        } else {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }

        if (world.getCurrentState() != GameWorld.WorldState.WAITING_FOR_INPUT) {
            renderBalls();
        }
        renderCheckers();
        shapeRenderer.end();
    }

    private void renderBalls() {
        shapeRenderer.setColor(Color.BLACK);

        ArrayList<Ball> balls = world.getBalls();

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            shapeRenderer.circle(ball.getBounds().x, ball.getBounds().y, ball.getBounds().radius);
        }
    }

    private void renderCheckers() {
        shapeRenderer.setColor(Color.BLUE);

        ArrayList<BallChecker> ballCheckers = world.getCheckers();

        for (int i = 0; i < ballCheckers.size(); i++) {
            BallChecker ballChecker = ballCheckers.get(i);
            shapeRenderer.circle(ballChecker.getBounds().x, ballChecker.getBounds().y, ballChecker.getBounds().radius);
        }
    }

    public Viewport getViewport() {
        return viewport;
    }

}
