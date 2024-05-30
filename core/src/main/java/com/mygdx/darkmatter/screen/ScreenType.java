package com.mygdx.darkmatter.screen;

public enum ScreenType {

    FIRST_SCREEN(FirstScreen.class),
    SECOND_SCREEN(SecondScreen.class);

    private final Class<? extends AbstractScreen> screenClass;

    ScreenType(Class<? extends AbstractScreen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends AbstractScreen> getScreenClass() {
        return screenClass;
    }
}
