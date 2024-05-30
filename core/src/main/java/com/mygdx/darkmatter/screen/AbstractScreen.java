package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.darkmatter.DarkMatter;

public abstract class AbstractScreen extends ScreenAdapter {

    protected final SpriteBatch batch;
    protected final DarkMatter game;

    protected AbstractScreen(final DarkMatter game) {
        this.game = game;
        this.batch = game.getSpriteBatch();
    }
}
