package com.mygdx.darkmatter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.darkmatter.screen.ScreenType;

import java.util.EnumMap;

public class DarkMatter extends Game {

    private EnumMap<ScreenType, Screen> screenCache;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

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
        batch.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }
}
