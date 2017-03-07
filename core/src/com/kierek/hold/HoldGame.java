package com.kierek.hold;

import com.badlogic.gdx.Game;
import com.kierek.screens.GameScreen;

public class HoldGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
    }
}
