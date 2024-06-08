package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.event.GameEventManager;

public abstract class AbstractScreen extends ScreenAdapter {

    protected final DarkMatter game;
    protected final Engine engine;
    protected final GameEventManager gameEventManager;
    protected final SpriteBatch batch;
    protected final Viewport uiViewport;
    protected final Viewport viewport;

    protected AbstractScreen(final DarkMatter game) {
        this.game = game;
        this.batch = game.getSpriteBatch();
        this.engine = game.getEngine();
        this.gameEventManager = game.getGameEventManager();
        this.uiViewport = game.getUiViewport();
        this.viewport = game.getViewport();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }
}
