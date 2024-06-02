package com.mygdx.darkmatter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.ecs.system.*;
import com.mygdx.darkmatter.screen.ScreenType;

import java.util.EnumMap;

public class DarkMatter extends Game {

    public static final float UNIT_SCALE = 1 / 16f;

    public static final float WORLD_WIDTH = 9;
    public static final float WORLD_HEIGHT = 16;

    private Engine engine;
    private EnumMap<ScreenType, Screen> screenCache;
    private SpriteBatch batch;
    private Viewport viewport;

    private TextureAtlas graphicsAtlas;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        graphicsAtlas = new TextureAtlas("graphics/dark-matter.atlas");

        engine = new PooledEngine();
        engine.addSystem(new PlayerSystem(viewport));
        engine.addSystem(new MoveSystem());
        engine.addSystem(new DamageSystem());
        engine.addSystem(new PlayerAnimationSystem(
            graphicsAtlas.findRegion("ship_base"),
            graphicsAtlas.findRegion("ship_left"),
            graphicsAtlas.findRegion("ship_right"))
        );
        engine.addSystem(new AnimationSystem(graphicsAtlas));
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new RemoveSystem());
        engine.addSystem(new DebugSystem());

        setScreen(ScreenType.GAME_SCREEN);
    }

    public void setScreen(ScreenType screenType) {
        if (screenCache == null) {
            screenCache = new EnumMap<>(ScreenType.class);
        }

        final Screen screen = screenCache.get(screenType);

        if (screen == null) {
            try {
                final Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), DarkMatter.class).newInstance(this);

                screenCache.put(screenType, newScreen);
                setScreen(newScreen);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Screen " + screenType + " could not be instantiated", e);
            }
        } else {
            setScreen(screen);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log("DarkMatter", "Sprites in batch: " + batch.maxSpritesInBatch);
        batch.dispose();
        graphicsAtlas.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public Engine getEngine() {
        return engine;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public TextureAtlas getGraphicsAtlas() {
        return graphicsAtlas;
    }
}
