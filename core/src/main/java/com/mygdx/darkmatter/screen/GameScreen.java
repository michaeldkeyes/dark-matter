package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.darkmatter.DarkMatter;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    private final Sprite sprite;
    private final Texture img;

    public GameScreen(final DarkMatter game) {
        super(game);

        img = new Texture("graphics/ship_base.png");
        sprite = new Sprite(img);
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show");
        sprite.setPosition(100, 100);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        sprite.draw(batch);

        batch.end();


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
        img.dispose();
    }
}
