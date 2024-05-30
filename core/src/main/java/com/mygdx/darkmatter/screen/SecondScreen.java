package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.darkmatter.DarkMatter;

public class SecondScreen extends AbstractScreen {

    public SecondScreen(final DarkMatter game) {
        super(game);
        Gdx.app.log("SecondScreen", "constructor");
    }

    @Override
    public void show() {
        Gdx.app.log("SecondScreen", "show");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(ScreenType.FIRST_SCREEN);
        }
    }
}
