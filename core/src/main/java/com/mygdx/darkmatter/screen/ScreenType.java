package com.mygdx.darkmatter.screen;

public enum ScreenType {

    GAME_SCREEN(GameScreen.class);

    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends AbstractScreen> getScreenClass() {
        return screenClass;
    }
}
