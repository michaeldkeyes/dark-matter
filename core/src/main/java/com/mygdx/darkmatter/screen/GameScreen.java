package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

import static com.mygdx.darkmatter.DarkMatter.UNIT_SCALE;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    private static final float WORLD_WIDTH = 9;
    private static final float WORLD_HEIGHT = 16;

    private final Viewport viewport;

    private final Entity player;

    public GameScreen(final DarkMatter game) {
        super(game);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        player = engine.createEntity();
        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.position.set(1, 1, 0);
        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);
        graphicComponent.sprite.setRegion(new Texture(Gdx.files.internal("graphics/ship_base.png")));
        graphicComponent.sprite.setSize(
            graphicComponent.sprite.getTexture().getWidth() * UNIT_SCALE,
            graphicComponent.sprite.getTexture().getHeight() * UNIT_SCALE
        );
        graphicComponent.sprite.setOriginCenter();
        player.add(transformComponent);
        player.add(graphicComponent);

        engine.addEntity(player);
    }

    @Override
    public void render(final float delta) {
        engine.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        viewport.apply();

        ScreenUtils.clear(0, 0, 0, 1);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        engine.getEntities().forEach(entity -> {
            final TransformComponent transform = TransformComponent.MAPPER.get(entity);
            final GraphicComponent graphic = GraphicComponent.MAPPER.get(entity);

            graphic.sprite.setPosition(transform.position.x, transform.position.y);
            graphic.sprite.draw(batch);
        });

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
    }
}
