package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.FacingComponent;
import com.mygdx.darkmatter.ecs.component.GraphicComponent;
import com.mygdx.darkmatter.ecs.component.PlayerComponent;
import com.mygdx.darkmatter.ecs.component.TransformComponent;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    public GameScreen(final DarkMatter game) {
        super(game);

        final Entity entity = engine.createEntity();

        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.position.set(MathUtils.random(0f, 9f), MathUtils.random(0f, 16f), 0);

        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);

        final PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        final FacingComponent facingComponent = engine.createComponent(FacingComponent.class);

        entity.add(transformComponent);
        entity.add(graphicComponent);
        entity.add(playerComponent);
        entity.add(facingComponent);

        engine.addEntity(entity);

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
