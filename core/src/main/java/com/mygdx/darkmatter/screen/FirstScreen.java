package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.darkmatter.DarkMatter;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen extends AbstractScreen {

    public FirstScreen(final DarkMatter game) {
        super(game);
        Gdx.app.log("FirstScreen", "constructor");
    }

    @Override
    public void show() {
        Gdx.app.log("FirstScreen", "show");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(ScreenType.SECOND_SCREEN);
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
