package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.darkmatter.DarkMatter;

public abstract class AbstractScreen extends ScreenAdapter {

    protected final DarkMatter game;

    protected AbstractScreen(final DarkMatter game) {
        this.game = game;
    }
}
