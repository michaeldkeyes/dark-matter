package com.mygdx.darkmatter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.darkmatter.screen.ScreenType;

import java.util.EnumMap;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class DarkMatter extends Game {

    private EnumMap<ScreenType, Screen> screenCache;

    @Override
    public void create() {

        setScreen(ScreenType.FIRST_SCREEN);
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
}
