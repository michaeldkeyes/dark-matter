package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.darkmatter.DarkMatter;
import com.mygdx.darkmatter.ecs.component.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen extends AbstractScreen {

    private static final float MAX_DELTA_TIME = 1 / 20f;

    public GameScreen(final DarkMatter game) {
        super(game);

        final Entity entity = engine.createEntity();

        final TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.setInitialPosition(4.5f, 8f, 0f);

        final MoveComponent moveComponent = engine.createComponent(MoveComponent.class);

        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);

        final PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        final FacingComponent facingComponent = engine.createComponent(FacingComponent.class);

        entity.add(transformComponent);
        entity.add(moveComponent);
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

        engine.update(Math.min(MAX_DELTA_TIME, delta));

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
