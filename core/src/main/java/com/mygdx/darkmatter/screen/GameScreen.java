package com.mygdx.darkmatter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.DarkMatter;

import static com.mygdx.darkmatter.DarkMatter.UNIT_SCALE;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    private static final float WORLD_WIDTH = 9;
    private static final float WORLD_HEIGHT = 16;

    private final Sprite sprite;
    private final Texture img;
    private final Viewport viewport;

    public GameScreen(final DarkMatter game) {
        super(game);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        img = new Texture("graphics/ship_base.png");
        sprite = new Sprite(img);
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show");
        sprite.setPosition(1, 1);
        //sprite.setSize(img.getWidth() * UNIT_SCALE, img.getHeight() * UNIT_SCALE);
        sprite.setSize(1, 1);
    }

    @Override
    public void render(final float delta) {
        viewport.apply();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(0, 0, 0, 1);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        sprite.draw(batch);

        batch.end();

        // uiViewport.apply();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true);
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
