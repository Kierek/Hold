package com.kierek.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.kierek.gameObjects.BallChecker;

public class InputHandler implements InputProcessor {

    private Vector3 touchPos;
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    private int nextRoundPointer;

    public InputHandler(GameWorld gameWorld, GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.gameWorld = gameWorld;
        touchPos = new Vector3();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY, 0);
        gameRenderer.getViewport().unproject(touchPos);

        switch (gameWorld.getCurrentState()) {
            case WAITING_FOR_INPUT:
                gameWorld.createChecker(touchPos.x, touchPos.y, pointer);
                return true;
            case CALCULATING_SCORE:
                nextRoundPointer = pointer;
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (gameWorld.getCurrentState()) {
            case WAITING_FOR_INPUT:
                for (BallChecker c : gameWorld.getCheckers()) {
                    if (c.getTouchPointer() == pointer) {
                        c.setExpanding(false);
                        break;
                    }
                }
                return true;
            case CALCULATING_SCORE:
                if (pointer == nextRoundPointer) {
                    gameWorld.nextRound();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
