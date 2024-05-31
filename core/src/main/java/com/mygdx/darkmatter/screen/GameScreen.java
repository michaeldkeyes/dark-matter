package com.mygdx.darkmatter.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        transformComponent.position.set(4.5f, 8f, 0);

        final GraphicComponent graphicComponent = engine.createComponent(GraphicComponent.class);

        final PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        final FacingComponent facingComponent = engine.createComponent(FacingComponent.class);

        entity.add(transformComponent);
        entity.add(graphicComponent);
        entity.add(playerComponent);
        entity.add(facingComponent);

        engine.addEntity(entity);

        final Entity entity2 = engine.createEntity();

        final TransformComponent transformComponent2 = engine.createComponent(TransformComponent.class);
        transformComponent2.position.set(1f, 1f, 0);

        final GraphicComponent graphicComponent2 = engine.createComponent(GraphicComponent.class);
        graphicComponent2.sprite.setRegion(game.getGraphicsAtlas().findRegion("ship_left"));

        entity2.add(transformComponent2);
        entity2.add(graphicComponent2);

        engine.addEntity(entity2);

        final Entity entity3 = engine.createEntity();

        final TransformComponent transformComponent3 = engine.createComponent(TransformComponent.class);
        transformComponent3.position.set(8f, 1f, 0);

        final GraphicComponent graphicComponent3 = engine.createComponent(GraphicComponent.class);
        graphicComponent3.sprite.setRegion(game.getGraphicsAtlas().findRegion("ship_right"));

        entity3.add(transformComponent3);
        entity3.add(graphicComponent3);

        engine.addEntity(entity3);

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
