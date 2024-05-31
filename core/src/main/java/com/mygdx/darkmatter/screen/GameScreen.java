package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;
import com.mygdx.darkmatter.ecs.system.RenderSystem;

import static com.mygdx.darkmatter.DarkMatter.UNIT_SCALE;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    //private final Entity player;

    public GameScreen(final DarkMatter game) {
        super(game);

//        player = engine.createEntity();
//        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
//        transformComponent.position.set(1, 1, 0);
//        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);
//        graphicComponent.sprite.setRegion(new Texture(Gdx.files.internal("graphics/ship_base.png")));
//        graphicComponent.sprite.setSize(
//            graphicComponent.sprite.getTexture().getWidth() * UNIT_SCALE,
//            graphicComponent.sprite.getTexture().getHeight() * UNIT_SCALE
//        );
//        graphicComponent.sprite.setOriginCenter();
//        player.add(transformComponent);
//        player.add(graphicComponent);

        for (int i = 0; i < 10; i++) {
            final Entity entity = engine.createEntity();
            final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
            transformComponent.position.set(MathUtils.random(0f, 9f), MathUtils.random(0f, 16f), 0);
            final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);
            graphicComponent.sprite.setRegion(new Texture(Gdx.files.internal("graphics/ship_base.png")));
            graphicComponent.sprite.setSize(
                graphicComponent.sprite.getTexture().getWidth() * UNIT_SCALE,
                graphicComponent.sprite.getTexture().getHeight() * UNIT_SCALE
            );
            graphicComponent.sprite.setOriginCenter();
            entity.add(transformComponent);
            entity.add(graphicComponent);

            engine.addEntity(entity);
        }

        //engine.addEntity(player);

        engine.addSystem(new RenderSystem(batch, viewport));
    }

    @Override
    public void render(final float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        engine.update(delta);

        // uiViewport.apply();
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
