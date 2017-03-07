package com.kierek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kierek.game.GameRenderer;
import com.kierek.game.GameWorld;
import com.kierek.game.InputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen() {
        world = new GameWorld();
        renderer = new GameRenderer(world);

        Gdx.input.setInputProcessor(new InputHandler(world, renderer));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        renderer.render();
        world.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
